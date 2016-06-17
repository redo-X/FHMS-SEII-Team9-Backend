package de.warehouse.shared.interfaces;

import java.util.List;

import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
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

public interface ICommissionService {
	public List<CustomerOrder> getPendingCustomerOrdersWithoutPicker(int sessionId) throws SessionExpiredException, AccessDeniedException;
	public List<CustomerOrder> getPendingCustomerOrdersByPickerId(int sessionId, int pickerId) throws AccessDeniedException, SessionExpiredException;
		
	public CustomerOrder getCustomerOrderById(int sessionId, int customerOrderId) throws AccessDeniedException, SessionExpiredException;
	
	public List<CustomerOrderPosition> getPositionsByCustomerOrderId(int sessionId, int customerOrderId) throws AccessDeniedException, SessionExpiredException;
	public List<CustomerOrderPosition> getPendingPositionsByCustomerOrderId(int sessionId, int customerOrderId)throws AccessDeniedException, SessionExpiredException;
	
	public void allocateCustomerOrder(int sessionId, int customerOrderId, int employeeId) throws CustomerOrderAlreadyAllocatedException, EntityNotFoundException, AccessDeniedException, SessionExpiredException;
	
	public void updatePickedQuantity(int sessionId, int customerOrderPositionId, int pickedQuantity) throws NegativeQuantityException, PickedQuantityTooHighException, CustomerOrderMustBeAllocateToPicker, AccessDeniedException, SessionExpiredException;

	public void updateStart(int sessionId, int customerOrderId) throws CustomerOrderCommissionAlreadyStartedException, CustomerOrderMustBeAllocateToPicker, AccessDeniedException, SessionExpiredException;
	public void updateFinish(int sessionId, int customerOrderId) throws CustomerOrderCommissionAlreadyFinishedException, CustomerOrderMustBeAllocateToPicker, CustomerOrderNotCompletelyCommissioned, AccessDeniedException, SessionExpiredException;
	
	public void updateCommissionProgress(int sessionId, int customerOrderId) throws AccessDeniedException, SessionExpiredException;
}
