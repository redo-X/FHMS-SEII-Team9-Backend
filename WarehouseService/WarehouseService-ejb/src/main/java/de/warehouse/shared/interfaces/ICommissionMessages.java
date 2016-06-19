package de.warehouse.shared.interfaces;

/**
 * @author David
 */
public interface ICommissionMessages {
	/**
	 * @see de.warehouse.dao.interfaces.ICommissionMessagesDAO#commitMessage(int, int, int, int, String)
	 */
	public void commitMessage(int sessionId, int customerOrderPositionId, int differenceQuantity, String note);
}