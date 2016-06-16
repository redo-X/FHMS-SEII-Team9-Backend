package de.warehouse.dto.requests.commission;

import de.warehouse.dto.DataTransferObjectBase;

public class UpdateFinishOfCommissionRequest extends DataTransferObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3934766151759892571L;
	
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
