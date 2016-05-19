package de.warehouse.session;

import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.warehouse.shared.Employee;
import de.warehouse.shared.Role;
import de.warehouse.shared.WarehouseSession;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.ISessionManagement;

/**
 * Session Bean implementation class SessionManagement
 */
@Singleton
@Remote(ISessionManagement.class)
public class SessionManagement implements ISessionManagement {

	@PersistenceContext
	private EntityManager em;

	@Override
	public int createSession(Employee employee) {
		
		// David: Any open session => remove it first
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<WarehouseSession> cq = cb.createQuery(WarehouseSession.class);
		Root<WarehouseSession> e = cq.from(WarehouseSession.class);
		cq.where(cb.equal(e.get("employee"), employee));
		TypedQuery<WarehouseSession> q = em.createQuery(cq);
		
		for(WarehouseSession s : q.getResultList()) {
			this.em.remove(s);
		}
	
		WarehouseSession session = new WarehouseSession(employee);
		this.em.persist(session);
		return session.getId();
	}

	@Override
	public WarehouseSession getById(int warehouseSessionId) {
		return this.em.find(WarehouseSession.class, warehouseSessionId);
	}

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

	@Override
	public void closeSession(int warehouseSessionId) {
		WarehouseSession session = this.getById(warehouseSessionId);

		if (session != null) {
			this.em.remove(session);
		}
	}
}
