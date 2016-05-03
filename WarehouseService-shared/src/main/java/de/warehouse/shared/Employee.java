package de.warehouse.shared;

import java.util.Date;

import de.warehouse.shared.util.DateUtil;

/**
 * @author David
 *
 */
public class Employee {
	private Integer code;
	
	private String firstName;
	private String lastName;
	
	private String mailAddress;
	
	private Date dateOfBirth;
	
	private Role role;
	
	
	public Integer getAgeInYears() {
		return DateUtil.getDiffYears(dateOfBirth);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Employee [code=" + code + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	
}
