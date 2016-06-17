package de.warehouse.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import de.warehouse.dao.interfaces.IEmployeeDAO;
import de.warehouse.persistence.Employee;
import de.warehouse.persistence.Role;

/**
 * @see de.warehouse.dao.interfaces.IEmployeeDAO
 */
@Stateless
public class EmployeeDAO implements IEmployeeDAO {
	
	private static final Logger logger = Logger.getLogger(EmployeeDAO.class);

	@PersistenceContext
	private EntityManager em;

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#findById(int)
	 */
	@Override
	public Employee findById(int id) {
		logger.info(String.format("INVOKE: %s(%d)", "findById", id));
		
		return this.em.find(Employee.class, id);
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#create(Employee)
	 */
	@Override
	public Employee create(Employee e) {
		logger.info(String.format("INVOKE: %s(%s)", "create", e.toString()));
		
		this.em.persist(e);
		
		logger.info("SAVEPOINT");
		
		return e;
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#update(Employee)
	 */
	@Override
	public Employee update(Employee e) {
		logger.info(String.format("INVOKE: %s(%s)", "update", e.toString()));
		
		Employee result = this.em.merge(e);
		
		logger.info("SAVEPOINT");
		
		return result;
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#delete(Employee)
	 */
	@Override
	public void delete(Employee e) {
		logger.info(String.format("INVOKE: %s(%s)", "delete", e.toString()));
		
		this.em.remove(em.contains(e) ? e : em.merge(e));
		
		logger.info("SAVEPOINT");
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#getAll()
	 */
	@Override
	public List<Employee> getAll() {
		logger.info(String.format("INVOKE: %s", "getAll"));
		
		return this.em.createQuery("SELECT e FROM " + Employee.class.getSimpleName() + " e", Employee.class)
				.getResultList();
	}

	/**
	 * @see de.warehouse.dao.interfaces.IEmployeeDAO#getByRole(de.warehouse.persistence.Role)
	 */
	@Override
	public List<Employee> getByRole(Role role) {
		logger.info(String.format("INVOKE: %s(%s)", "getByRole", role));
		
		return this.em.createQuery("SELECT e FROM " + Employee.class.getSimpleName() + " e WHERE e.role = :role", Employee.class)
				.setParameter("role", role)
				.getResultList();
	}
}
