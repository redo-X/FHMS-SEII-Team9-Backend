package de.warehouse.dto.requests;

import de.warehouse.dto.DataTransferObjectBase;
import de.warehouse.dto.ResponseBase;

public class LogoutRequest extends DataTransferObjectBase {

	private static final long serialVersionUID = 2203008283670200283L;
	
	private int sessionId;

	/**
	 * @return the sessionId
	 */
	public int getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	


	
}
