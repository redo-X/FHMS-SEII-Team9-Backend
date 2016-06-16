package de.warehouse.dto.requests.article;

import de.warehouse.dto.DataTransferObjectBase;

public class UpdateQuantityOnStockOfArticleRequest extends DataTransferObjectBase {

	private static final long serialVersionUID = 6679620445391589282L;
	
	private String articleCode;
	private int receiptQuantity;
	
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
	 * @return the receiptQuantity
	 */
	public int getReceiptQuantity() {
		return receiptQuantity;
	}
	/**
	 * @param receiptQuantity the receiptQuantity to set
	 */
	public void setReceiptQuantity(int receiptQuantity) {
		this.receiptQuantity = receiptQuantity;
	}
	
}
