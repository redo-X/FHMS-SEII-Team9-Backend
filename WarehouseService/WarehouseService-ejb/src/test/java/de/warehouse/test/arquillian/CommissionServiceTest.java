package de.warehouse.test.arquillian;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.fail;

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
import de.warehouse.shared.interfaces.ICommissionService;
import de.warehouse.test.ArquillianTestWithSessionsBase;

/**
 * This test must test the authorization and authentication of the employees.
 * 
 * @author Florian
 *
 */
@RunWith(Arquillian.class)
public class CommissionServiceTest extends ArquillianTestWithSessionsBase {

	private final int VALID_CUSTOMER_ORDER_ID = 5;
	private final int VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER = 6;

	private final int VALID_PICKER_ID = 1;
	private final int VALID_POSITION_ONE_ID = 8;
	@EJB
	private ICommissionService commissionService;

	@Test
	public void testGetPendingCustomerOrdersWithoutPicker() {
		try {
			this.commissionService.getPendingCustomerOrdersWithoutPicker(super.sessionIdOfKommissionierer);
		} catch (SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetPendingCustomerOrdersByPickerId() {
		try {
			this.commissionService.getPendingCustomerOrdersByPickerId(super.sessionIdOfKommissionierer,
					VALID_PICKER_ID);
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetCustomerOrderById() {
		try {
			this.commissionService.getCustomerOrderById(super.sessionIdOfKommissionierer, VALID_CUSTOMER_ORDER_ID);
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetPositionsByCustomerOrderId() {
		try {
			this.commissionService.getPositionsByCustomerOrderId(super.sessionIdOfKommissionierer,
					VALID_CUSTOMER_ORDER_ID);
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetPendingPositionsByCustomerOrderId() {
		try {
			this.commissionService.getPendingPositionsByCustomerOrderId(super.sessionIdOfKommissionierer,
					VALID_CUSTOMER_ORDER_ID);
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testAllocateCustomerOrder() throws CustomerOrderAlreadyAllocatedException, EntityNotFoundException {
		try {
			this.commissionService.allocateCustomerOrder(super.sessionIdOfKommissionierer,
					VALID_CUSTOMER_ORDER_ID_WITHOUT_PICKER, VALID_PICKER_ID);
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(1)
	public void testUpdateStart()
			throws CustomerOrderCommissionAlreadyStartedException, CustomerOrderMustBeAllocateToPicker {
		try {
			this.commissionService.updateStart(super.sessionIdOfKommissionierer, VALID_CUSTOMER_ORDER_ID);
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(2)
	public void testUpdatePickedQuantity()
			throws NegativeQuantityException, PickedQuantityTooHighException, CustomerOrderMustBeAllocateToPicker {
		try {
			this.commissionService.updatePickedQuantity(super.sessionIdOfKommissionierer, VALID_POSITION_ONE_ID, 10);
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = CustomerOrderNotCompletelyCommissioned.class)
	@InSequence(3)
	public void testUpdateFinishWithPendingPositionsO() throws CustomerOrderCommissionAlreadyFinishedException,
			CustomerOrderMustBeAllocateToPicker, CustomerOrderNotCompletelyCommissioned {
		try {
			this.commissionService.updateFinish(super.sessionIdOfKommissionierer, VALID_CUSTOMER_ORDER_ID);
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(4)
	public void testUpdatePickedQuantityOfPendingPositions()
			throws NegativeQuantityException, PickedQuantityTooHighException, CustomerOrderMustBeAllocateToPicker {
		try {
			for (CustomerOrderPosition p : this.commissionService
					.getPendingPositionsByCustomerOrderId(super.sessionIdOfKommissionierer, VALID_CUSTOMER_ORDER_ID)) {
				this.commissionService.updatePickedQuantity(super.sessionIdOfKommissionierer, p.getCustomerOrderPositionId(), p.getRemainingQuantity());
			}
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@InSequence(5)
	public void testUpdateFinish() throws CustomerOrderCommissionAlreadyFinishedException,
			CustomerOrderMustBeAllocateToPicker, CustomerOrderNotCompletelyCommissioned {
		try {
			this.commissionService.updateFinish(super.sessionIdOfKommissionierer, VALID_CUSTOMER_ORDER_ID);
		} catch (AccessDeniedException | SessionExpiredException e) {
			fail(e.getMessage());
		}
	}
}
