package de.warehouse.dao.interfaces;

import java.util.List;

import de.warehouse.persistence.CustomerOrderPositionMessage;

public interface ICommissionMessagesDAO {
	public void commitMessage(int sessionId, int customerOrderPositionId, int responsibleEmployeeId, int differenceQuantity, String note);
	
	public List<CustomerOrderPositionMessage> getPendingMessages();
	
	public void updateMailFlag( int customerOrderPositionMessageId);
	
	public void create(CustomerOrderPositionMessage e);
	public void delete(CustomerOrderPositionMessage e);
}
