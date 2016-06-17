package de.warehouse.test.arquillian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.warehouse.persistence.Article;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.EntityWithIdentifierAlreadyExistsException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.interfaces.IArticleRepository;
import de.warehouse.test.ArquillianTestWithSessionsBase;

/**
 * @author Florian
 * @see http://stackoverflow.com/questions/1836364/bad-form-for-junit-test-to-
 *      throw-exception
 */
@RunWith(Arquillian.class)
public class ArticleRepositoryTest extends ArquillianTestWithSessionsBase {

	@EJB
	private IArticleRepository articleRepository;

	@Test(expected = AccessDeniedException.class)
	public void getAllAfterDatabaseInitWithUnsufficientRights() throws AccessDeniedException {
		try {
			assertEquals(null, this.articleRepository.getAll(super.sessionIdOfKommissionierer));
		} catch (SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = EntityWithIdentifierAlreadyExistsException.class)
	public void testTryCreateArticleWithExistingIdentifier() throws EntityWithIdentifierAlreadyExistsException {
		Article a = new Article();

		a.setCode("A2000");
		a.setName("Test");
		a.setQuantityOnStock(10);
		a.setQuantityOnCommitment(0);

		try {
			this.articleRepository.create(super.sessionIdOfAdministrator, a);
		} catch (SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(1)
	public void getAllAfterDatabaseInit() {
		try {
			Integer numberOfArticles = this.articleRepository.getAll(super.sessionIdOfLagerist).size();
			assertEquals("There must be 5 articles after database init in the database.", 5, numberOfArticles, 0);
		} catch (SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(2)
	public void testCreateNewArticle() {
		Article a = new Article();

		a.setCode("T0000");
		a.setName("Test");
		a.setQuantityOnStock(10);
		a.setQuantityOnCommitment(0);

		try {
			a = this.articleRepository.create(super.sessionIdOfAdministrator, a);

			assertNotEquals(null, this.articleRepository.findById(super.sessionIdOfAdministrator, a.getCode()));
		} catch (EntityWithIdentifierAlreadyExistsException | SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(3)
	public void getAllAfterDatabaseInitAndFirstCreationOfArticle() {
		try {
			Integer numberOfArticles = this.articleRepository.getAll(super.sessionIdOfLagerist).size();
			assertEquals("There should be 6 articles.", 6, numberOfArticles, 0);
		} catch (SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(4)
	public void testCreateArticleForTestSequence() {
		Article testArticle = new Article();

		testArticle.setCode("T9999");
		testArticle.setName("Test");
		testArticle.setQuantityOnStock(10);
		testArticle.setQuantityOnCommitment(0);

		try {
			testArticle = this.articleRepository.create(super.sessionIdOfAdministrator, testArticle);

			assertNotEquals(null,
					this.articleRepository.findById(super.sessionIdOfAdministrator, testArticle.getCode()));
		} catch (EntityWithIdentifierAlreadyExistsException | SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(5)
	public void testUpdateArticleForTestSequence() {
		try {
			Article testArticle = this.articleRepository.findById(super.sessionIdOfAdministrator, "T9999");

			testArticle.setName("Test 123");

			this.articleRepository.update(super.sessionIdOfAdministrator, testArticle);

			assertEquals("Test 123",
					this.articleRepository.findById(super.sessionIdOfAdministrator, "T9999").getName());
		} catch (SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(6)
	public void testDeleteArticleForTestSequence() {
		try {
			Article testArticle = this.articleRepository.findById(super.sessionIdOfAdministrator, "T9999");

			this.articleRepository.remove(super.sessionIdOfAdministrator, testArticle);
			
			assertEquals(null, this.articleRepository.findById(super.sessionIdOfAdministrator, "T9999"));
		} catch (SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}		
	}
}