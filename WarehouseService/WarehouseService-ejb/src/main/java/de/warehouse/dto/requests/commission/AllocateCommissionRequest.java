package de.warehouse.dto.requests.commission;

import de.warehouse.dto.DataTransferObjectBase;

public class AllocateCommissionRequest extends DataTransferObjectBase {

	private static final long serialVersionUID = 3024593575189871590L;

	private int customerOrderId;
	private int employeeId;
	
	/**
	 * @return the customerOrderId
	 */
	public int getCustomerOrderId() {
		return customerOrderId;
	}
	/**
	 * @param customerOrderId the customerOrderId to set
	 */
	public void setCustomerOrderId(int customerOrderId) {
		this.customerOrderId = customerOrderId;
	}
	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}
