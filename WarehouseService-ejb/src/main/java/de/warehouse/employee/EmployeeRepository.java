package de.warehouse.employee;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.warehouse.shared.Employee;
import de.warehouse.shared.Role;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.IEmployeeRepository;
import de.warehouse.shared.interfaces.ISessionManagement;

@Stateless
@Remote(IEmployeeRepository.class)
public class EmployeeRepository implements IEmployeeRepository {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private ISessionManagement sessionManagementBean;


	@Override
	@Lock(LockType.READ)
	public Employee GetByCode(Integer code) {
		return this.em.find(Employee.class, code);
	}

	@Override
	@Lock(LockType.READ)
	public Employee[] GetAll(int sessionId) throws AccessDeniedException, SessionExpiredException {
		this.sessionManagementBean.ensureAuthorization(Role.Administrator, sessionId);
		
		return (Employee[]) this.em.createQuery("SELECT t FROM " + Employee.class.getSimpleName() + " t")
				.getResultList().toArray();
	}

	@Override
	@Lock(LockType.WRITE)
	public Employee Create(Employee employee) {
		this.em.persist(employee);

		return employee;
	}

	@Override
	@Lock(LockType.WRITE)
	public Employee Update(Employee employee) {
		return this.em.merge(employee);
	}

	@Override
	@Lock(LockType.WRITE)
	public void Remove(Employee employee) {
		this.em.remove(employee);
	}

}
