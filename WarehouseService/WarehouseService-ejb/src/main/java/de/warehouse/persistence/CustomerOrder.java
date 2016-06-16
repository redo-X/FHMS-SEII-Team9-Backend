/**
 * 
 */
package de.warehouse.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import de.warehouse.shared.exceptions.CustomerOrderAlreadyAllocatedException;
import de.warehouse.shared.utils.DateUtil;

/**
 * @author David
 *
 */
@Entity
public class CustomerOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3808434973954797744L;

	@Id
	@GeneratedValue
	private Integer code;

	@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
	private Customer customer;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Employee picker;

	@Column(nullable = false)
	private Date orderDate;
	@Column(nullable = false)
	private Date dueDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
	@MapKey
	private Map<Integer, CustomerOrderPosition> positions;

	@Column(nullable = false)
	private double commissionProgress;

	private LocalDateTime startOfCommission;
	private LocalDateTime finishOfCommission;

	@Column(nullable = false)
	private boolean isEmailSent;

	public CustomerOrder() {

	}

	public CustomerOrder(int code) {
		this.code = code;
		this.positions = new HashMap<Integer, CustomerOrderPosition>();
	}
	public CustomerOrder(Date orderDate, Date dueDate) {
		this.positions = new HashMap<Integer, CustomerOrderPosition>();
		
		this.orderDate = orderDate;
		this.dueDate = dueDate;
	}
	public CustomerOrder(int code, Date orderDate, Date dueDate) {
		this.code = code;
		this.positions = new HashMap<Integer, CustomerOrderPosition>();
		
		this.orderDate = orderDate;
		this.dueDate = dueDate;
	}

	/**
	 * Shows if the customer order is
	 * 
	 * @return true, if dueDate <= currentDate else false
	 */
	public boolean isOverdue() {
		int diffInDays = DateUtil.getDiffDays(DateUtil.addDays(dueDate, this.customer.getDeliveryToleranceInDays()));

		return diffInDays <= 0;
	}

	/**
	 * Calculates the progress of the whole commission.
	 * 
	 * @author David
	 * @return Progress of commission
	 */
	public double getProgress() {
		Double progress = 0.0;
		Integer numberOfPositions = 0;

		for (CustomerOrderPosition pos : this.getPositions().values()) {
			progress += pos.getProgress();

			numberOfPositions++;
		}

		return progress / Double.valueOf(numberOfPositions == 0 ? 1 : numberOfPositions);
	}
	/**
	 * Calculates the current progress and set it to the commissionProgress property.
	 */
	public void updateProgress() {
		this.setCommissionProgress(getProgress());
	}

	@Override
	public String toString() {
		return "CustomerOrder [code=" + code + ", customer=" + customer + ", picker=" + picker + ", orderDate="
				+ orderDate + ", dueDate=" + dueDate + ", commissionProgress=" + commissionProgress
				+ ", startOfCommission=" + startOfCommission + ", finishOfCommission=" + finishOfCommission
				+ ", isEmailSent=" + isEmailSent + "]";
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
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
	 * @param picker
	 *            the picker to set
	 * @throws CustomerOrderAlreadyAllocatedException 
	 */
	public void setPicker(Employee picker) throws CustomerOrderAlreadyAllocatedException {
		if (this.picker != null && picker != null) {
			throw new CustomerOrderAlreadyAllocatedException("CustomerOrder has already a picker.");
		}
		this.picker = picker;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		if (DateUtil.getDiffDays(orderDate) < 0) {
			throw new IllegalArgumentException("OrderDate must not be in the past.");
		}
		this.orderDate = orderDate;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		if (DateUtil.getDiffDays(dueDate) <= 0) {
			throw new IllegalArgumentException("DueDate must be in the future.");
		}
		if (DateUtil.getDiffDays(orderDate, dueDate) < 0) {
			throw new IllegalArgumentException("DueDate must not be less than order date.");
		}
		this.dueDate = dueDate;
	}

	/**
	 * @return the positions
	 */
	public Map<Integer, CustomerOrderPosition> getPositions() {
		return positions;
	}

	/**
	 * @param positions
	 *            the positions to set
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
	 * @param commissionProgress
	 *            the commissionProgress to set
	 */
	public void setCommissionProgress(double commissionProgress) {
		if (commissionProgress < 0) {
			throw new IllegalArgumentException("commissionProgress must not be negative.");
		}
		if (commissionProgress > 1) {
			throw new IllegalArgumentException("commissionProgress must not be greater than one.");
		}
		this.commissionProgress = commissionProgress;
	}

	/**
	 * @return the startOfCommission
	 */
	public LocalDateTime getStartOfCommission() {
		return startOfCommission;
	}

	/**
	 * @param startOfCommission
	 *            the startOfCommission to set
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
	 * @param finishOfCommission
	 *            the finishOfCommission to set
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
	 * @param isEmailSent
	 *            the isEmailSent to set
	 */
	public void setEmailSent(boolean isEmailSent) {
		this.isEmailSent = isEmailSent;
	}
}