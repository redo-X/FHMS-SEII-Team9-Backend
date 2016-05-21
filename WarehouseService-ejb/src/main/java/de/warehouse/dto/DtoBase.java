package de.warehouse.dto;

import java.io.Serializable;

public abstract class DtoBase implements Serializable {

	private static final long serialVersionUID = 8861713728527618365L;
	
	// 0 => Alles OK
	// < 0 => Fehler
	// > 0 => Warnungen/Hinweise
	private Integer resultCode;
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
