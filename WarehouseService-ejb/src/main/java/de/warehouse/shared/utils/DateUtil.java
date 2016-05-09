/**
 * 
 */
package de.warehouse.shared.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;


/**
 * @author David
 * Helper Methods to calculate with Java based Dates
 */
public class DateUtil {
	
	public static Date getCurrentDate() {
		return new Date();
	}
	public static Date getDateFromParts(int year, int month, int day) {
		return getDateFromParts(year, month, day, 0, 0, 0);
	}
	public static Date getDateFromParts(int year, int month, int day, int hour, int minute, int second) {
		return new DateTime(year, month, day, hour, minute, second).toDate();
	}
	
	/**
	 * @author David
	 * Calculate the difference of the current system date and the given parameter
	 * @param last the Date for the Calculation
	 */
	public static int getDiffYears(Date last) {
		Date now = new Date();
		return getDiffYears(now, last);
	}
	/**
	 * @author David
	 * Calculate the difference in years of the first date and the second date
	 * @param first the first Date for the Calculation
	 * @param last the second Date for the Calculation
	 */
	public static int getDiffYears(Date first, Date last) {
		DateTime juFirst = new DateTime(first);
		DateTime juLast = new DateTime(last);
		
		Period period = new Period(juFirst, juLast);
		
		return period.getYears();
	}
	/**
	 * @author David
	 * Calculate the difference in days of the current system date and the given parameter
	 * @param last the Date for the Calculation
	 */
	public static int getDiffDays(Date last) {
		Date now = new Date();
		return getDiffDays(now, last);
	}
	/**
	 * @author David
	 * Calculate the difference in days of the first date and the second date
	 * @param first the first Date for the Calculation
	 * @param last the second Date for the Calculation
	 */
	public static int getDiffDays(Date first, Date last) {
		DateTime juFirst = new DateTime(first);
		DateTime juLast = new DateTime(last);
		
		Period period = new Period(juFirst, juLast);
		
		return period.getDays();
	}
	
	public static Date addDays(Date date, int daysToAdd) {
		DateTime juFirst = new DateTime(date);
		
		return juFirst.plusDays(daysToAdd).toDate();
	}
	public static Date addMonths(Date date, int monthsToAdd) {
		DateTime juFirst = new DateTime(date);
		
		return juFirst.plusMonths(monthsToAdd).toDate();
	}
	public static Date addYears(Date date, int yearsToAdd) {
		DateTime juFirst = new DateTime(date);
		
		return juFirst.plusYears(yearsToAdd).toDate();
	}
}
