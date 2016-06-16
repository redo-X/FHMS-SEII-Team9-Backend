package de.warehouse.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.warehouse.persistence.Employee;
import de.warehouse.persistence.Role;

/**
 * This interface encapsulates the data access logic for the employees.
 * @author David
 */
@Local
public interface IEmployeeDAO {
	/**
	 * Try to find an employee based on the given identity field.
	 * @author David
	 * @param id Identifier of the employee
	 * @return null, if no employee with the id exists or entity instance
	 * */
	public Employee findById(int id);
	/**
	 * Loads all employees from the database.
	 * @author David
	 * @return List of employees
	 * */
	public List<Employee> getAll();
	/**
	 * Loads all employees with the given role from the database.
	 * @param role to filter employees by
	 * @author David
	 * @return List of employees
	 */
	public List<Employee> getByRole(Role role);
	/**
	 * Creates a new article in the database.
	 * @author David
	 * @param e Instance of a detached article entity
	 * @return Created entity with generated fields like id
	 * */
	public Employee create(Employee e);
	/**
	 * Updates an existing employee in the database.
	 * @author David
	 * @param e Instance of a detached employee entity
	 * @return Updates entity with generated fields like updatedAt
	 * */
	public Employee update(Employee e);
	/**
	 * Deletes an existing employee in the database.
	 * @author David
	 * @param e Instance of a detached employee entity
	 * */
	public void delete(Employee e);
}
