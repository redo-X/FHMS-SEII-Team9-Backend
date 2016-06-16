package de.warehouse.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.CustomerOrderMustBeAllocateToPicker;
import de.warehouse.shared.exceptions.CustomerOrderNotCompletelyCommissioned;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;

/**
 * This interface encapsulates the data access logic for the customer orders (in this business context commissions).
 * @author David
 */
@Local
public interface ICommissionDAO {
	/**
	 * Loads all customer orders from the database.
	 * @author David
	 * @return List of customer orders
	 * */
	public List<CustomerOrder> getCustomerOrders();
	/**
	 * Loads all pending customer orders from the database.
	 * Pending: Customer orders where the commission progress is not 100%.
	 * @author David
	 * @return List of customer orders
	 * */
	public List<CustomerOrder> getPendingCustomerOrders();
	/**
	 * Loads all pending and unallocated customer orders from the database.
	 * Pending: Customer orders where the commission progress is not 100%.
	 * @author David
	 * @return List of customer orders
	 * */
	public List<CustomerOrder> getPendingCustomerOrdersWithoutPicker();
	/**
	 * Loads all pending and allocated customer orders from the database.
	 * Pending: Customer orders where the commission progress is not 100%.
	 * @author David
	 * @param employeeId Identifier of the employee (in this business context: picker)
	 * @return List of customer orders
	 * */
	public List<CustomerOrder> getPendingCustomerOrdersByEmployeeId(int employeeId);
	
	/**
	 * Try to find a customer order based on the given identity field.
	 * @author David
	 * @param customerOrderId Identifier of the customer order
	 * @return Instance of customer order without related neighbors
	 * */
	public CustomerOrder getCustomerOrderById(int customerOrderId);
	/**
	 * Try to find a customer order position based on the given identity field.
	 * @author David
	 * @param customerOrderPositionId Identifier of the customer order
	 * @return Instance of customer order position 
	 * */
	public CustomerOrderPosition getCustomerOrderPositionById(int customerOrderPositionId);
	/**
	 * Try to find a customer order based on the given identity field.
	 * @author David
	 * @param customerOrderId Identifier of the customer order
	* @return Instance of customer order with related neighbors
	 * */
	public CustomerOrder getCustomerOrderWithPickerById(int customerOrderId);
	/**
	 * Loads all positions of the customer order.
	 * @author David
	 * @param customerOrderId Identifier of the customer order
	 * @return List of customer order positions
	 */
	public List<CustomerOrderPosition> getPositionsByCustomerOrderId(int customerOrderId);
	/**
	 * Loads all pending positions of the customer order.
	 * Pending: Customer orders where the commission progress is not 100%.
	 * @author David
	 * @param customerOrderId Identifier of the customer order
	 * @return List of customer order positions
	 */
	public List<CustomerOrderPosition> getPendingPositionsByCustomerOrderId(int customerOrderId);
	/**
	 * Allocate the customer order to the given employee (in this business context: picker).
	 * @param customerOrderId Identifier of the customer order
	 * @param employeeId Identifier of the employee (in this business context: picker)
	 * @throws CustomerOrderAlreadyAllocatedException if customer order already allocated
	 */
	public void allocateCustomerOrder(int customerOrderId, int employeeId) throws CustomerOrderAlreadyAllocatedException;
	/**
	 * Update the picked quantity of a position in a customer order.
	 * @param customerOrderPositionId Identifier of the customer order position to be updated
	 * @param pickedQuantity Quantity that was picked by the employee (in this business context: picker)
	 * @throws NegativeQuantityException pickedQuantity must be greater or equals 0
	 * @throws PickedQuantityTooHighException pickedQuantity + already picked Quantity from the past must be less than the ordered quantity
	 * @throws CustomerOrderMustBeAllocateToPicker customer order must be allocated to an employee (in this business context: picker) to do the commission
	 */
	public void updatePickedQuantity(int customerOrderPositionId, int pickedQuantity) throws NegativeQuantityException, PickedQuantityTooHighException, CustomerOrderMustBeAllocateToPicker;
	/**
	 * Set a date and time value to track the starting point of the process.
	 * @param customerOrderId Identifier of the customer order
	 * @throws CustomerOrderCommissionAlreadyStartedException if the customer order already been allocated to an employee
	 * @throws CustomerOrderMustBeAllocateToPicker customer order must be allocated to an employee (in this business context: picker) first
	 */
	public void updateStart(int customerOrderId) throws CustomerOrderCommissionAlreadyStartedException, CustomerOrderMustBeAllocateToPicker;
	/**
	 * Set a date and time value to track the ending point of the process.
	 * @param customerOrderId Identifier of the customer order
	 * @throws CustomerOrderCommissionAlreadyFinishedException if the customer order already been finished
	 * @throws CustomerOrderMustBeAllocateToPicker customer order must be allocated to an employee (in this business context: picker) first
	 */
	public void updateFinish(int customerOrderId) throws CustomerOrderCommissionAlreadyFinishedException, CustomerOrderMustBeAllocateToPicker, CustomerOrderNotCompletelyCommissioned;
	/**
	 * Calculates the commission progress based on the picked quantity of the positions
	 * @param customerOrderId Identifier of the customer order
	 */
	public void updateCommissionProgress(int customerOrderId);
}
