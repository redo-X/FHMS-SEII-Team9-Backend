/**
 * 
 */
package de.warehouse.shared;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.warehouse.shared.util.DateUtil;
import javax.persistence.*;
/**
 * @author David
 *
 */
/**
 * @author David
 *
 */
@Entity
public class CustomerOrder implements Serializable{
	@Id
	@GeneratedValue
	private Integer code;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Customer customer;
	@ManyToOne(cascade=CascadeType.ALL)
	private Employee picker;
	
	@Column(nullable=false)
	private Date orderDate;
	@Column(nullable=false)
	private Date dueDate;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="order")
	@MapKey
	private Map<Integer, CustomerOrderPosition> positions;
	
	@Column(nullable=false)
	private double commissionProgress;
	
	private LocalDateTime startOfCommission;
	private LocalDateTime finishOfCommission;
	@Column(nullable=false)
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
	 * Calculates the progress of the whole commission.
	 * @author David
	 * @return Progress of commission
	 */
	public double getProgress() {
		Double progress = 0.0;
		Integer numberOfPositions = 0;
		
		for(CustomerOrderPosition pos : this.getPositions().values()) {
			progress += pos.getProgress();
			
			numberOfPositions++;
		}
		
		return progress / Double.valueOf(numberOfPositions);
	}
	
	
	
	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	/**
	 * @return the picker
	 */
	public Employee getPicker() {
		return picker;
	}
	/**
	 * @param picker the picker to set
	 */
	public void setPicker(Employee picker) {
		this.picker = picker;
	}
	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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
	/**
	 * @return the positions
	 */
	public Map<Integer, CustomerOrderPosition> getPositions() {
		return positions;
	}
	/**
	 * @param positions the positions to set
	 */
	public void setPositions(Map<Integer, CustomerOrderPosition> positions) {
		this.positions = positions;
	}
	/**
	 * @return the commissionProgress
	 */
	public double getCommissionProgress() {
		return commissionProgress;
	}
	/**
	 * @param commissionProgress the commissionProgress to set
	 */
	public void setCommissionProgress(double commissionProgress) {
		this.commissionProgress = commissionProgress;
	}
	/**
	 * @return the startOfCommission
	 */
	public LocalDateTime getStartOfCommission() {
		return startOfCommission;
	}
	/**
	 * @param startOfCommission the startOfCommission to set
	 */
	public void setStartOfCommission(LocalDateTime startOfCommission) {
		this.startOfCommission = startOfCommission;
	}
	/**
	 * @return the finishOfCommission
	 */
	public LocalDateTime getFinishOfCommission() {
		return finishOfCommission;
	}
	/**
	 * @param finishOfCommission the finishOfCommission to set
	 */
	public void setFinishOfCommission(LocalDateTime finishOfCommission) {
		this.finishOfCommission = finishOfCommission;
	}
	/**
	 * @return the isEmailSent
	 */
	public boolean isEmailSent() {
		return isEmailSent;
	}
	/**
	 * @param isEmailSent the isEmailSent to set
	 */
	public void setEmailSent(boolean isEmailSent) {
		this.isEmailSent = isEmailSent;
	}

	
	
}
