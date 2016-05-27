package de.warehouse.shared.interfaces;

import de.warehouse.shared.Employee;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.SessionExpiredException;

public interface IEmployeeRepository {
	public Employee GetByCode(Integer code);
	
	public Employee[] GetAll(int sessionId) throws SessionExpiredException, AccessDeniedException;
	
	public Employee Create(Employee employee);
	public Employee Update(Employee employee);
	
	public void Remove(Employee employee);
}
