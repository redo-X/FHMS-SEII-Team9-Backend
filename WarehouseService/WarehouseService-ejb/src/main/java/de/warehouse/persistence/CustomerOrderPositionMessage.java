/**
 * 
 */
package de.warehouse.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * @author David, Florian
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
	
	@Column(nullable=true)
	private String note;
	
	@Column(nullable=false)
	private Integer quantityDifference;
	
	@Column(nullable = false)
	private boolean isEmailSent;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private Employee createdByEmployee;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private Employee responsibleEmployee;


	
	/**
	 * @return the customerOrderPositionMessageId
	 */
	public int getCustomerOrderPositionMessageId() {
		return customerOrderPositionMessageId;
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

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerOrderPositionMessage [customerOrderPositionMessageId=" + customerOrderPositionMessageId
				+ ", position=" + position + ", note=" + note + ", quantityDifference=" + quantityDifference
				+ ", isEmailSent=" + isEmailSent + ", createdByEmployee=" + createdByEmployee + ", responsibleEmployee="
				+ responsibleEmployee + "]";
	}
}
