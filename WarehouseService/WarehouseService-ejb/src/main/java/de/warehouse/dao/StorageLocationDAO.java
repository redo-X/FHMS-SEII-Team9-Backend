package de.warehouse.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.warehouse.dao.interfaces.IStorageLocationDAO;
import de.warehouse.persistence.StorageLocation;
import de.warehouse.shared.exceptions.EntityWithIdentifierAlreadyExistsException;

/**
 * @see de.warehouse.dao.interfaces.IStorageLocationDAO
 */
@Stateless
public class StorageLocationDAO implements IStorageLocationDAO {
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#findById(String)
	 */
	@Override
	public StorageLocation findById(String id) {
		return this.em.find(StorageLocation.class, id);
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#findByIdWithArticles(String)
	 */
	@Override
	public StorageLocation findByIdWithArticles(String id) {
		return this.em.createQuery("SELECT x FROM " + StorageLocation.class.getSimpleName() + " x JOIN FETCH x.stockArticles a WHERE x.code = :code", StorageLocation.class)
				.setParameter("code", id)
				.getSingleResult();
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#getAll()
	 */
	@Override
	public List<StorageLocation> getAll() {
		return this.em.createQuery("SELECT x FROM " + StorageLocation.class.getSimpleName()+ " x", StorageLocation.class)
				.getResultList();
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#create(StorageLocation)
	 */
	@Override
	public StorageLocation create(StorageLocation s) throws EntityWithIdentifierAlreadyExistsException {
		if(this.findById(s.getCode()) != null) {
			throw new EntityWithIdentifierAlreadyExistsException(String.format("Storage Location with id %s already exists.", s.getCode()));
		}
		this.em.persist(s);
		return s;
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#update(StorageLocation)
	 */
	@Override
	public StorageLocation update(StorageLocation s) {
		return this.em.merge(s);
	}
	/**
	 * @see de.warehouse.dao.interfaces.IStorageLocationDAO#delete(StorageLocation)
	 */
	@Override
	public void delete(StorageLocation s) {
		this.em.remove(em.contains(s) ? s : em.merge(s));
	}

}
