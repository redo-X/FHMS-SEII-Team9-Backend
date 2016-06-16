package de.warehouse.shared.interfaces;

public interface ICommissionMessages {
	public void commitMessage(int sessionId, int customerOrderPositionId, int differenceQuantity, String note);
}