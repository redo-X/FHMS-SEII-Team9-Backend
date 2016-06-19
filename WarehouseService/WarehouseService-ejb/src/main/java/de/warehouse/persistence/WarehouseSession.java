package de.warehouse.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * @author David, Florian
 */
@Entity
public class WarehouseSession implements Serializable {

	private static final long serialVersionUID = -5065712173625744465L;
	
	@Id 
	@GeneratedValue
	private int id;
	@ManyToOne(fetch=FetchType.EAGER)
	private Employee employee;
	private Date creationTime;
	
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
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WarehouseSession [id=" + id + ", employee=" + employee + ", creationTime=" + creationTime + "]";
	}
}
