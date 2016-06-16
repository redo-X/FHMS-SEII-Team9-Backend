package de.warehouse.test.arquillian;

import static org.junit.Assert.*;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.warehouse.persistence.Role;
import de.warehouse.persistence.WarehouseSession;
import de.warehouse.shared.exceptions.AccessDeniedException;
import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.SessionExpiredException;
import de.warehouse.shared.exceptions.UsernamePasswordMismatchException;
import de.warehouse.shared.interfaces.ISessionManagement;
import de.warehouse.test.ArquillianTestBase;

@RunWith(Arquillian.class)
public class SessionManagementTest extends ArquillianTestBase {
	@EJB
	private ISessionManagement sessionManagementDummy;

	@Test
	@InSequence(1)
	public void testLogin() {
		int sessionId = -1;

		try {
			sessionId = this.sessionManagementDummy.createSession(1, "geheim");
		} catch (EntityNotFoundException | UsernamePasswordMismatchException e) {
			fail(e.getMessage());
		}

		assertNotEquals(-1, sessionId, 0);
	}

	@Test
	@InSequence(2)
	public void testFindCreatedSession() {
		WarehouseSession session = this.sessionManagementDummy.getById(11);

		assertNotEquals(null, session);
	}

	@Test
	@InSequence(3)
	public void testEnsureAuthorizationPositive() {
		try {
			this.sessionManagementDummy.ensureAuthorization(Role.Kommissionierer, 11);
		} catch (SessionExpiredException | AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = AccessDeniedException.class)
	@InSequence(4)
	public void testEnsureAuthorizationOnLageristSecuredMethod() throws AccessDeniedException {
		try {
			this.sessionManagementDummy.ensureAuthorization(Role.Lagerist, 11);
		} catch (SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = AccessDeniedException.class)
	@InSequence(5)
	public void testEnsureAuthorizationOnAdministratorSecuredMethod() throws AccessDeniedException {
		try {
			this.sessionManagementDummy.ensureAuthorization(Role.Administrator, 11);
		} catch (SessionExpiredException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@InSequence(10)
	public void testLogout() {
		this.sessionManagementDummy.closeSession(11);
	}

	@Test(expected = SessionExpiredException.class)
	@InSequence(11)
	public void testEnsureAuthorizationWithExpiredSession() throws SessionExpiredException {
		try {
			this.sessionManagementDummy.ensureAuthorization(Role.Administrator, 11);
		} catch (AccessDeniedException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testFindNotCreatedSession() {
		WarehouseSession session = this.sessionManagementDummy.getById(-999);

		assertEquals(null, session);
	}

}