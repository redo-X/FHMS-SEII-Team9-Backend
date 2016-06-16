package de.warehouse.test.legacy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.warehouse.persistence.CustomerOrder;
import de.warehouse.persistence.CustomerOrderPosition;
import de.warehouse.persistence.Employee;
import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.exceptions.CustomerOrderMustBeAllocateToPicker;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;

public class CustomerOrderPositionTests {

	private CustomerOrderPosition position1;
	private CustomerOrderPosition position2;

	@Before
	public void prepareCustomerOrder() throws Exception {
		
		Employee e = new Employee(10, "Max", "Mustermann");
		
		CustomerOrder orderWithPositions = new CustomerOrder(1000);
		
		orderWithPositions.setPicker(e);
		
		this.position1 = new CustomerOrderPosition(2000);
		this.position1.setOrder(orderWithPositions);
		this.position1.setOrderedQuantity(10);
		this.position1.setPickedQuantity(0);
		
		
		this.position2 = new CustomerOrderPosition(2001);
		this.position2.setOrder(orderWithPositions);
		this.position2.setOrderedQuantity(10);
		this.position2.setPickedQuantity(0);
	}
	
	@Test
	public void testPositionsProgrssIsZeroPercent() {
		assertEquals("Position #1 progress is not zero.", 0, this.position1.getProgress(), 0);
		assertEquals("Position #2 progress is not zero.", 0, this.position2.getProgress(), 0);
	}	
	@Test
	public void testPositionsProgrssIsFiftyPercent() {
		try {
			this.position1.setPickedQuantity(5);
		} catch (NegativeQuantityException | CustomerOrderMustBeAllocateToPicker | PickedQuantityTooHighException e) {
			fail(e.getMessage());
		}
		
		assertEquals("Position #1 progress is not 0,5.", 0.5, this.position1.getProgress(), 0);
	}	
	@Test
	public void testPositionsProgrssIsHundredPercent() {
		try {
			this.position1.setPickedQuantity(10);
		} catch (NegativeQuantityException | CustomerOrderMustBeAllocateToPicker | PickedQuantityTooHighException e) {
			fail(e.getMessage());
		}
		
		assertEquals("Position #1 progress is not 1,0.", 1, this.position1.getProgress(), 0);
	}
	
	@Test(expected=CustomerOrderMustBeAllocateToPicker.class)
	public void testSetPickedQuantityAtUnallocatedCustomerOrder() throws CustomerOrderMustBeAllocateToPicker, CustomerOrderAlreadyAllocatedException {
		try {
			this.position1.getOrder().setPicker(null);
			this.position1.setPickedQuantity(20);
		} catch (NegativeQuantityException | PickedQuantityTooHighException e) {
			fail(e.getMessage());
		}
	}
	@Test(expected=NegativeQuantityException.class)
	public void testSetPickedQuantityLessThanZero() throws NegativeQuantityException {
		try {
			this.position1.setPickedQuantity(-10);
		} catch (CustomerOrderMustBeAllocateToPicker | PickedQuantityTooHighException e) {
			fail(e.getMessage());
		}
	}
	@Test(expected=PickedQuantityTooHighException.class)
	public void testSetPickedQuantityGreaterThanOrderedQuantity() throws PickedQuantityTooHighException {
		try {
			this.position1.setPickedQuantity(20);
		} catch (NegativeQuantityException | CustomerOrderMustBeAllocateToPicker e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testRemainingQuantityIsZero() {
		try {
			this.position1.setPickedQuantity(10);
		} catch (NegativeQuantityException | CustomerOrderMustBeAllocateToPicker | PickedQuantityTooHighException e) {
			fail(e.getMessage());
		}
		
		assertEquals("Position #1 remaining quantity is not zero.", 0, this.position1.getRemainingQuantity(), 0);
	}
	@Test
	public void testRemainingQuantityIsFive() {
		try {
			this.position1.setPickedQuantity(5);
		} catch (NegativeQuantityException | CustomerOrderMustBeAllocateToPicker | PickedQuantityTooHighException e) {
			fail(e.getMessage());
		}
		
		assertEquals("Position #1 remaining quantity is not five.", 5, this.position1.getRemainingQuantity(), 0);
	}
	@Test
	public void testRemainingQuantityIsTen() {		
		assertEquals("Position #1 remaining quantity is not ten.", 10, this.position1.getRemainingQuantity(), 0);
	}
}