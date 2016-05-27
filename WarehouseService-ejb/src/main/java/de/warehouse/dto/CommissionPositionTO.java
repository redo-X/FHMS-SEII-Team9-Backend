package de.warehouse.dto;

public class CommissionPositionTO extends ResponseBase {
	
	private static final long serialVersionUID = 939506005597148808L;

	private Integer commissionPositionId;

	private String articleCode;
	
	private String articleName;
	
	private String storageLocation;
	
	private Integer quantityToCommit;
	private Integer quantityOnStock;
	
	
	public CommissionPositionTO() {
		
	}
	/**
	 * @param commissionPositionId
	 * @param articleCode
	 * @param articleName
	 * @param storageLocation
	 * @param quantityToCommit
	 * @param quantityOnStock
	 */
	public CommissionPositionTO(Integer commissionPositionId, String articleCode, String articleName,
			String storageLocation, Integer quantityToCommit, Integer quantityOnStock) {
		this.commissionPositionId = commissionPositionId;
		this.articleCode = articleCode;
		this.articleName = articleName;
		this.storageLocation = storageLocation;
		this.quantityToCommit = quantityToCommit;
		this.quantityOnStock = quantityOnStock;
	}
	
	
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
	 * @return the articleCode
	 */
	public String getArticleCode() {
		return articleCode;
	}
	/**
	 * @param articleCode the articleCode to set
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}
	/**
	 * @return the articleName
	 */
	public String getArticleName() {
		return articleName;
	}
	/**
	 * @param articleName the articleName to set
	 */
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	/**
	 * @return the storageLocation
	 */
	public String getStorageLocation() {
		return storageLocation;
	}
	/**
	 * @param storageLocation the storageLocation to set
	 */
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
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
	/**
	 * @return the quantityOnStock
	 */
	public Integer getQuantityOnStock() {
		return quantityOnStock;
	}
	/**
	 * @param quantityOnStock the quantityOnStock to set
	 */
	public void setQuantityOnStock(Integer quantityOnStock) {
		this.quantityOnStock = quantityOnStock;
	}
	
	
}
