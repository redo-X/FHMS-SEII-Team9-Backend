package de.warehouse.shared.interfaces;

import java.util.List;

import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.CustomerOrderMustBeAllocateToPicker;
import de.warehouse.shared.exceptions.CustomerOrderNotCompletelyCommissioned;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;

public interface ICommissionService {
	public List<CustomerOrder> getPendingCustomerOrdersWithoutPicker();
	public List<CustomerOrder> getPendingCustomerOrdersByPickerId(int pickerId);
		
	public CustomerOrder getCustomerOrderById(int customerOrderId);
	
	public List<CustomerOrderPosition> getPositionsByCustomerOrderId(int customerOrderId);
	public List<CustomerOrderPosition> getPendingPositionsByCustomerOrderId(int customerOrderId);
	
	public void allocateCustomerOrder(int customerOrderId, int employeeId) throws CustomerOrderAlreadyAllocatedException;
	
	public void updatePickedQuantity(int customerOrderPositionId, int pickedQuantity) throws NegativeQuantityException, PickedQuantityTooHighException, CustomerOrderMustBeAllocateToPicker;

	public void updateStart(int customerOrderId) throws CustomerOrderCommissionAlreadyStartedException, CustomerOrderMustBeAllocateToPicker;
	public void updateFinish(int customerOrderId) throws CustomerOrderCommissionAlreadyFinishedException, CustomerOrderMustBeAllocateToPicker, CustomerOrderNotCompletelyCommissioned;
	
	public void updateCommissionProgress(int customerOrderId);
	
	// TODO: Möglichkeit einen Misstand zu melden
	
}
