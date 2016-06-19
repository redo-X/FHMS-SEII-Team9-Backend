package de.warehouse.test;

import static org.junit.Assert.fail;

import javax.ejb.EJB;

import org.junit.After;
import org.junit.Before;

import de.warehouse.shared.exceptions.EntityNotFoundException;
import de.warehouse.shared.exceptions.UsernamePasswordMismatchException;
import de.warehouse.shared.interfaces.ISessionManagement;

/**
 * This class expand the superclass with logins of all role types.
 * @author David
 * @see de.warehouse.test.ArquillianTestBase
 */
public abstract class ArquillianTestWithSessionsBase extends ArquillianTestBase {
	
	@EJB
	private ISessionManagement sessionManagementDummy;

	protected int sessionIdOfKommissionierer;
	protected int sessionIdOfLagerist;
	protected int sessionIdOfAdministrator;
	
	@Before
	public void loginKommissioniererAndLagerist()  {
		try {
			this.sessionIdOfKommissionierer = this.sessionManagementDummy.createSession(1, "geheim");
			this.sessionIdOfLagerist = this.sessionManagementDummy.createSession(2, "geheim");
			this.sessionIdOfAdministrator = this.sessionManagementDummy.createSession(3, "geheim");
		} catch (EntityNotFoundException | UsernamePasswordMismatchException e) {
			fail(e.getMessage());
		}
	}
	@After
	public void logoutKommissioniererAndLagerist()  {
		this.sessionManagementDummy.closeSession(this.sessionIdOfKommissionierer);
		this.sessionManagementDummy.closeSession(this.sessionIdOfLagerist);
		this.sessionManagementDummy.closeSession(this.sessionIdOfAdministrator);
	}
	
}
