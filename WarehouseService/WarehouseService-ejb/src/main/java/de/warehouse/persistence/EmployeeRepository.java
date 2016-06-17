package de.warehouse.persistence;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.warehouse.dao.interfaces.IEmployeeDAO;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.IEmployeeRepository;
import de.warehouse.shared.interfaces.ISessionManagement;

@Stateless
@Remote(IEmployeeRepository.class)
public class EmployeeRepository implements IEmployeeRepository {

	@EJB
	private ISessionManagement sessionManagementBean;

	@EJB
	private IEmployeeDAO employeeDAO;

	/**
	 * @see de.warehouse.shared.interfaces.IEmployeeRepository#findById(Integer)
	 */
	@Override
	@Lock(LockType.READ)
	public Employee findById(Integer code) {
		return this.employeeDAO.findById(code);
	}

	/**
	 * @see de.warehouse.shared.interfaces.IEmployeeRepository#getAll(int)
	 */
	@Override
	@Lock(LockType.READ)
	public List<Employee> getAll(int sessionId) throws AccessDeniedException, SessionExpiredException {
		this.sessionManagementBean.ensureAuthorization(Role.Administrator, sessionId);

		return this.employeeDAO.getAll();
	}

	/**
	 * @see de.warehouse.shared.interfaces.IEmployeeRepository#create(Employee)
	 */
	@Override
	@Lock(LockType.WRITE)
	public Employee create(int sessionId, Employee employee) throws AccessDeniedException, SessionExpiredException {
		this.sessionManagementBean.ensureAuthorization(Role.Administrator, sessionId);

		return this.employeeDAO.create(employee);
	}

	/**
	 * @see de.warehouse.shared.interfaces.IEmployeeRepository#update(Employee)
	 */
	@Override
	@Lock(LockType.WRITE)
	public Employee update(int sessionId, Employee employee) throws AccessDeniedException, SessionExpiredException {
		this.sessionManagementBean.ensureAuthorization(Role.Administrator, sessionId);

		return this.employeeDAO.update(employee);
	}

	/**
	 * @see de.warehouse.shared.interfaces.IEmployeeRepository#delete(Employee)
	 */
	@Override
	@Lock(LockType.WRITE)
	public void delete(int sessionId, Employee employee) throws AccessDeniedException, SessionExpiredException {
		this.sessionManagementBean.ensureAuthorization(Role.Administrator, sessionId);

		this.employeeDAO.delete(employee);
	}
}
