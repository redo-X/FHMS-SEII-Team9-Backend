package de.warehouse.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import de.warehouse.dao.interfaces.ICommissionDAO;
import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.persistence.Employee;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.CustomerOrderMustBeAllocateToPicker;
import de.warehouse.shared.exceptions.CustomerOrderNotCompletelyCommissioned;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;

/**
 * @see de.warehouse.dao.interfaces.ICommissionDAO
 */
@Stateless
public class CommissionDAO implements ICommissionDAO {
	
	private static final Logger logger = Logger.getLogger(CommissionDAO.class);

	@Resource
	private SessionContext sessionContext;

	@PersistenceContext
	private EntityManager em;

	private ICommissionDAO selfReference;

	@PostConstruct
	public void init() {
		logger.info("Initializing...");
		
		this.selfReference = this.sessionContext.getBusinessObject(ICommissionDAO.class);
		
		logger.info("Initialized.");
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#getCustomerOrders()
	 */
	@Override
	public List<CustomerOrder> getCustomerOrders() {
		logger.info("INVOKE: getCustomerOrders");
		
		return this.em.createQuery("SELECT e FROM " + CustomerOrder.class.getSimpleName() + " e", CustomerOrder.class)
				.getResultList();
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#getPendingCustomerOrders()
	 */
	@Override
	public List<CustomerOrder> getPendingCustomerOrders() {
		logger.info("INVOKE: getPendingCustomerOrders");
	
		return this.em.createQuery(
				"SELECT e FROM " + CustomerOrder.class.getSimpleName() + " e WHERE e.commissionProgress < 1",
				CustomerOrder.class).getResultList();
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#getPendingCustomerOrdersWithoutPicker()
	 */
	@Override
	public List<CustomerOrder> getPendingCustomerOrdersWithoutPicker() {
		logger.info("INVOKE: getPendingCustomerOrdersWithoutPicker");
		
		return this.em.createQuery(
				"SELECT e FROM " + CustomerOrder.class.getSimpleName()
						+ " e WHERE e.commissionProgress < 1 AND e.picker = null AND EXISTS (SELECT 1 FROM "
						+ CustomerOrderPosition.class.getSimpleName()
						+ " p WHERE p.order.code = e.code AND p.pickedQuantity < p.orderedQuantity)",
				CustomerOrder.class).getResultList();
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#getPendingCustomerOrdersByEmployeeId(int)
	 */
	@Override
	public List<CustomerOrder> getPendingCustomerOrdersByEmployeeId(int pickerId) {
		logger.info("INVOKE: getPendingCustomerOrdersWithoutPicker");
		
		Employee picker = this.em.find(Employee.class, pickerId);

		return this.em.createQuery(
				"SELECT e FROM " + CustomerOrder.class.getSimpleName()
						+ " e WHERE e.picker = :picker AND e.commissionProgress < 1 AND EXISTS (SELECT 1 FROM "
						+ CustomerOrderPosition.class.getSimpleName()
						+ " p WHERE p.order.code = e.code AND p.pickedQuantity < p.orderedQuantity)",
				CustomerOrder.class).setParameter("picker", picker).getResultList();
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#getCustomerOrderById(int)
	 */
	@Override
	public CustomerOrder getCustomerOrderById(int customerOrderId) {
		logger.info("INVOKE: getCustomerOrderById: " + customerOrderId);
		
		return this.em.find(CustomerOrder.class, customerOrderId);
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#getCustomerOrderWithPickerById(int)
	 */
	@Override
	public CustomerOrder getCustomerOrderWithPickerById(int customerOrderId) {
		logger.info("INVOKE: getCustomerOrderWithPickerById: " + customerOrderId);
		
		return this.em
				.createQuery("SELECT x FROM " + CustomerOrder.class.getSimpleName()
						+ " x JOIN FETCH x.picker p WHERE x.code = :code", CustomerOrder.class)
				.setParameter("code", customerOrderId).getSingleResult();
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#getCustomerOrderPositionById(int)
	 */
	@Override
	public CustomerOrderPosition getCustomerOrderPositionById(int customerOrderPositionId) {
		logger.info("INVOKE: getCustomerOrderPositionById: " + customerOrderPositionId);
		
		return this.em.find(CustomerOrderPosition.class, customerOrderPositionId);
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#getPositionsByCustomerOrderId(int)
	 */
	@Override
	public List<CustomerOrderPosition> getPositionsByCustomerOrderId(int customerOrderId) {
		logger.info("INVOKE: getPositionsByCustomerOrderId: " + customerOrderId);
		
		return this.em
				.createQuery("SELECT e FROM " + CustomerOrderPosition.class.getSimpleName()
						+ " e WHERE e.order = :customerOrder", CustomerOrderPosition.class)
				.setParameter("customerOrder", this.em.find(CustomerOrder.class, customerOrderId)).getResultList();
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#getPendingPositionsByCustomerOrderId(int)
	 */
	@Override
	public List<CustomerOrderPosition> getPendingPositionsByCustomerOrderId(int customerOrderId) {
		logger.info("INVOKE: getPendingPositionsByCustomerOrderId: " + customerOrderId);
		
		return this.em
				.createQuery(
						"SELECT e FROM " + CustomerOrderPosition.class.getSimpleName()
								+ " e WHERE e.order = :customerOrder AND e.pickedQuantity < e.orderedQuantity",
						CustomerOrderPosition.class)
				.setParameter("customerOrder", this.em.find(CustomerOrder.class, customerOrderId)).getResultList();
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#allocateCustomerOrder(int,
	 *      int)
	 */
	@Override
	public void allocateCustomerOrder(int customerOrderId, int employeeId)
			throws CustomerOrderAlreadyAllocatedException, EntityNotFoundException {
		logger.info("INVOKE: allocateCustomerOrder: " + customerOrderId + " to employee: " + employeeId);
		
		CustomerOrder c = this.em.find(CustomerOrder.class, customerOrderId);
		if(null == c) {
			logger.error("CustomerOrder with id: " + customerOrderId + " not found.");
			
			throw new EntityNotFoundException("CustomerOrder with id: " + customerOrderId + " not found.");
		}
		
		Employee e = this.em.find(Employee.class, employeeId);
		if(null == e) {
			logger.error("Employee with id: " + employeeId + " not found.");
			
			throw new EntityNotFoundException("Employee with id: " + employeeId + " not found.");
		}
		
		logger.info("Set new picker " + employeeId + " on customer order " + customerOrderId);
		
		c.setPicker(e);
		
		this.em.merge(c);
		
		logger.info("SAVEPOINT");
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#updatePickedQuantity(int,
	 *      int)
	 */
	@Override
	public void updatePickedQuantity(int customerOrderPositionId, int pickedQuantity)
			throws NegativeQuantityException, PickedQuantityTooHighException, CustomerOrderMustBeAllocateToPicker {
		logger.info("INVOKE: updatePickedQuantity: " + customerOrderPositionId + " with " + pickedQuantity);
		
		CustomerOrderPosition pos = this.em.find(CustomerOrderPosition.class, customerOrderPositionId);

		// Adds the newly picked quantity to the actual quantity
		logger.info("Set picked quantity " + pickedQuantity);
		pos.setPickedQuantity(pickedQuantity);
		
		logger.info("Set date of commission " + LocalDateTime.now());		
		pos.setDateOfCommission(LocalDateTime.now());

		this.em.merge(pos);
		
		logger.info("SAVEPOINT");

		// Update the progress of the customer order
		// "this" is not a problem here
		logger.info("SELF-INVOCATION: updateCommissionProgress");
		this.selfReference.updateCommissionProgress(pos.getOrder().getCode());
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#updateStart(int)
	 */
	@Override
	public void updateStart(int customerOrderId)
			throws CustomerOrderCommissionAlreadyStartedException, CustomerOrderMustBeAllocateToPicker {
		logger.info("INVOKE: updateStart: " + customerOrderId);
		
		CustomerOrder c = this.em.find(CustomerOrder.class, customerOrderId);

		if (c.getPicker() == null) {
			throw new CustomerOrderMustBeAllocateToPicker("Customer order must be allocated to a picker.");
		}
		if (c.getStartOfCommission() != null) {
			// Already started
			throw new CustomerOrderCommissionAlreadyStartedException("CustomerOrder was already started!");
		}

		c.setStartOfCommission(LocalDateTime.now());
		
		logger.info("Commission " + customerOrderId + " started.");
		
		this.em.merge(c);
		
		logger.info("SAVEPOINT");
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#updateFinish(int)
	 */
	@Override
	public void updateFinish(int customerOrderId) throws CustomerOrderCommissionAlreadyFinishedException,
			CustomerOrderMustBeAllocateToPicker, CustomerOrderNotCompletelyCommissioned {
		logger.info("INVOKE: updateFinish: " + customerOrderId);
		
		CustomerOrder c = this.em.find(CustomerOrder.class, customerOrderId);

		if (c.getPicker() == null) {
			throw new CustomerOrderMustBeAllocateToPicker("Customer order must be allocated to a picker.");
		}
		if (c.getFinishOfCommission() != null) {
			// Already started
			throw new CustomerOrderCommissionAlreadyFinishedException("CustomerOrder was already finished!");
		}
		if (!c.getPositions().isEmpty() && c.getCommissionProgress() != 1) {
			throw new CustomerOrderNotCompletelyCommissioned("CustomerOrder is not completely commissioned!");
		}

		c.setFinishOfCommission(LocalDateTime.now());
		
		logger.info("Commission " + customerOrderId + " finished.");
		
		this.em.merge(c);
		
		logger.info("SAVEPOINT");
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionDAO#updateCommissionProgress(int)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void updateCommissionProgress(int customerOrderId) {
		logger.info("INVOKE: updateCommissionProgress: " + customerOrderId);
		
		CustomerOrder c = this.em.find(CustomerOrder.class, customerOrderId);

		c.updateProgress();
				
		logger.info("Progress updated");

		this.em.merge(c);
		
		logger.info("SAVEPOINT");
	}

}
