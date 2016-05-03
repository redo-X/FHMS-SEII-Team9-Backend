/**
 * 
 */
package de.warehouse.shared;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

import de.warehouse.shared.util.DateUtil;

/**
 * @author David
 *
 */
public class CustomerOrder {
	private Integer code;
	
	private Customer customer;
	private Employee picker;
	
	private Date orderDate;
	private Date dueDate;
	
	private HashMap<Integer, CustomerOrderPosition> positions;
	
	private double commissionProgress;
	
	private LocalDateTime startOfCommission;
	private LocalDateTime finishOfCommission;
	
	private boolean isEmailSent;
	
	
	/**
	 * Shows if the customer order is
	 * @return true, if dueDate <= currentDate
	 * 		   		 else false
	 */
	public boolean isOverdue(){
		return 0 == DateUtil.getDiffDays(DateUtil.addDays(dueDate, this.customer.getDeliveryToleranceInDays()));
	}


	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
