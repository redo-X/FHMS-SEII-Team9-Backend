package de.warehouse.dto.requests.article;

import de.warehouse.dto.DataTransferObjectBase;

public class UpdateStorageLocationOfArticleRequest extends DataTransferObjectBase {

	private static final long serialVersionUID = 8480409668427533212L;
	
	private String articleCode;
	private String storageLocationCode;
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
	 * @return the storageLocationCode
	 */
	public String getStorageLocationCode() {
		return storageLocationCode;
	}
	/**
	 * @param storageLocationCode the storageLocationCode to set
	 */
	public void setStorageLocationCode(String storageLocationCode) {
		this.storageLocationCode = storageLocationCode;
	}
}