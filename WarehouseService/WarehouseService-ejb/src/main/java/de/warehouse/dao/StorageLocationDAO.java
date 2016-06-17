package de.warehouse.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import de.warehouse.dao.interfaces.IStorageLocationDAO;
import de.warehouse.persistence.StorageLocation;
import de.warehouse.shared.exceptions.EntityWithIdentifierAlreadyExistsException;

/**
 * @author Florian
 * @see de.warehouse.dao.interfaces.IStorageLocationDAO
 */
@Stateless
public class StorageLocationDAO implements IStorageLocationDAO {
	
	private static final Logger logger = Logger.getLogger(StorageLocationDAO.class);
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#findById(String)
	 */
	@Override
	public StorageLocation findById(String id) {
		logger.info(String.format("INVOKE: %s(%s)", "findById", id));
		
		return this.em.find(StorageLocation.class, id);
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#findByIdWithArticles(String)
	 */
	@Override
	public StorageLocation findByIdWithArticles(String id) {
		logger.info(String.format("INVOKE: %s(%s)", "findByIdWithArticles", id));
		
		return this.em.createQuery("SELECT x FROM " + StorageLocation.class.getSimpleName() + " x JOIN FETCH x.stockArticles a WHERE x.code = :code", StorageLocation.class)
				.setParameter("code", id)
				.getSingleResult();
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#getAll()
	 */
	@Override
	public List<StorageLocation> getAll() {
		logger.info(String.format("INVOKE: %s", "getAll"));
		
		return this.em.createQuery("SELECT x FROM " + StorageLocation.class.getSimpleName()+ " x", StorageLocation.class)
				.getResultList();
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#create(StorageLocation)
	 */
	@Override
	public StorageLocation create(StorageLocation s) throws EntityWithIdentifierAlreadyExistsException {
		logger.info(String.format("INVOKE: %s(%s)", "create", s.toString()));
		
		if(this.findById(s.getCode()) != null) {
			logger.error("StorageLocation already exists.");
			
			throw new EntityWithIdentifierAlreadyExistsException(String.format("Storage Location with id %s already exists.", s.getCode()));
		}
		
		this.em.persist(s);
		
		logger.info("SAVEPOINT");
		
		return s;
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#update(StorageLocation)
	 */
	@Override
	public StorageLocation update(StorageLocation s) {
		logger.info(String.format("INVOKE: %s(%s)", "update", s.toString()));
		
		StorageLocation result = this.em.merge(s);
		
		logger.info("SAVEPOINT");
		
		return result;
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#delete(StorageLocation)
	 */
	@Override
	public void delete(StorageLocation s) {
		logger.info(String.format("INVOKE: %s(%s)", "delete", s.toString()));
		
		this.em.remove(em.contains(s) ? s : em.merge(s));
		
		logger.info("SAVEPOINT");
	}

}
