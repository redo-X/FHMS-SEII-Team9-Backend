package de.warehouse.test.shared;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import de.warehouse.shared.CustomerOrder;
import de.warehouse.shared.util.DateUtil;

public class CustomerOrderTests {
	
	@Test
	public void testIsOverdueFalse() {
		CustomerOrder order = new CustomerOrder();
		
		Date now = DateUtil.getCurrentDate();
		
		order.setDueDate(new Date(now.getYear() - 10, 1, 1));
		
		assertEquals(false, order.isOverdue());
	}
	@Test
	public void testIsOverdueTrue() {
		CustomerOrder order = new CustomerOrder();
		
		order.setDueDate(DateUtil.getDateFromParts(2000, 1, 1));
		
		assertEquals(true, order.isOverdue());
	}
}
