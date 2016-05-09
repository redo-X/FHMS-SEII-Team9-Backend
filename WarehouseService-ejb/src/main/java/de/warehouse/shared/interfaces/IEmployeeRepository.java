package de.warehouse.shared.interfaces;

import de.warehouse.shared.Employee;

public interface IEmployeeRepository {
	public Employee GetByCode(Integer code);
	
	public Employee[] GetAll();
	
	public Employee Create(Employee employee);
	public Employee Update(Employee employee);
	public Employee Remove(Employee employee);
}
