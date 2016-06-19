package de.warehouse.session;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import de.warehouse.dao.interfaces.IWarehouseSessionDAO;
import de.warehouse.persistence.Employee;
import de.warehouse.persistence.Role;
import de.warehouse.persistence.WarehouseSession;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.exceptions.UsernamePasswordMismatchException;
import de.warehouse.shared.interfaces.ISessionManagement;

/**
 * Session Bean implementation class SessionManagement
 * @author David, Florian, Thomas, Marco
 * @see de.warehouse.shared.interfaces.ISessionManagement
 */
@Singleton
@Remote(ISessionManagement.class)
public class SessionManagement implements ISessionManagement {

	@EJB
	private IWarehouseSessionDAO warehouseSessionDAO;

	/**
	 * @see de.warehouse.shared.interfaces.ISessionManagement#createSession(Integer, String)
	 */
	@Override
	public int createSession(Integer code, String password) throws EntityNotFoundException, UsernamePasswordMismatchException {
		return this.warehouseSessionDAO.create(code, password);
	}
	/**
	 * @see de.warehouse.shared.interfaces.ISessionManagement#getById(int)
	 */
	@Override
	public WarehouseSession getById(int warehouseSessionId) {
		return this.warehouseSessionDAO.findById(warehouseSessionId);
	}

	/**
	 * @see de.warehouse.shared.interfaces.ISessionManagement#ensureAuthorization(Role, int)
	 */
	@Override
	public void ensureAuthorization(Role requiredLevel, int warehouseSessionId) throws SessionExpiredException, AccessDeniedException {
		WarehouseSession session = this.getById(warehouseSessionId);

		if (session != null) {
			Employee e = session.getEmployee();
			if (e != null) {
				Role r = e.getRole();

				if (r != null) {
					if(r.ordinal() >= requiredLevel.ordinal()) {
						return; // User has the required role or higher
					}
				}
			}
		}
		else {
			throw new SessionExpiredException();
		}

		throw new AccessDeniedException();
	}
	/**
	 * @see de.warehouse.shared.interfaces.ISessionManagement#closeSession(int)
	 */
	@Override
	public void closeSession(int warehouseSessionId) {
		this.warehouseSessionDAO.delete(warehouseSessionId);
	}
}
