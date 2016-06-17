package de.warehouse.test.legacy;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.warehouse.persistence.Customer;
import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.persistence.Employee;
import de.warehouse.shared.utils.DateUtil;

/**
 * @author David
 *
 */
public class CustomerOrderTests {

	// CustomerOrder with two positions and 10 ordered quantity
	private CustomerOrder orderWithPositions;

	@Before
	public void prepareCustomerOrder() throws Exception {
		Employee e = new Employee(10, "Max", "Mustermann");
		
		orderWithPositions = new CustomerOrder(1000);
		
		orderWithPositions.setPicker(e);

		CustomerOrderPosition pos1 = new CustomerOrderPosition(2000);
		pos1.setOrder(orderWithPositions);
		pos1.setOrderedQuantity(10);
		pos1.setPickedQuantity(0);
		
		CustomerOrderPosition pos2 = new CustomerOrderPosition(2001);
		pos2.setOrder(orderWithPositions);
		pos2.setOrderedQuantity(10);
		pos2.setPickedQuantity(0);
	}

	@Test
	public void testIsOverdueFalse() {
		Date now = DateUtil.getCurrentDate();
		
		CustomerOrder order = new CustomerOrder(1000, now, DateUtil.addDays(now, 10));
		Customer cust = new Customer();

		cust.setDeliveryToleranceInDays(0);

		order.setCustomer(cust);

		assertEquals(false, order.isOverdue());
	}

	@Test
	public void testIsOverdueTrue() {
		CustomerOrder order = new CustomerOrder(1000, DateUtil.getCurrentDate(), DateUtil.getCurrentDate());
		Customer cust = new Customer();

		cust.setDeliveryToleranceInDays(0);

		order.setCustomer(cust);

		assertEquals(true, order.isOverdue());
	}

	@Test
	public void testUpdateProgressIsZeroPercent() {
		assertEquals(0, this.orderWithPositions.getProgress(), 0);
	}

	@Test
	public void testUpdateProgressIsTwentyFivePercent() throws Exception {
		// Progress = 0,2
		this.orderWithPositions.getPositions().get(2000).setPickedQuantity(2);
		// Progress = 0,3
		this.orderWithPositions.getPositions().get(2001).setPickedQuantity(3);

		// Average progress must be 0,25
		assertEquals(0.25, this.orderWithPositions.getProgress(), 0);
	}

	@Test
	public void testUpdateProgressIsFiftyPercent() throws Exception {
		this.orderWithPositions.getPositions().get(2000).setPickedQuantity(5);
		this.orderWithPositions.getPositions().get(2001).setPickedQuantity(5);

		assertEquals(0.5, this.orderWithPositions.getProgress(), 0);
	}

	@Test
	public void testUpdateProgressIsHundredPercent() throws Exception {
		this.orderWithPositions.getPositions().get(2000).setPickedQuantity(10);
		this.orderWithPositions.getPositions().get(2001).setPickedQuantity(10);

		assertEquals(1, this.orderWithPositions.getProgress(), 0);
	}

	@Test
	public void testCommissionProgressIsZeroPercent() {
		this.orderWithPositions.updateProgress();

		assertEquals(0, this.orderWithPositions.getCommissionProgress(), 0);
	}

	@Test
	public void testCommissionProgressIsTwentyFivePercent() throws Exception {
		// Progress = 0,2
		this.orderWithPositions.getPositions().get(2000).setPickedQuantity(2);
		// Progress = 0,3
		this.orderWithPositions.getPositions().get(2001).setPickedQuantity(3);

		this.orderWithPositions.updateProgress();

		// Average progress must be 0,25
		assertEquals(0.25, this.orderWithPositions.getCommissionProgress(), 0);
	}

	@Test
	public void testCommissionProgressIsFiftyPercent() throws Exception {
		this.orderWithPositions.getPositions().get(2000).setPickedQuantity(5);
		this.orderWithPositions.getPositions().get(2001).setPickedQuantity(5);

		this.orderWithPositions.updateProgress();

		assertEquals(0.5, this.orderWithPositions.getCommissionProgress(), 0);
	}

	@Test
	public void testCommissionProgressIsHundredPercent() throws Exception  {
		this.orderWithPositions.getPositions().get(2000).setPickedQuantity(10);
		this.orderWithPositions.getPositions().get(2001).setPickedQuantity(10);

		this.orderWithPositions.updateProgress();

		assertEquals(1, this.orderWithPositions.getCommissionProgress(), 0);
	}

	@Test
	public void testGetPendingPositionCount() {
		int pendingPosCount = this.orderWithPositions.getPendingPositionCount();

		assertEquals(2, pendingPosCount, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetNegativeCommissionProgress() {
		this.orderWithPositions.setCommissionProgress(-1);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetCommissionProgressToTwo() {
		this.orderWithPositions.setCommissionProgress(2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetOrderDateInPast() {
		this.orderWithPositions.setOrderDate(DateUtil.addDays(DateUtil.getCurrentDate(), -10));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetDueDateInPast() {
		this.orderWithPositions.setDueDate(DateUtil.addDays(DateUtil.getCurrentDate(), -10));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetDueDateBeforeOrderDate() {
		CustomerOrder order = new CustomerOrder(1000, DateUtil.getDateFromParts(2016, 3, 1), DateUtil.getDateFromParts(2016, 3, 1));
		
		order.setDueDate(DateUtil.getDateFromParts(2016, 2, 1));
	}
}