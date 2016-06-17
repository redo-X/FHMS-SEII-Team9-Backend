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

import org.jboss.logging.Logger;

import de.warehouse.dao.interfaces.ICommissionDAO;
import de.warehouse.dao.interfaces.ICommissionMessagesDAO;
import de.warehouse.dao.interfaces.IEmployeeDAO;
import de.warehouse.dao.interfaces.IWarehouseSessionDAO;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.persistence.CustomerOrderPositionMessage;
import de.warehouse.persistence.Employee;
import de.warehouse.persistence.WarehouseSession;
import de.warehouse.picking.CommissionService;

@Stateless
public class CommissionMessagesDAO implements ICommissionMessagesDAO {
	
	private static final Logger logger = Logger.getLogger(CommissionMessagesDAO.class);

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
		logger.info(String.format("INVOKE: %s(%d, %d, %d, %d, %s)", "commitMessage", sessionId, customerOrderPositionId, responsibleEmployeeId, differenceQuantity, note));
		
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

		
		logger.info("SELF-INVOCATION: create");
		this.selfReference.create(message);
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionMessagesDAO#updateMailFlag(int)
	 */
	@Override
	public void updateMailFlag(int customerOrderPositionMessageId) {
		logger.info(String.format("INVOKE: %s(%d)", "updateMailFlag", customerOrderPositionMessageId));
		
		CustomerOrderPositionMessage message = this.em.find(CustomerOrderPositionMessage.class,
				customerOrderPositionMessageId);
		
		message.setEmailSent(true);
		
		this.em.merge(message);
		
		logger.info("SAVEPOINT");
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionMessagesDAO#create(de.warehouse.persistence.CustomerOrderPositionMessage)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void create(CustomerOrderPositionMessage e) {
		logger.info(String.format("INVOKE: %s(%s)", "create", e.toString()));
		
		this.em.persist(e);
		
		logger.info("SAVEPOINT");
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionMessagesDAO#delete(de.warehouse.persistence.CustomerOrderPositionMessage)
	 */
	@Override
	public void delete(CustomerOrderPositionMessage e) {
		logger.info(String.format("INVOKE: %s(%s)", "delete", e.toString()));
		
		this.em.remove(e);
		
		logger.info("SAVEPOINT");
	}

	/**
	 * @see de.warehouse.dao.interfaces.ICommissionMessagesDAO#getPendingMessages()
	 */
	@Override
	public List<CustomerOrderPositionMessage> getPendingMessages() {
		logger.info(String.format("INVOKE: %s", "getPendingMessages"));
		
		return this.em.createQuery(
				"SELECT x FROM " + CustomerOrderPositionMessage.class.getSimpleName() + " x WHERE x.isEmailSent = 0",
				CustomerOrderPositionMessage.class).getResultList();
	}
}
