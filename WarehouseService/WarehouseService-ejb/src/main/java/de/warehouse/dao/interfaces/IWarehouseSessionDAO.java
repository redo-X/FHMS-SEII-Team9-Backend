package de.warehouse.dao.interfaces;

import javax.ejb.Local;

import de.warehouse.persistence.WarehouseSession;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.UsernamePasswordMismatchException;

/**
 * This interface encapsulates the data access logic for the sessions.
 * @author David
 */
@Local
public interface IWarehouseSessionDAO {
	/**
	 * Creates a new session to authenticate the user.
	 * Known as login.
	 * @author David
	 * @param code Identifier of the employee
	 * @param password Password of the employee
	 * @return Identifier of the created session.
	 * @throws EntityNotFoundException if no employee with the identifier exists
	 * @throws UsernamePasswordMismatchException if password not valid for the identifier
	 * */
	public int create(Integer code, String password)
			throws EntityNotFoundException, UsernamePasswordMismatchException;
	/**
	 * Try to find a session based on the given identity field.
	 * @param warehouseSessionId Session identifier
	 * @return null, if no session with the id exists, expired or entity instance
	 */
	public WarehouseSession findById(int warehouseSessionId);
	/**
	 * Deletes an existing session in the database.
	 * Known as logout.
	 * @author David
	 * @param warehouseSessionId Session identifier
	 * */
	public void delete(int warehouseSessionId);
}