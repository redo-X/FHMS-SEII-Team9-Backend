package de.warehouse.dto.requests.commission;

import de.warehouse.dto.DataTransferObjectBase;

public class GetPendingPositionsByCommissionIdRequest extends DataTransferObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 591730296342617531L;
	
	private int commissionId;

	/**
	 * @return the commissionId
	 */
	public int getCommissionId() {
		return commissionId;
	}

	/**
	 * @param commissionId the commissionId to set
	 */
	public void setCommissionId(int commissionId) {
		this.commissionId = commissionId;
	}
}
