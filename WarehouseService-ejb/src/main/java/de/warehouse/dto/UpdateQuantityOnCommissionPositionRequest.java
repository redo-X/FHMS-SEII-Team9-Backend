package de.warehouse.dto;

public class UpdateQuantityOnCommissionPositionRequest extends DtoBase {

	private static final long serialVersionUID = 1260836931671776938L;
	
	private Integer commissionPositionId;
	private Integer quantityToCommit;
	/**
	 * @return the commissionPositionId
	 */
	public Integer getCommissionPositionId() {
		return commissionPositionId;
	}
	/**
	 * @param commissionPositionId the commissionPositionId to set
	 */
	public void setCommissionPositionId(Integer commissionPositionId) {
		this.commissionPositionId = commissionPositionId;
	}
	/**
	 * @return the quantityToCommit
	 */
	public Integer getQuantityToCommit() {
		return quantityToCommit;
	}
	/**
	 * @param quantityToCommit the quantityToCommit to set
	 */
	public void setQuantityToCommit(Integer quantityToCommit) {
		this.quantityToCommit = quantityToCommit;
	}

	
}
