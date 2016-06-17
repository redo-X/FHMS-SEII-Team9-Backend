package de.warehouse.shared.interfaces;

import java.util.List;

import de.warehouse.persistence.Employee;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.SessionExpiredException;

/**
 * @see de.warehouse.dao.interfaces.IEmployeeDAO
 */
public interface IEmployeeRepository {
	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#findById(int)
	 */
	public Employee findById(Integer code);
	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#getAll()
	 */
	public List<Employee> getAll(int sessionId) throws SessionExpiredException, AccessDeniedException;
	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#create(Employee)
	 */
	public Employee create(int sessionId, Employee employee) throws SessionExpiredException, AccessDeniedException;
	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#update(Employee)
	 */
	public Employee update(int sessionId, Employee employee) throws SessionExpiredException, AccessDeniedException;
	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#delete(Employee)
	 */
	public void delete(int sessionId, Employee employee) throws SessionExpiredException, AccessDeniedException;
}
