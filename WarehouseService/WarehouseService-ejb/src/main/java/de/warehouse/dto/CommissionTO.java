package de.warehouse.dto;

public class CommissionTO extends ResponseBase {

	private static final long serialVersionUID = -6796983288704720189L;
	
	private int commissionId;
	private int positionCount;
	
	private boolean isRelatedToEmployee;

	private double progress;
	
	private Integer employeeCode;
	private String employeeFullName;
	
	
	public CommissionTO() {
		
	}	
	/**
	 * @param commissionId
	 * @param positionCount
	 * @param isRelatedToEmployee
	 * @param progress
	 * @param employeeCode
	 * @param employeeFullName
	 */
	public CommissionTO(int commissionId, int positionCount, boolean isRelatedToEmployee, double progress,
			Integer employeeCode, String employeeFullName) {
		this.commissionId = commissionId;
		this.positionCount = positionCount;
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
