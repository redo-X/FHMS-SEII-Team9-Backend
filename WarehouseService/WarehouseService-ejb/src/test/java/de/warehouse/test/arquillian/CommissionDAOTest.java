package de.warehouse.test.arquillian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRequiredException;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.warehouse.dao.interfaces.ICommissionDAO;
import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyFinishedException;
import de.warehouse.shared.exceptions.CustomerOrderCommissionAlreadyStartedException;
import de.warehouse.shared.exceptions.CustomerOrderMustBeAllocateToPicker;
import de.warehouse.shared.exceptions.CustomerOrderNotCompletelyCommissioned;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;
import de.warehouse.test.ArquillianTestWithSessionsBase;

@RunWith(Arquillian.class)
public class CommissionDAOTest extends ArquillianTestWithSessionsBase {


	private final int VALID_CUSTOMER_ORDER_ID = 5;
	private final int VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER = 6;
	
	private final int VALID_PICKER_ID = 1;
	private final int VALID_PICKER_2_ID = 2;
	private final int INVALID_PICKER_ID = 5;
	
	private final int VALID_POSITION_ONE_ID = 8;
	private final int VALID_POSITION_TWO_ID = 9;
	private final int VALID_POSITION_THREE_ID = 10;

	@EJB
	private ICommissionDAO commissionDAO;

	
	@Test
	public void testGetCustomerOrders() {
		assertEquals(3, this.commissionDAO.getCustomerOrders().size(), 0);
	}

	@Test
	public void testGetPendingCustomerOrders() {
		assertEquals(3, this.commissionDAO.getPendingCustomerOrders().size(), 0);
	}
	
	@Test(expected=EJBTransactionRequiredException.class)
	public void testUpdateCommissionProgressWithoutTransactionContext() {
		this.commissionDAO.updateCommissionProgress(VALID_CUSTOMER_ORDER_ID);
	}

	@Test
	public void testGetPendingCustomerOrdersWithoutPicker() {
		assertEquals(0, this.commissionDAO.getPendingCustomerOrdersWithoutPicker().size(), 0);
	}

	@Test
	public void testGetPendingCustomerOrdersByEmployeeIdWithValid() {
		assertEquals(1, this.commissionDAO.getPendingCustomerOrdersByEmployeeId(VALID_PICKER_ID).size(), 0);
	}
	@Test
	public void testGetPendingCustomerOrdersByEmployeeIdWithInvalid() {
		assertEquals(0, this.commissionDAO.getPendingCustomerOrdersByEmployeeId(INVALID_PICKER_ID).size(), 0);
	}
	
	@Test
	public void testGetCustomerOrderById() {
		try {
		    CustomerOrder customerOrder = this.commissionDAO.getCustomerOrderById(VALID_CUSTOMER_ORDER_ID);
		    
		    assertNotNull(customerOrder);
		    
		    // David: will fail because the entity is in detached state and the proxy is "disposed"
		    assertNull("Picker must be lazy!", customerOrder.getPicker());
		}catch(Exception e) {
			/* 
			 * David:
			 * LazyInitializationException is expected, but we didnt't want to add the hibernate dependency.
			 * So we choose this workaround to detect if the picker is lazy.
			 */
		}
	}
	@Test
	public void testGetCustomerOrderWithPickerById() {
	    CustomerOrder customerOrder = this.commissionDAO.getCustomerOrderWithPickerById(VALID_CUSTOMER_ORDER_ID);
	    
	    assertNotNull(customerOrder);
	    
	    assertNotNull("Picker must not be lazy!", customerOrder.getPicker());
	}

	@Test(expected=CustomerOrderAlreadyAllocatedException.class)
	public void testAllocateCustomerOrderWithAllocatedCustomerOrder() throws CustomerOrderAlreadyAllocatedException{
		try {
			this.commissionDAO.allocateCustomerOrder(VALID_CUSTOMER_ORDER_ID, VALID_PICKER_2_ID);
		} catch (EntityNotFoundException e) {
			fail(e.getMessage());
		}
	}
	@Test(expected=CustomerOrderMustBeAllocateToPicker.class)
	public void testUpdateStartWithUnallocatedCustomerOrder() throws CustomerOrderMustBeAllocateToPicker {
		try {
			this.commissionDAO.updateStart(VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER);
		} catch (CustomerOrderCommissionAlreadyStartedException e) {
			fail(e.getMessage());
		}
	}
	@Test(expected=CustomerOrderMustBeAllocateToPicker.class)
	public void testUpdateFinishWithUnallocatedCustomerOrder() throws CustomerOrderMustBeAllocateToPicker {
		try {
			this.commissionDAO.updateFinish(VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER);
		} catch (CustomerOrderCommissionAlreadyFinishedException | CustomerOrderNotCompletelyCommissioned e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(1)
	public void testAllocateCustomerOrder() {
		try {
			this.commissionDAO.allocateCustomerOrder(VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER, VALID_PICKER_2_ID);
		} catch (CustomerOrderAlreadyAllocatedException | EntityNotFoundException e) {
			fail(e.getMessage());
		}
	}
	@Test
	@InSequence(2)
	public void testUpdateStart() {
		try {
			this.commissionDAO.updateStart(VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER);
		} catch (CustomerOrderCommissionAlreadyStartedException | CustomerOrderMustBeAllocateToPicker e) {
			fail(e.getMessage());
		}
	}
	@Test(expected=CustomerOrderCommissionAlreadyStartedException.class)
	@InSequence(3)
	public void testUpdateStartWithStartedCustomerOrder() throws CustomerOrderCommissionAlreadyStartedException {
		try {
			this.commissionDAO.updateStart(VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER);
		} catch (CustomerOrderMustBeAllocateToPicker e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@InSequence(4)
	public void testUpdateFinishWithStartedCustomerOrder()  {
		try {
			this.commissionDAO.updateFinish(VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER);
		} catch (CustomerOrderCommissionAlreadyFinishedException | CustomerOrderMustBeAllocateToPicker | CustomerOrderNotCompletelyCommissioned e) {
			fail(e.getMessage());
		}
	}	
	@Test(expected=CustomerOrderCommissionAlreadyFinishedException.class)
	@InSequence(5)
	public void testUpdateFinishWithFinishedCustomerOrder() throws CustomerOrderCommissionAlreadyFinishedException  {
		try {
			this.commissionDAO.updateFinish(VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER);
		} catch (CustomerOrderMustBeAllocateToPicker | CustomerOrderNotCompletelyCommissioned e) {
			fail(e.getMessage());
		}
	}
	
	//
	// BEGIN OF: POSITION TESTS
	//
	
	@Test
	public void testGetPositionsByCustomerOrderId() {
		assertEquals(3, this.commissionDAO.getPositionsByCustomerOrderId(VALID_CUSTOMER_ORDER_ID).size(), 0);
	}
	@Test
	public void testGetPendingPositionsByCustomerOrderId() {
		assertEquals(3, this.commissionDAO.getPendingPositionsByCustomerOrderId(VALID_CUSTOMER_ORDER_ID).size(), 0);
	}
	
	// New Test Sequence beginning at 10
	@Test
	@InSequence(10)
	public void testUpdateStartOfCustomerOrderWithPositions()  {
		try {
			this.commissionDAO.updateStart(VALID_CUSTOMER_ORDER_ID);
		} catch (CustomerOrderMustBeAllocateToPicker | CustomerOrderCommissionAlreadyStartedException e) {
			fail(e.getMessage());
		}
	}
	@Test
	@InSequence(11)
	public void testUpatePickedQuantity() {
		try {
			this.commissionDAO.updatePickedQuantity(VALID_POSITION_ONE_ID, 10);
		} catch (NegativeQuantityException | PickedQuantityTooHighException | CustomerOrderMustBeAllocateToPicker e) {
			fail(e.getMessage());
		}
	}
	@Test(expected=NegativeQuantityException.class)
	@InSequence(11)
	public void testUpatePickedQuantityTrySetNegativeQuantity() throws NegativeQuantityException {
		try {
			this.commissionDAO.updatePickedQuantity(VALID_POSITION_ONE_ID, -10);
		} catch (PickedQuantityTooHighException | CustomerOrderMustBeAllocateToPicker e) {
			fail(e.getMessage());
		}
	}
	
	@Test(expected=PickedQuantityTooHighException.class)
	@InSequence(12)
	public void testUpatePickedQuantityTrySetTooHighQuantity() throws PickedQuantityTooHighException {
		try {
			this.commissionDAO.updatePickedQuantity(VALID_POSITION_TWO_ID, 100);
		} catch (NegativeQuantityException | CustomerOrderMustBeAllocateToPicker e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@InSequence(13)
	public void testGetPendingPositionsByCustomerOrderIdForModifiedCustomerOrder() {
		assertEquals("Must be 2 pending positions", 2, this.commissionDAO.getPendingPositionsByCustomerOrderId(VALID_CUSTOMER_ORDER_ID).size(), 0);
	}
	@Test(expected=CustomerOrderNotCompletelyCommissioned.class)
	@InSequence(14)
	public void testUpdateFinishOfCustomerOrderWithPendingPositions() throws CustomerOrderNotCompletelyCommissioned {
		try {
			this.commissionDAO.updateFinish(VALID_CUSTOMER_ORDER_ID);
		} catch (CustomerOrderMustBeAllocateToPicker | CustomerOrderCommissionAlreadyFinishedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@InSequence(15)
	public void testUpatePickedQuantityOfOtherPositionsToHundredPercent() {
		try {
			this.commissionDAO.updatePickedQuantity(VALID_POSITION_TWO_ID, 5);
			this.commissionDAO.updatePickedQuantity(VALID_POSITION_THREE_ID, 25);
		} catch (NegativeQuantityException | PickedQuantityTooHighException | CustomerOrderMustBeAllocateToPicker e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@InSequence(16)
	public void testGetDateOfCommissionOfCustomerOrderPositionIsSettedAfterCommission() {
		CustomerOrderPosition pos = this.commissionDAO.getCustomerOrderPositionById(VALID_POSITION_ONE_ID);
		
		assertNotNull(pos);		
		assertNotNull(pos.getDateOfCommission());		
		assertEquals("Must be 100% => 1", 1, pos.getProgress(), 0);
	}
	
	@Test
	@InSequence(17)
	public void testUpdateFinishOfCustomerOrderWithPositions()  {
		try {
			this.commissionDAO.updateFinish(VALID_CUSTOMER_ORDER_ID);
		} catch (CustomerOrderMustBeAllocateToPicker | CustomerOrderCommissionAlreadyFinishedException | CustomerOrderNotCompletelyCommissioned e) {
			fail(e.getMessage());
		}
	}
	@Test
	@InSequence(18)
	public void testCommissionProgressIsHundredPercent()  {
		assertEquals("Must be 100% => 1", 1, this.commissionDAO.getCustomerOrderById(VALID_CUSTOMER_ORDER_ID).getCommissionProgress(), 0);
	}
}
