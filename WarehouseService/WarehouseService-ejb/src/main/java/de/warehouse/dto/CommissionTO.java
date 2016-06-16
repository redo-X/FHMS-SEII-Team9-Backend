package de.warehouse.dto;

public class CommissionTO extends ResponseBase {

	private static final long serialVersionUID = -6796983288704720189L;
	
	private int commissionId;
	private int positionCount;

	private int orderDateAsUnixTimestamp;
	private int dueDateAsUnixTimestamp;
	
	private boolean isRelatedToEmployee;

	private double progress;
	
	private Integer employeeCode;
	private String employeeFullName;
	
	
	public CommissionTO() {
		
	}	
	
	/**
	 * @param commissionId
	 * @param positionCount
	 * @param orderDateAsUnixTimestamp
	 * @param dueDateAsUnixTimestamp
	 * @param isRelatedToEmployee
	 * @param progress
	 * @param employeeCode
	 * @param employeeFullName
	 */
	public CommissionTO(int commissionId, int positionCount, int orderDateAsUnixTimestamp, int dueDateAsUnixTimestamp,
			boolean isRelatedToEmployee, double progress, Integer employeeCode, String employeeFullName) {
		this.commissionId = commissionId;
		this.positionCount = positionCount;
		this.orderDateAsUnixTimestamp = orderDateAsUnixTimestamp;
		this.dueDateAsUnixTimestamp = dueDateAsUnixTimestamp;
		this.isRelatedToEmployee = isRelatedToEmployee;
		this.progress = progress;
		this.employeeCode = employeeCode;
		this.employeeFullName = employeeFullName;
	}

	/**
	 * @return the commissionId
	 */
	public int getCommissionId() {
		return commissionId;
	}
	/**
	 * @param commissionId the commissionId to set
	 */
	public void setCommissionId(int commissionId) {
		this.commissionId = commissionId;
	}
	/**
	 * @return the positionCount
	 */
	public int getPositionCount() {
		return positionCount;
	}
	/**
	 * @param positionCount the positionCount to set
	 */
	public void setPositionCount(int positionCount) {
		this.positionCount = positionCount;
	}
	/**
	 * @return the orderDateAsUnixTimestamp
	 */
	public int getOrderDateAsUnixTimestamp() {
		return orderDateAsUnixTimestamp;
	}

	/**
	 * @param orderDateAsUnixTimestamp the orderDateAsUnixTimestamp to set
	 */
	public void setOrderDateAsUnixTimestamp(int orderDateAsUnixTimestamp) {
		this.orderDateAsUnixTimestamp = orderDateAsUnixTimestamp;
	}

	/**
	 * @return the dueDateAsUnixTimestamp
	 */
	public int getDueDateAsUnixTimestamp() {
		return dueDateAsUnixTimestamp;
	}

	/**
	 * @param dueDateAsUnixTimestamp the dueDateAsUnixTimestamp to set
	 */
	public void setDueDateAsUnixTimestamp(int dueDateAsUnixTimestamp) {
		this.dueDateAsUnixTimestamp = dueDateAsUnixTimestamp;
	}

	/**
	 * @return the isRelatedToEmployee
	 */
	public boolean isRelatedToEmployee() {
		return isRelatedToEmployee;
	}
	/**
	 * @param isRelatedToEmployee the isRelatedToEmployee to set
	 */
	public void setRelatedToEmployee(boolean isRelatedToEmployee) {
		this.isRelatedToEmployee = isRelatedToEmployee;
	}
	/**
	 * @return the progress
	 */
	public double getProgress() {
		return progress;
	}
	/**
	 * @param progress the progress to set
	 */
	public void setProgress(double progress) {
		this.progress = progress;
	}
	/**
	 * @return the employeeCode
	 */
	public Integer getEmployeeCode() {
		return employeeCode;
	}
	/**
	 * @param employeeCode the employeeCode to set
	 */
	public void setEmployeeCode(Integer employeeCode) {
		this.employeeCode = employeeCode;
	}
	/**
	 * @return the employeeFullName
	 */
	public String getEmployeeFullName() {
		return employeeFullName;
	}
	/**
	 * @param employeeFullName the employeeFullName to set
	 */
	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}
	
	
}
