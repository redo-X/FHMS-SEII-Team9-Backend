package de.warehouse.shared.interfaces;

import java.util.List;

import de.warehouse.shared.CustomerOrder;
import de.warehouse.shared.CustomerOrderPosition;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;

public interface ICommissionService {
	public List<CustomerOrder> getIncompletedCustomerOrders();
	
	public CustomerOrder getCustomerOrderById(int customerOrderId);
	
	public List<CustomerOrderPosition> getPositionByCustomerOrderId(int customerOrderId);
	public List<CustomerOrderPosition> getPendingPositionByCustomerOrderId(int customerOrderId);
	
	public void allocateCustomerOrder(int customerOrderId, int employeeId) throws CustomerOrderAlreadyAllocatedException;
	
	public void updatePickedQuantity(int customerOrderPositionId, int pickedQuantity) throws NegativeQuantityException, PickedQuantityTooHighException;

	public void updateStart(int customerOrderId) throws CustomerOrderCommissionAlreadyStartedException;
	public void updateFinish(int customerOrderId) throws CustomerOrderCommissionAlreadyFinishedException;
}
