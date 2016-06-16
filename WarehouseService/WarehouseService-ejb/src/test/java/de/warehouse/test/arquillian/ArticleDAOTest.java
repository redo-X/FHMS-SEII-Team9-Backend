package de.warehouse.test.arquillian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.warehouse.dao.interfaces.IArticleDAO;
import de.warehouse.persistence.Article;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.test.ArquillianTestWithSessionsBase;

@RunWith(Arquillian.class)
public class ArticleDAOTest extends ArquillianTestWithSessionsBase {

	private final String VALID_ARTICLE_ID = "A2000";
	private final String INVALID_ARTICLE_ID = "INVALID_ID";

	private final String VALID_STORAGE_LOCATION_ID = "AA-01-04";
	private final String INVALID_STORAGE_LOCATION_ID = "INVALID_ID";

	@EJB
	private IArticleDAO articleDAO;

	@Test(expected = EntityNotFoundException.class)
	public void testGetArticlesByInvalidStorageLocation() throws EntityNotFoundException {
		this.articleDAO.getByStorageLocation(INVALID_STORAGE_LOCATION_ID);
	}

	@Test
	@InSequence(1)
	public void testGetArticlesByStorageLocation() {

		try {
			List<Article> articles = this.articleDAO.getByStorageLocation(VALID_STORAGE_LOCATION_ID);

			assertNotNull(articles);
			assertEquals(2, articles.size(), 0);
		} catch (EntityNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(2)
	public void testUpdateOfStorageLocationWithValidArticleAndStorageLocationId() {
		try {
			this.articleDAO.updateStorageLocationOfArticle(VALID_ARTICLE_ID, VALID_STORAGE_LOCATION_ID);
		} catch (EntityNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = EntityNotFoundException.class)
	public void testUpdateOfStorageLocationWithInvalidStorageLocationId() throws EntityNotFoundException {
		this.articleDAO.updateStorageLocationOfArticle(VALID_ARTICLE_ID, INVALID_STORAGE_LOCATION_ID);
	}

	@Test(expected = EntityNotFoundException.class)
	public void testUpdateOfStorageLocationWithInvalidArticleId() throws EntityNotFoundException {
		this.articleDAO.updateStorageLocationOfArticle(INVALID_ARTICLE_ID, VALID_STORAGE_LOCATION_ID);
	}

	@Test(expected = EntityNotFoundException.class)
	public void testUpdateOfStorageLocationWithInvalidArticleAndStorageLocationId() throws EntityNotFoundException {
		this.articleDAO.updateStorageLocationOfArticle(INVALID_ARTICLE_ID, INVALID_STORAGE_LOCATION_ID);
	}

	@Test
	public void testUpdateQuantityOnStockOfArticleWithValidArticleId() {
		try {
			int quantityOnStockBeforeUpdate = this.articleDAO.findById(VALID_ARTICLE_ID).getQuantityOnStock();
			this.articleDAO.updateQuantityOnStockOfArticle(VALID_ARTICLE_ID, 1);
			int quantityOnStockAfterUpdate = this.articleDAO.findById(VALID_ARTICLE_ID).getQuantityOnStock();

			assertEquals(quantityOnStockBeforeUpdate + 1, quantityOnStockAfterUpdate, 0);
		} catch (EntityNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateQuantityOnStockOfArticleWithValidArticleIdAndZeroQuantity() {
		try {
			int quantityOnStockBeforeUpdate = this.articleDAO.findById(VALID_ARTICLE_ID).getQuantityOnStock();
			this.articleDAO.updateQuantityOnStockOfArticle(VALID_ARTICLE_ID, 0);
			int quantityOnStockAfterUpdate = this.articleDAO.findById(VALID_ARTICLE_ID).getQuantityOnStock();

			assertEquals(quantityOnStockBeforeUpdate, quantityOnStockAfterUpdate, 0);
		} catch (EntityNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = EntityNotFoundException.class)
	public void testUpdateQuantityOnStockOfArticleWithInvalidArticleId() throws EntityNotFoundException {
		this.articleDAO.updateQuantityOnStockOfArticle(INVALID_ARTICLE_ID, 1);
	}
}