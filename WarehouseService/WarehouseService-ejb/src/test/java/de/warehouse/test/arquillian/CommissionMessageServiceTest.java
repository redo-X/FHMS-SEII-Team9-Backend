package de.warehouse.test.arquillian;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.warehouse.dao.interfaces.ICommissionMessagesDAO;
import de.warehouse.shared.interfaces.ICommissionMessages;
import de.warehouse.test.ArquillianTestWithSessionsBase;

@RunWith(Arquillian.class)
public class CommissionMessageServiceTest extends ArquillianTestWithSessionsBase {

	private final int VALID_POSITION_ID = 8;

	@EJB
	private ICommissionMessages commissionMessageBean;
	
	@EJB
	private ICommissionMessagesDAO commissionMessageDAO;

	
	@Test
	@InSequence(1)
	public void testCommitMessage(){
		this.commissionMessageBean.commitMessage(sessionIdOfKommissionierer, VALID_POSITION_ID, 100, "TEST");
	}
	
	@Test
	@InSequence(2)
	public void testGetPendingMessagesIsTwo() {
		assertEquals("Message must be propagated to two employees.", 2, this.commissionMessageDAO.getPendingMessages().size(), 0);
	}
}
