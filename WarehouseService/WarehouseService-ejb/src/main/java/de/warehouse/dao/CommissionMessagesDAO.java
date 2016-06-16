package de.warehouse.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.warehouse.dao.interfaces.ICommissionDAO;
import de.warehouse.dao.interfaces.ICommissionMessagesDAO;
import de.warehouse.dao.interfaces.IEmployeeDAO;
import de.warehouse.dao.interfaces.IWarehouseSessionDAO;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.persistence.CustomerOrderPositionMessage;
import de.warehouse.persistence.Employee;
import de.warehouse.persistence.WarehouseSession;

@Stateless
public class CommissionMessagesDAO implements ICommissionMessagesDAO {

	@Resource
	private SessionContext sessionContext;

	@PersistenceContext
	private EntityManager em;

	@EJB
	private ICommissionDAO commissionDAO;

	@EJB
	private IEmployeeDAO employeeDAO;

	@EJB
	private IWarehouseSessionDAO warehouseSessionDAO;

	private ICommissionMessagesDAO selfReference;

	@PostConstruct
	public void init() {
		this.selfReference = this.sessionContext.getBusinessObject(ICommissionMessagesDAO.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void commitMessage(int sessionId, int customerOrderPositionId, int responsibleEmployeeId,
			int differenceQuantity, String note) {
		CustomerOrderPosition pos = this.commissionDAO.getCustomerOrderPositionById(customerOrderPositionId);
		WarehouseSession session = this.warehouseSessionDAO.findById(sessionId);
		Employee creator = session.getEmployee();
		Employee responsibleEmployee = this.employeeDAO.findById(responsibleEmployeeId);

		CustomerOrderPositionMessage message = new CustomerOrderPositionMessage();

		message.setCreatedByEmployee(creator);
		message.setResponsibleEmployee(responsibleEmployee);
		message.setNote(note);
		message.setPosition(pos);
		message.setQuantityDifference(differenceQuantity);

		this.selfReference.create(message);
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionMessagesDAO#updateMailFlag(int)
	 */
	@Override
	public void updateMailFlag(int customerOrderPositionMessageId) {
		CustomerOrderPositionMessage message = this.em.find(CustomerOrderPositionMessage.class,
				customerOrderPositionMessageId);
		
		message.setEmailSent(true);
		
		this.em.merge(message);
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionMessagesDAO#create(de.warehouse.persistence.CustomerOrderPositionMessage)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void create(CustomerOrderPositionMessage e) {
		this.em.persist(e);
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionMessagesDAO#delete(de.warehouse.persistence.CustomerOrderPositionMessage)
	 */
	@Override
	public void delete(CustomerOrderPositionMessage e) {
		this.em.remove(e);
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionMessagesDAO#getPendingMessages()
	 */
	@Override
	public List<CustomerOrderPositionMessage> getPendingMessages() {
		return this.em.createQuery(
				"SELECT x FROM " + CustomerOrderPositionMessage.class.getSimpleName() + " x WHERE x.isEmailSent = 0",
				CustomerOrderPositionMessage.class).getResultList();
	}
}
