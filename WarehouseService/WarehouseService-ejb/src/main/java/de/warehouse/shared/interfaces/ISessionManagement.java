package de.warehouse.shared.interfaces;

import de.warehouse.persistence.Role;
import de.warehouse.persistence.WarehouseSession;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.exceptions.UsernamePasswordMismatchException;

public interface ISessionManagement {
	public int createSession(Integer code, String password) throws EntityNotFoundException, UsernamePasswordMismatchException;
	
	public WarehouseSession getById(int warehouseSessionId);
	
	public void ensureAuthorization(Role requiredLevel, int warehouseSessionId) throws SessionExpiredException, AccessDeniedException;
	
	public void closeSession(int warehouseSessionId);
}
