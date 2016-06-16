package de.warehouse.shared.interfaces;

import java.util.List;

import de.warehouse.persistence.Employee;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.SessionExpiredException;

public interface IEmployeeRepository {
	public Employee findById(Integer code);
	
	public List<Employee> getAll(int sessionId) throws SessionExpiredException, AccessDeniedException;
	
	public Employee create(Employee employee);
	public Employee update(Employee employee);
	
	public void delete(Employee employee);
}
