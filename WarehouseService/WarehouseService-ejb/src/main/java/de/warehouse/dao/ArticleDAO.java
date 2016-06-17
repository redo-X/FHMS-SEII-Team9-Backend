package de.warehouse.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import de.warehouse.dao.interfaces.IArticleDAO;
import de.warehouse.persistence.Article;
import de.warehouse.persistence.StorageLocation;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.EntityWithIdentifierAlreadyExistsException;

/**
 * @see de.warehouse.dao.interfaces.IArticleDAO
 */
@Stateless
public class ArticleDAO implements IArticleDAO {

	private static final Logger logger = Logger.getLogger(ArticleDAO.class);

	@PersistenceContext
	private EntityManager em;

	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#findById(String)
	 */
	@Override
	public Article findById(String id) {
		logger.info("findById: " + id);

		return this.em.find(Article.class, id);
	}

	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#getAll()
	 */
	@Override
	public List<Article> getAll() {
		logger.info("getAll");

		return this.em.createQuery("SELECT x FROM " + Article.class.getSimpleName() + " x", Article.class)
				.getResultList();
	}

	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#getByStorageLocation(String)
	 */
	@Override
	public List<Article> getByStorageLocation(String storageLocationId)
			throws de.warehouse.shared.exceptions.EntityNotFoundException {
		logger.info("getByStorageLocation: " + storageLocationId);

		StorageLocation storageLocation = this.em.find(StorageLocation.class, storageLocationId);

		if (null == storageLocation) {
			throw new EntityNotFoundException(
					String.format("Storage Location with id: '%s' not found.", storageLocationId));
		}

		return this.em
				.createQuery("SELECT x FROM " + Article.class.getSimpleName()
						+ " x WHERE x.storageLocation = :storageLocation", Article.class)
				.setParameter("storageLocation", storageLocation).getResultList();
	}

	/**
	 * @throws EntityNotFoundException
	 * @see de.warehouse.dao.interfaces.IArticleDAO#updateStorageLocationOfArticle(String,
	 *      String)
	 */
	@Override
	public void updateStorageLocationOfArticle(String articleId, String storageLocationId)
			throws EntityNotFoundException {
		logger.info("updateStorageLocationOfArticle: " + articleId + "," + storageLocationId);

		Article a = this.em.find(Article.class, articleId);
		if (null == a) {
			logger.error("Article with id " + articleId + " not found.");

			throw new EntityNotFoundException(String.format("Article with id: '%s' not found.", articleId));
		}

		StorageLocation s = this.em.find(StorageLocation.class, storageLocationId);
		if (null == s) {
			logger.error("StorageLocation with id " + storageLocationId + " not found.");

			throw new EntityNotFoundException(
					String.format("StorageLocation with id: '%s' not found.", storageLocationId));
		}

		logger.info("Set new storage location " + storageLocationId + " of article + " + articleId);
		
		a.setStorageLocation(s);

		this.em.merge(a);
		
		logger.info("Savepoint.");
	}

	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#updateQuantityOnStockOfArticle(String,
	 *      int)
	 */
	@Override
	public void updateQuantityOnStockOfArticle(String articleId, int receiptQuantity) throws EntityNotFoundException {
		logger.info("updateQuantityOnStockOfArticle: " + articleId + "," + receiptQuantity);
		
		if (0 == receiptQuantity) {
			logger.error("receiptQuantity is 0!");
			
			return;
		}

		Article a = this.em.find(Article.class, articleId);
		if (null == a) {
			logger.error("Article with id " + articleId + " not found.");
			
			throw new EntityNotFoundException(String.format("Article with id: '%s' not found.", articleId));
		}

		int newQuantityOnStock = a.getQuantityOnStock() + receiptQuantity;

		logger.info("Set new quantity on stock " + newQuantityOnStock + " of article + " + articleId);
		
		a.setQuantityOnStock(newQuantityOnStock);

		this.em.merge(a);
		
		logger.info("Savepoint.");
	}

	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#create(Article)
	 */
	@Override
	public Article create(Article a) throws EntityWithIdentifierAlreadyExistsException {
		if (this.findById(a.getCode()) != null) {
			logger.error("Article with id " + a.getCode() + " already exists.");
			
			throw new EntityWithIdentifierAlreadyExistsException(
					String.format("Article with code '%s' already exists.", a.getCode()));
		}
		this.em.persist(a);
		
		logger.info("Savepoint.");
		
		return a;
	}

	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#update(Article)
	 */
	@Override
	public Article update(Article a) {
		logger.info("Article with id " + a.getCode() + " updating.");
		return this.em.merge(a);
	}

	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#delete(Article)
	 */
	@Override
	public void delete(Article a) {
		this.em.remove(em.contains(a) ? a : em.merge(a));
		
		logger.info("Article with id " + a.getCode() + " removed.");
	}
}
