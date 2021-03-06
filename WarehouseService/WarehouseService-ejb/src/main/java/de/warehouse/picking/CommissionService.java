package de.warehouse.picking;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import de.warehouse.dao.interfaces.ICommissionDAO;
import de.warehouse.messaging.OutputRequesterBean;
import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.persistence.Role;
import de.warehouse.shared.DocTypes;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.CustomerOrderMustBeAllocateToPicker;
import de.warehouse.shared.exceptions.CustomerOrderNotCompletelyCommissioned;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.ICommissionService;
import de.warehouse.shared.interfaces.ISessionManagement;

/**
 * @author David
 */
@Stateless
@Remote(ICommissionService.class)
public class CommissionService implements ICommissionService {

	private static final Logger logger = Logger.getLogger(CommissionService.class);

	@EJB
	private ISessionManagement sessionManagementBean;

	@EJB
	private ICommissionDAO commissionDAO;

	@EJB
	private OutputRequesterBean requesterBean;

	/**
	 * @throws SessionExpiredException 
	 * @see de.warehouse.shared.interfaces.ICommissionService#getPendingCustomerOrdersWithoutPicker()
	 */
	@Override
	@Lock(LockType.READ)
	public List<CustomerOrder> getPendingCustomerOrdersWithoutPicker(int sessionId) throws SessionExpiredException, AccessDeniedException {
				
		logger.info(String.format("INVOKE: %s", "getPendingCustomerOrdersWithoutPicker"));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		return this.commissionDAO.getPendingCustomerOrdersWithoutPicker();
	}
	/**
	 * @see de.warehouse.shared.interfaces.ICommissionService#getPendingCustomerOrdersByPickerId(int)
	 */
	@Override
	@Lock(LockType.READ)
	public List<CustomerOrder> getPendingCustomerOrdersByPickerId(int sessionId, int pickerId) throws AccessDeniedException, SessionExpiredException {

		logger.info(String.format("INVOKE: %s(%d)", "getPendingCustomerOrdersByPickerId", pickerId));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		return this.commissionDAO.getPendingCustomerOrdersByEmployeeId(pickerId);
	}
	/**
	 * @throws AccessDeniedException 
	 * @throws SessionExpiredException 
	 * @see de.warehouse.shared.interfaces.ICommissionService#getCustomerOrderById(int)
	 */
	@Override
	@Lock(LockType.READ)
	public CustomerOrder getCustomerOrderById(int sessionId, int customerOrderId) throws SessionExpiredException, AccessDeniedException {
		logger.info(String.format("INVOKE: %s(%d)", "getCustomerOrderById", customerOrderId));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		return this.commissionDAO.getCustomerOrderById(customerOrderId);
	}
	/**
	 * @see de.warehouse.shared.interfaces.ICommissionService#getPositionsByCustomerOrderId(int)
	 */
	@Override
	@Lock(LockType.READ)
	public List<CustomerOrderPosition> getPositionsByCustomerOrderId(int sessionId, int customerOrderId) throws SessionExpiredException, AccessDeniedException {
		
		logger.info(String.format("INVOKE: %s(%d)", "getPositionsByCustomerOrderId", customerOrderId));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		return this.commissionDAO.getPositionsByCustomerOrderId(customerOrderId);
	}
	/**
	 * @see de.warehouse.shared.interfaces.ICommissionService#getPendingPositionsByCustomerOrderId(int)
	 */
	@Override
	public List<CustomerOrderPosition> getPendingPositionsByCustomerOrderId(int sessionId, int customerOrderId) throws SessionExpiredException, AccessDeniedException {
		logger.info(String.format("INVOKE: %s(%d)", "getPendingPositionsByCustomerOrderId", customerOrderId));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		return this.commissionDAO.getPendingPositionsByCustomerOrderId(customerOrderId);
	}
	/**
	 * @see de.warehouse.shared.interfaces.ICommissionService#allocateCustomerOrder(int, int)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void allocateCustomerOrder(int sessionId, int customerOrderId, int employeeId)
			throws CustomerOrderAlreadyAllocatedException, EntityNotFoundException, SessionExpiredException, AccessDeniedException {
		logger.info(String.format("INVOKE: %s(%d, %d)", "allocateCustomerOrder", customerOrderId, employeeId));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		this.commissionDAO.allocateCustomerOrder(customerOrderId, employeeId);
		
		CustomerOrder order = this.commissionDAO.getCustomerOrderWithPickerById(customerOrderId);

		String recipient = order.getPicker().getMailAddress();
		String subject = "INFO: Kommissionen";
		String body = String.format("Commission %d was allocated to you.", order.getCode());

		this.requesterBean.sendInfo(DocTypes.CommissionInfo.name(), recipient, subject, body);
	}
	/**
	 * @see de.warehouse.shared.interfaces.ICommissionService#updatePickedQuantity(int, int)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void updatePickedQuantity(int sessionId, int customerOrderPositionId, int pickedQuantity)
			throws NegativeQuantityException, PickedQuantityTooHighException, CustomerOrderMustBeAllocateToPicker, SessionExpiredException, AccessDeniedException {
		logger.info(String.format("INVOKE: %s(%d, %d)", "updatePickedQuantity", customerOrderPositionId, pickedQuantity));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		this.commissionDAO.updatePickedQuantity(customerOrderPositionId, pickedQuantity);
	}
	/**
	 * @see de.warehouse.shared.interfaces.ICommissionService#updateStart(int)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void updateStart(int sessionId, int customerOrderId)
			throws CustomerOrderCommissionAlreadyStartedException, CustomerOrderMustBeAllocateToPicker, SessionExpiredException, AccessDeniedException {
		logger.info(String.format("INVOKE: %s(%d)", "updateStart", customerOrderId));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		this.commissionDAO.updateStart(customerOrderId);

		CustomerOrder order = this.commissionDAO.getCustomerOrderWithPickerById(customerOrderId);

		String recipient = order.getPicker().getMailAddress();
		String subject = "INFO: Kommissionen";
		String body = String.format("Commission %d started at %s by %s.", order.getCode(),
				order.getStartOfCommission().toString(), order.getPicker().getFullName());

		this.requesterBean.sendInfo(DocTypes.CommissionInfo.name(), recipient, subject, body);
	}
	/**
	 * @see de.warehouse.shared.interfaces.ICommissionService#updateFinish(int)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void updateFinish(int sessionId, int customerOrderId)
			throws CustomerOrderCommissionAlreadyFinishedException, CustomerOrderMustBeAllocateToPicker, CustomerOrderNotCompletelyCommissioned, SessionExpiredException, AccessDeniedException {

		logger.info(String.format("INVOKE: %s(%d)", "updateFinish", customerOrderId));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		this.commissionDAO.updateFinish(customerOrderId);
		
		CustomerOrder order = this.commissionDAO.getCustomerOrderWithPickerById(customerOrderId);

		String recipient = order.getPicker().getMailAddress();
		String subject = "INFO: Kommissionen";
		String body = String.format("Commission %d finished at %s by %s.", order.getCode(),
				order.getFinishOfCommission().toString(), order.getPicker().getFullName());

		this.requesterBean.sendInfo(DocTypes.CommissionInfo.name(), recipient, subject, body);
	}
	/**
	 * @see de.warehouse.shared.interfaces.ICommissionService#updateCommissionProgress(int)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void updateCommissionProgress(int sessionId, int customerOrderId) throws SessionExpiredException, AccessDeniedException {
		logger.info(String.format("INVOKE: %s(%d)", "updateCommissionProgress", customerOrderId));
		
		this.sessionManagementBean.ensureAuthorization(Role.Kommissionierer, sessionId);
		
		this.commissionDAO.updateCommissionProgress(customerOrderId);
	}
}
