package de.warehouse.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.*;

import de.warehouse.shared.util.DateUtil;

/**
 * @author David
 *
 */
@Entity
public class Employee implements Serializable {
	@Id
	@GeneratedValue
	private Integer code;
	
	private String password;
	
	private String firstName;
	private String lastName;
	
	private String mailAddress; 
	
	
	@Column(nullable=false)
	@Enumerated(EnumType.ORDINAL) 
	private Role role;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="createdByEmployee")
	private Map<Integer, CustomerOrderPositionMessage> createdCustomerOrderPositionMessages;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="responsibleEmployee")
	private Map<Integer, CustomerOrderPositionMessage> responsibleForCustomerOrderPositionMessages;
	

	public String getFullName() {
		return String.format("%s %s", this.getFirstName(), this.getLastName());
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}
	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Employee [code=" + code + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
