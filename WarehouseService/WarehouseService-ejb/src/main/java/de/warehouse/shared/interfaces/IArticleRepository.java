package de.warehouse.shared.interfaces;

import java.util.List;

import de.warehouse.persistence.Article;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.EntityWithIdentifierAlreadyExistsException;
import de.warehouse.shared.exceptions.SessionExpiredException;

/**
 * @see de.warehouse.dao.interfaces.IArticleDAO
 */
public interface IArticleRepository {
	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#findById(String)
	 */
	public Article findById(String code);
	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#getAll()
	 */
	public List<Article> getAll(int sessionId) throws SessionExpiredException, AccessDeniedException;
	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#create(Article)
	 */
	public Article create(Article article) throws EntityWithIdentifierAlreadyExistsException;
	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#update(Article)
	 */
	public Article update(Article article);
	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#updateStorageLocationOfArticle(String, String)
	 */
	public void updateStorageLocationOfArticle(String articleCode, String storageLocationCode) throws EntityNotFoundException;
	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#updateQuantityOnStockOfArticle(String, int)
	 */
	public void updateQuantityOnStockOfArticle(String articleCode, int receiptQuantity) throws EntityNotFoundException;
	/**
	 * @see de.warehouse.dao.interfaces.IArticleDAO#delete(Article)
	 */
	public void remove(Article article);
}
