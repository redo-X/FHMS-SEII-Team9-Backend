package de.warehouse.test.shared.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.warehouse.shared.util.DateUtil;



public class DateUtilTests {

	private Calendar cal;
	
	@Before
	public void prepareCalendar() {
		cal = Calendar.getInstance();
		cal.set(2015, Calendar.JANUARY, 1, 0, 0, 0);
	}
	
	@Test
	public void testGetDiffInYearsDifferentDates() {
		Date d1 = cal.getTime();
		cal.set(2016, Calendar.JANUARY, 1, 0, 0, 0);
		Date d2 = cal.getTime();
		
		assertEquals(1, DateUtil.getDiffYears(d1, d2));
	}
	@Test
	public void testGetDiffInYearsSameDates() {
		Date d1 = cal.getTime();
		cal.set(2015, Calendar.JANUARY, 1, 0, 0, 0);
		Date d2 = cal.getTime();
		
		assertEquals(0, DateUtil.getDiffYears(d1, d2));
	}

	@Test
	public void testAddDayWithinMonth() {
		Date d1 = cal.getTime();
		cal.set(2015, Calendar.JANUARY, 2, 0, 0, 0);
		Date d2 = cal.getTime();
		
		assertEquals(d2, DateUtil.addDays(d1, 1));
	}
	@Test
	public void testAddDayWithOverflowMonth() {
		Date d1 = cal.getTime();
		cal.set(2015, Calendar.FEBRUARY, 1, 0, 0, 0);
		Date d2 = cal.getTime();
		
		assertEquals(d2, DateUtil.addDays(d1, 31));
	}
}
