package de.warehouse.dto.requests.commission;

import de.warehouse.dto.DataTransferObjectBase;

public class UpdateStartOfCommissionRequest extends DataTransferObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5717327935805827829L;
	
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
