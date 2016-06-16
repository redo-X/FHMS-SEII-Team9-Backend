package de.warehouse.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author David
 *
 */
@Entity
public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1731231651445561362L;

	@Id
	@GeneratedValue
	private Integer code;

	private String password;

	private String firstName;
	private String lastName;

	private String mailAddress;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Role role;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "createdByEmployee")
	private Set<CustomerOrderPositionMessage> createdCustomerOrderPositionMessages;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "responsibleEmployee")
	private Set<CustomerOrderPositionMessage> responsibleForCustomerOrderPositionMessages;

	public Employee() {

	}

	public Employee(int code, String firstName, String lastName) {
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
	}

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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
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
	 * @param lastName
	 *            the lastName to set
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
	 * @param mailAddress
	 *            the mailAddress to set
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
	 * @param password
	 *            the password to set
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
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Employee [code=" + code + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
