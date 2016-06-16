package de.warehouse.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.warehouse.persistence.StorageLocation;
import de.warehouse.shared.exceptions.EntityWithIdentifierAlreadyExistsException;

/**
 * This interface encapsulates the data access logic for the storage locations.
 * @author David
 */
@Local
public interface IStorageLocationDAO {
	/**
	 * Try to find a storage location based on the given identity field.
	 * @param id Identifier of the storage location
	 * @return null, if no storage location with the id exists or entity instance
	 */
	public StorageLocation findById(String id);
	/**
	 * Try to find a storage location based on the given identity field.
	 * This method also eager loads the related articles of the storage location.
	 * @param id Identifier of the storage location
	 * @return null, if no storage location with the id exists or entity instance
	 */
	public StorageLocation findByIdWithArticles(String id);
	/**
	 * Loads all storage locations from the database.
	 * @author David
	 * @return List of storage locations
	 * */
	public List<StorageLocation> getAll();
	/**
	 * Creates a new storage location in the database.
	 * @author David
	 * @param s Instance of a detached storage location entity
	 * @return Created entity with generated fields like id
	 * @throws EntityWithIdentifierAlreadyExistsException if Storage Location with given id already exists
	 * */
	public StorageLocation create(StorageLocation s) throws EntityWithIdentifierAlreadyExistsException;
	/**
	 * Updates an existing storage location in the database.
	 * @author David
	 * @param s Instance of a detached storage location entity
	 * @return Updates entity with generated fields like updatedAt
	 * */
	public StorageLocation update(StorageLocation s);
	/**
	 * Deletes an existing storage location in the database.
	 * @author David
	 * @param s Instance of a detached storage location entity
	 * */
	public void delete(StorageLocation s);
}
