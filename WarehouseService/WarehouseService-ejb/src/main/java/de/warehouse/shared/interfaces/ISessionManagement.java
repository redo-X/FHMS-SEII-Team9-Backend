package de.warehouse.shared.interfaces;

import de.warehouse.persistence.Role;
import de.warehouse.persistence.WarehouseSession;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.exceptions.UsernamePasswordMismatchException;

/**
 * @see de.warehouse.dao.interfaces.IWarehouseSessionDAO
 */
public interface ISessionManagement {
	/**
	 * @see de.warehouse.dao.interfaces.IWarehouseSessionDAO#create(Integer, String)
	 */
	public int createSession(Integer code, String password) throws EntityNotFoundException, UsernamePasswordMismatchException;
	/**
	 * @see de.warehouse.dao.interfaces.IWarehouseSessionDAO#findById(int)
	 */
	public WarehouseSession getById(int warehouseSessionId);
	/**
	 * Ensures authentication and authorization based on the sessionId and claimed Role
	 * @param requiredLevel Role that the employee has to be related to
	 * @param warehouseSessionId current Context
	 * @throws SessionExpiredException if employee logged out
	 * @throws AccessDeniedException if employee has not the permission to execute the method
	 */
	public void ensureAuthorization(Role requiredLevel, int warehouseSessionId) throws SessionExpiredException, AccessDeniedException;
	/**
	 * @see de.warehouse.dao.interfaces.IWarehouseSessionDAO#delete(int)
	 */
	public void closeSession(int warehouseSessionId);
}
