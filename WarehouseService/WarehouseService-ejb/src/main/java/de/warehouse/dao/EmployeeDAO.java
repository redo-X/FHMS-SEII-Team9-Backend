package de.warehouse.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.warehouse.dao.interfaces.IEmployeeDAO;
import de.warehouse.persistence.Employee;
import de.warehouse.persistence.Role;

/**
 * @see de.warehouse.dao.interfaces.IEmployeeDAO
 */
@Stateless
public class EmployeeDAO implements IEmployeeDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#findById(int)
	 */
	@Override
	public Employee findById(int id) {
		return this.em.find(Employee.class, id);
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#create(Employee)
	 */
	@Override
	public Employee create(Employee e) {
		this.em.persist(e);
		return e;
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#update(Employee)
	 */
	@Override
	public Employee update(Employee e) {
		return this.em.merge(e);
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#delete(Employee)
	 */
	@Override
	public void delete(Employee e) {
		this.em.remove(em.contains(e) ? e : em.merge(e));
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#getAll()
	 */
	@Override
	public List<Employee> getAll() {
		return this.em.createQuery("SELECT e FROM " + Employee.class.getSimpleName() + " e", Employee.class)
				.getResultList();
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#getByRole(de.warehouse.persistence.Role)
	 */
	@Override
	public List<Employee> getByRole(Role role) {
		return this.em.createQuery("SELECT e FROM " + Employee.class.getSimpleName() + " e WHERE e.role = :role", Employee.class)
				.setParameter("role", role)
				.getResultList();
	}
}
