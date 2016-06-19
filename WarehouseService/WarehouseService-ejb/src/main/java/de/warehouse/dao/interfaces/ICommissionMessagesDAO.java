package de.warehouse.dao.interfaces;

import java.util.List;

import de.warehouse.persistence.CustomerOrderPositionMessage;

/**
 * This interface encapsulates the data access logic for the customer orders (in this business context commissions).
 * @author David
 */
public interface ICommissionMessagesDAO {
	/**
	 * Creates a new message to notify the responsible employees about something in the corporation.
	 * @param sessionId to identify the sender (employee)
	 * @param customerOrderPositionId the trigger for the message
	 * @param responsibleEmployeeId responsible employee
	 * @param differenceQuantity physically stored quantity
	 * @param note additional information about the situation
	 */
	public void commitMessage(int sessionId, int customerOrderPositionId, int responsibleEmployeeId, int differenceQuantity, String note);
	/**
	 * @return messages that are not sent via e-mail
	 */
	public List<CustomerOrderPositionMessage> getPendingMessages();
	/**
	 * Set the e-mail sent flag to true
	 * @param customerOrderPositionMessageId
	 */
	public void updateMailFlag( int customerOrderPositionMessageId);
	/**
	 * Create the message in the database
	 * @param e instance of the entity
	 */
	public void create(CustomerOrderPositionMessage e);
	/**
	 * Removes the message
	 * @param e instance of the entity
	 */
	public void delete(CustomerOrderPositionMessage e);
}
