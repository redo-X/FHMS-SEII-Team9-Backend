package de.warehouse.shared;

import java.util.Date;

import javax.persistence.*;

@Entity
public class WarehouseSession {
	@Id 
	@GeneratedValue
	private int id;
	@ManyToOne
	private Employee employee;
	private final Date creationTime;
	
	public WarehouseSession() {
		this.creationTime = new Date();
	}
	public WarehouseSession(Employee employee) {
		this.employee = employee;
		this.creationTime = new Date();
	}
	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}
	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}
	
	
}
