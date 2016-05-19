package de.warehouse.shared.interfaces;

import de.warehouse.shared.Employee;
import de.warehouse.shared.Role;
import de.warehouse.shared.WarehouseSession;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.SessionExpiredException;

public interface ISessionManagement {
	public int createSession(Employee employee);
	
	public WarehouseSession getById(int warehouseSessionId);
	
	public void ensureAuthorization(Role requiredLevel, int warehouseSessionId) throws SessionExpiredException, AccessDeniedException;
	
	public void closeSession(int warehouseSessionId);
}
