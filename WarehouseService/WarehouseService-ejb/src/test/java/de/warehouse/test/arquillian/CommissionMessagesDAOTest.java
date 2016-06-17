package de.warehouse.test.arquillian;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.warehouse.dao.interfaces.ICommissionMessagesDAO;
import de.warehouse.persistence.CustomerOrderPositionMessage;
import de.warehouse.test.ArquillianTestWithSessionsBase;

@RunWith(Arquillian.class)
@Transactional
public class CommissionMessagesDAOTest extends ArquillianTestWithSessionsBase {

	private final int VALID_EMPLOYEE_ID = 1;
	private final int VALID_POSITION_ID = 8;

	@EJB
	private ICommissionMessagesDAO commissionMessageDAO;

	@Test
	@InSequence(0)
	public void testGetPendingMessagesIsZero() {
		assertEquals(0, this.commissionMessageDAO.getPendingMessages().size(), 0);
	}

	@Test
	@InSequence(1)
	@Transactional(value=TransactionMode.COMMIT)
	public void testCommitMessage() {
		this.commissionMessageDAO.commitMessage(sessionIdOfKommissionierer, VALID_POSITION_ID, VALID_EMPLOYEE_ID, 20, "TEST");
	}

	@Test
	@InSequence(2)
	public void testGetPendingMessagesIsOne() {
		assertEquals(1, this.commissionMessageDAO.getPendingMessages().size(), 0);
	}

	@Test
	@InSequence(3)
	@Transactional(value=TransactionMode.COMMIT)
	public void testUpdateMailFlag() {
		for(CustomerOrderPositionMessage m : this.commissionMessageDAO.getPendingMessages()) {
			this.commissionMessageDAO.updateMailFlag(m.getCustomerOrderPositionMessageId());
		}		
	}

	@Test
	@InSequence(4)
	public void testGetPendingMessagesIsZeroAfterUpdateMailFlag() {
		assertEquals(0, this.commissionMessageDAO.getPendingMessages().size(), 0);
	}
}
