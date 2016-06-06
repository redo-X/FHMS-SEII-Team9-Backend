package de.warehouse.shared.interfaces;

import de.warehouse.shared.CustomerOrder;
import de.warehouse.shared.CustomerOrderPosition;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;

public interface ICommissionService {
	public CustomerOrder[] getIncompletedCustomerOrders();
	
	public CustomerOrder getCustomerOrderById(int customerOrderId);
	
	public CustomerOrderPosition[] getPositionByCustomerOrderId(int customerOrderId);
	
	public void allocateCustomerOrder(int customerOrderId, int employeeId) throws CustomerOrderAlreadyAllocatedException;
	
	public void updatePickedQuantity(int customerOrderPositionId, int pickedQuantity) throws NegativeQuantityException, PickedQuantityTooHighException;

	public void updateStart(int customerOrderPositionId) throws CustomerOrderCommissionAlreadyStartedException;
	public void updateFinish(int customerOrderPositionId) throws CustomerOrderCommissionAlreadyFinishedException;
}
