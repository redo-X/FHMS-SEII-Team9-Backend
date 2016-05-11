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
