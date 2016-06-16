/**
 * 
 */
package de.warehouse.persistence;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.warehouse.dao.interfaces.IArticleDAO;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.EntityWithIdentifierAlreadyExistsException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.IArticleRepository;
import de.warehouse.shared.interfaces.ISessionManagement;

/**
 * @see de.warehouse.shared.interfaces.IArticleRepository
 */
@Stateless
public class ArticleRepository implements IArticleRepository {

	@EJB
	private ISessionManagement sessionManagement;
	
	@EJB
	private IArticleDAO articleDAO;

	/**
	 * @see de.warehouse.shared.interfaces.IArticleRepository#findById(java.lang.Integer)
	 */
	@Override
	public Article findById(String code) {
		return this.articleDAO.findById(code);
	}

	/**
	 * @see de.warehouse.shared.interfaces.IArticleRepository#getAll(int)
	 */
	@Override
	public List<Article> getAll(int sessionId) throws SessionExpiredException, AccessDeniedException {
		this.sessionManagement.ensureAuthorization(Role.Lagerist, sessionId);

		return this.articleDAO.getAll();
	}

	/**
	 * @see de.warehouse.shared.interfaces.IArticleRepository#create(de.warehouse.persistence.Article)
	 */
	@Override
	public Article create(Article article) throws EntityWithIdentifierAlreadyExistsException {
		return this.articleDAO.create(article);
	}

	/**
	 * @see de.warehouse.shared.interfaces.IArticleRepository#update(de.warehouse.persistence.Article)
	 */
	@Override
	public Article update(Article article) {
		return this.articleDAO.update(article);
	}

	/**
	 * @see de.warehouse.shared.interfaces.IArticleRepository#updateStorageLocationOfArticle(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateStorageLocationOfArticle(String articleCode, String storageLocationCode) throws EntityNotFoundException {
		this.articleDAO.updateStorageLocationOfArticle(articleCode, storageLocationCode);
	}

	/**
	 * @see de.warehouse.shared.interfaces.IArticleRepository#updateQuantityOnStockOfArticle(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateQuantityOnStockOfArticle(String articleCode, int receiptQuantity) throws EntityNotFoundException {
		this.articleDAO.updateQuantityOnStockOfArticle(articleCode, receiptQuantity);
	}

	/**
	 * @see de.warehouse.shared.interfaces.IArticleRepository#remove(de.warehouse.persistence.Article)
	 */
	@Override
	public void remove(Article article) {
		this.articleDAO.delete(article);
	}
}