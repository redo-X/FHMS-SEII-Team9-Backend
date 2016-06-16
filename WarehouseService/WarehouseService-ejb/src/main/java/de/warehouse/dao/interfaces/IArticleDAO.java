package de.warehouse.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.warehouse.persistence.Article;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.EntityWithIdentifierAlreadyExistsException;

/**
 * This interface encapsulates the data access logic for the articles.
 * @author David
 */
@Local
public interface IArticleDAO {	
	/**
	 * Try to find an article based on the given identity field.
	 * @author David
	 * @param id Identifier of the article
	 * @return null, if no article with the id exists or entity instance
	 * */
	public Article findById(String id);
	/**
	 * Loads all articles from the database.
	 * @author David
	 * @return List of articles
	 * */
	public List<Article> getAll();
	/**
	 * Loads all articles which are stored in the given storage location from the database.
	 * @author David
	 * @param storageLocationId Storage location to filter the result set
	 * @return List of matching articles
	 * @throws EntityNotFoundException StorageLocation with given storageLocationId not exists 
	 * */
	public List<Article> getByStorageLocation(String storageLocationId) throws EntityNotFoundException;
	/**
	 * Modify the storage location of an article.
	 * @author David
	 * @param articleId Identifier of the article
	 * @param storageLocationId Identifier of the new storage location
	 * @throws EntityNotFoundException if articleId or storageLocationId is invalid
	 * */
	public void updateStorageLocationOfArticle(String articleId, String storageLocationId) throws EntityNotFoundException;
	/**
	 * Modify the quantity of stock of the given article.
	 * @author David
	 * @param articleId Identifier of the article
	 * @param receiptQuantity Receipt quantity; receiptQuantity represents the incoming quantity - not the final quantity
	 * @throws EntityNotFoundException if articleId is invalid
	 * */
	public void updateQuantityOnStockOfArticle(String articleId, int receiptQuantity) throws EntityNotFoundException;
	
	/**
	 * Creates a new article in the database.
	 * @author David
	 * @param a Instance of a detached article entity
	 * @throws EntityWithIdentifierAlreadyExistsException if an article with the code already created
	 * @return Created entity with generated fields like id
	 * */
	public Article create(Article a) throws EntityWithIdentifierAlreadyExistsException;
	/**
	 * Updates an existing article in the database.
	 * @author David
	 * @param a Instance of a detached article entity
	 * @return Updates entity with generated fields like updatedAt
	 * */
	public Article update(Article a);
	/**
	 * Deletes an existing article in the database.
	 * @author David
	 * @param a Instance of a detached article entity
	 * */
	public void delete(Article a);
}
