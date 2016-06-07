package de.warehouse.picking;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import de.warehouse.shared.CustomerOrder;
import de.warehouse.shared.CustomerOrderPosition;
import de.warehouse.shared.Employee;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;
import de.warehouse.shared.interfaces.ICommissionService;
import de.warehouse.shared.interfaces.ISessionManagement;

@Stateless
@Remote(ICommissionService.class)
public class CommissionService implements ICommissionService {
	
	@PersistenceContext
	private EntityManager em;

	@EJB
	private ISessionManagement sessionManagementBean;

	/* (non-Javadoc)
	 * @see de.warehouse.shared.interfaces.ICommissionService#getIncompletedCustomerOrders()
	 */
	@Override
	@Lock(LockType.READ)
	public List<CustomerOrder> getIncompletedCustomerOrders() {
        return this.em.createQuery("SELECT e FROM CustomerOrder e WHERE e.commissionProgress < 1", CustomerOrder.class).getResultList();
	}

	/* (non-Javadoc)
	 * @see de.warehouse.shared.interfaces.ICommissionService#getCustomerOrderById(int)
	 */
	@Override
	@Lock(LockType.READ)
	public CustomerOrder getCustomerOrderById(int customerOrderId) {
		return this.em.find(CustomerOrder.class, customerOrderId);
	}

	/* (non-Javadoc)
	 * @see de.warehouse.shared.interfaces.ICommissionService#getPositionByCustomerOrderId(int)
	 */
	@Override
	@Lock(LockType.READ)
	public List<CustomerOrderPosition> getPositionByCustomerOrderId(int customerOrderId) {
		Query q = this.em.createQuery("SELECT e FROM CustomerOrderPosition e WHERE e.order = :customerOrder", CustomerOrderPosition.class);
		
		q.setParameter("customerOrder", this.em.find(CustomerOrder.class, customerOrderId));
		
		return q.getResultList();
	}
	/* (non-Javadoc)
	 * @see de.warehouse.shared.interfaces.ICommissionService#getPendingPositionByCustomerOrderId(int)
	 */
	@Override
	public List<CustomerOrderPosition> getPendingPositionByCustomerOrderId(int customerOrderId) {
		Query q = this.em.createQuery("SELECT e FROM CustomerOrderPosition e WHERE e.order = :customerOrder AND e.pickedQuantity < e.orderedQuantity", CustomerOrderPosition.class);
		
		q.setParameter("customerOrder", this.em.find(CustomerOrder.class, customerOrderId));
		
		return q.getResultList();
	}

	/* (non-Javadoc)
	 * @see de.warehouse.shared.interfaces.ICommissionService#AllocateCustomerOrder(int, int)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void allocateCustomerOrder(int customerOrderId, int employeeId) throws CustomerOrderAlreadyAllocatedException {
		CustomerOrder c = this.em.find(CustomerOrder.class, customerOrderId);
		
		if (c.getPicker() != null) {
			throw new CustomerOrderAlreadyAllocatedException("CustomerOrder has already a picker.");
		}
		
		Employee e = this.em.find(Employee.class, employeeId);
		c.setPicker(e);
		this.em.merge(c);
	}

	/* (non-Javadoc)
	 * @see de.warehouse.shared.interfaces.ICommissionService#UpdatePickedQuantity(int, int)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void updatePickedQuantity(int customerOrderPositionId, int pickedQuantity) throws NegativeQuantityException, PickedQuantityTooHighException {
		if(pickedQuantity < 0) {
			// Quantity must not be negative
			throw new NegativeQuantityException("Picked quantity must not be negative.");
		}
		
		CustomerOrderPosition pos = this.em.find(CustomerOrderPosition.class, customerOrderPositionId);
		
		if(pickedQuantity > pos.getRemainingQuantity()){
			// Entered quantity is too high
			throw new PickedQuantityTooHighException("Picked quantity must be less or equals the ordered quantity.");
			
		}
		
		// Adds the newly picked quantity to the actual quantity
		pos.setPickedQuantity(pos.getPickedQuantity() + pickedQuantity);		
		pos.setDateOfCommission(LocalDateTime.now());
		
		pos.getOrder().updateProgress();
		
		this.em.merge(pos);
	}

	/* (non-Javadoc)
	 * @see de.warehouse.shared.interfaces.ICommissionService#UpdateStart(int)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void updateStart(int customerOrderId) throws CustomerOrderCommissionAlreadyStartedException {
		CustomerOrder c = this.em.find(CustomerOrder.class, customerOrderId);
		
		if(c.getStartOfCommission() != null) {
			// Already started
			throw new CustomerOrderCommissionAlreadyStartedException("CustomerOrder was already started!");
		}
		
		c.setStartOfCommission(LocalDateTime.now());	
		this.em.merge(c);
	}
	/* (non-Javadoc)
	 * @see de.warehouse.shared.interfaces.ICommissionService#UpdateFinish(int)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void updateFinish(int customerOrderId) throws CustomerOrderCommissionAlreadyFinishedException {
		CustomerOrder c = this.em.find(CustomerOrder.class, customerOrderId);
		
		if(c.getFinishOfCommission() != null) {
			// Already started
			throw new CustomerOrderCommissionAlreadyFinishedException("CustomerOrder was already finished!");
		}
		
		c.setFinishOfCommission(LocalDateTime.now());	
		this.em.merge(c);
	}
}
