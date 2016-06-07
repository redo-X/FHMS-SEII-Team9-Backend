/**
 * 
 */
package de.warehouse.shared;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author David
 *
 */
@Entity
public class CustomerOrderPositionMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1894370282465357747L;

	@Id
	@GeneratedValue
	private int customerOrderPositionMessageId;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private CustomerOrderPosition position;
	
	@Column(nullable=false)
	private String note;
	
	@Column(nullable=false)
	private Integer quantityDifference;
	
	@ManyToOne(optional=false)
	private Employee createdByEmployee;
	
	@ManyToOne(optional=false)
	private Employee responsibleEmployee;

	/**
	 * @return the customerOrderPositionMessageId
	 */
	public int getCustomerOrderPositionMessageId() {
		return customerOrderPositionMessageId;
	}

	/**
	 * @param customerOrderPositionMessageId the customerOrderPositionMessageId to set
	 */
	public void setCustomerOrderPositionMessageId(int customerOrderPositionMessageId) {
		this.customerOrderPositionMessageId = customerOrderPositionMessageId;
	}

	/**
	 * @return the position
	 */
	public CustomerOrderPosition getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(CustomerOrderPosition position) {
		this.position = position;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the quantityDifference
	 */
	public Integer getQuantityDifference() {
		return quantityDifference;
	}

	/**
	 * @param quantityDifference the quantityDifference to set
	 */
	public void setQuantityDifference(Integer quantityDifference) {
		this.quantityDifference = quantityDifference;
	}

	/**
	 * @return the createdByEmployee
	 */
	public Employee getCreatedByEmployee() {
		return createdByEmployee;
	}

	/**
	 * @param createdByEmployee the createdByEmployee to set
	 */
	public void setCreatedByEmployee(Employee createdByEmployee) {
		this.createdByEmployee = createdByEmployee;
	}

	/**
	 * @return the responsibleEmployee
	 */
	public Employee getResponsibleEmployee() {
		return responsibleEmployee;
	}

	/**
	 * @param responsibleEmployee the responsibleEmployee to set
	 */
	public void setResponsibleEmployee(Employee responsibleEmployee) {
		this.responsibleEmployee = responsibleEmployee;
	}
	
	
}
