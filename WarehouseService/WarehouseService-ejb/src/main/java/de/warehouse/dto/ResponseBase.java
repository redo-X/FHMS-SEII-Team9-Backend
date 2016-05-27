package de.warehouse.dto;

public abstract class ResponseBase extends DataTransferObjectBase {

	private static final long serialVersionUID = 8861713728527618365L;
	
	public static final Integer OK_CODE = 0;
	
	private Integer resultCode = OK_CODE;
	private String resultMessage;
	/**
	 * @return the resultCode
	 */
	public Integer getResultCode() {
		return resultCode;
	}
	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	/**
	 * @return the resultMessage
	 */
	public String getResultMessage() {
		return resultMessage;
	}
	/**
	 * @param resultMessage the resultMessage to set
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	
	
}
