package de.warehouse.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.warehouse.dao.interfaces.IWarehouseSessionDAO;
import de.warehouse.persistence.Employee;
import de.warehouse.persistence.WarehouseSession;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.UsernamePasswordMismatchException;

/**
 * @see de.warehouse.dao.interfaces.IWarehouseSessionDAO
 */
@Stateless
public class WarehouseSessionDAO implements IWarehouseSessionDAO {
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * @see de.warehouse.dao.interfaces.IWarehouseSessionDAO#create(Integer, String)
	 */
	@Override
	public int create(Integer code, String password)
			throws EntityNotFoundException, UsernamePasswordMismatchException {
		Employee e = this.em.find(Employee.class, code);
		
		List<WarehouseSession> sessions = this.em
				.createQuery("SELECT x FROM " + WarehouseSession.class.getSimpleName() + " x WHERE x.employee = :employee", WarehouseSession.class)
				.setParameter("employee", e)
				.getResultList();
		
		for(WarehouseSession ws : sessions) {
			this.em.remove(ws);
		}
		
		if(e == null) {
			throw new EntityNotFoundException(code.toString() + " not found.");
		}
		
		if(!e.getPassword().equals(password)) {
			throw new UsernamePasswordMismatchException();
		}
	
		WarehouseSession session = new WarehouseSession(e);
		this.em.persist(session);
		return session.getId();
	}
	/**
	 * @see de.warehouse.dao.interfaces.IWarehouseSessionDAO#findById(int)
	 */
	@Override
	public WarehouseSession findById(int warehouseSessionId) {
		return this.em.find(WarehouseSession.class, warehouseSessionId);
	}
	/**
	 * @see de.warehouse.dao.interfaces.IWarehouseSessionDAO#delete(int)
	 */
	@Override
	public void delete(int warehouseSessionId) {
		WarehouseSession session = this.findById(warehouseSessionId);

		if (session != null) {
			this.em.remove(session);
		}
	}

}
