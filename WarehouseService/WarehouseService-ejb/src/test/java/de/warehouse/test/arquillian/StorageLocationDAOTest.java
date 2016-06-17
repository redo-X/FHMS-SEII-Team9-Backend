package de.warehouse.test.arquillian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.warehouse.dao.interfaces.IStorageLocationDAO;
import de.warehouse.persistence.StorageLocation;
import de.warehouse.shared.exceptions.EntityWithIdentifierAlreadyExistsException;
import de.warehouse.test.ArquillianTestWithSessionsBase;

/**
 * @author Florian
 */
@RunWith(Arquillian.class)
public class StorageLocationDAOTest extends ArquillianTestWithSessionsBase {

	private final String VALID_CODE = "AA-01-01";
	private final String VALID_CODE_WITH_TWO_ARTICLES = "AA-01-04";

	@EJB
	private IStorageLocationDAO storageLocationDAO;

	@Test
	@InSequence(1)
	public void testGetAllAfterDbInit() {
		assertEquals(5, this.storageLocationDAO.getAll().size(), 0);
	}

	@Test
	@InSequence(2)
	public void testFindById() {
		assertNotNull(this.storageLocationDAO.findById(VALID_CODE));
	}

	@Test
	@InSequence(3)
	public void testFindByIdWithArticles() {
		StorageLocation sl = this.storageLocationDAO.findByIdWithArticles(VALID_CODE_WITH_TWO_ARTICLES);

		assertNotNull(sl);

		assertEquals(2, sl.getStockArticles().size(), 0);

	}

	@Test
	@InSequence(4)
	public void testCreateStorageLocation() {
		StorageLocation sl1 = new StorageLocation("AA-01-10");

		try {
			sl1 = this.storageLocationDAO.create(sl1);
		} catch (EntityWithIdentifierAlreadyExistsException e) {
			fail(e.getMessage());
		}

		assertNotNull(this.storageLocationDAO.findById("AA-01-10"));
	}

	@Test(expected = EntityWithIdentifierAlreadyExistsException.class)
	@InSequence(5)
	public void testCreateStorageLocationWithDuplicateKey() throws EntityWithIdentifierAlreadyExistsException {
		StorageLocation sl1 = new StorageLocation("AA-01-01");

		sl1 = this.storageLocationDAO.create(sl1);

		assertNotNull(this.storageLocationDAO.findById("AA-01-10"));
	}

	@Test
	@InSequence(6)
	public void testDeleteStorageLocation() {
		StorageLocation sl1 = this.storageLocationDAO.findById("AA-01-10");

		assertNotNull(sl1);

		this.storageLocationDAO.delete(sl1);

		assertNull(this.storageLocationDAO.findById("AA-01-10"));
	}
}