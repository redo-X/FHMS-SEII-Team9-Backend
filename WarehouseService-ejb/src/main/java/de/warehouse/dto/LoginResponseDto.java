package de.warehouse.dto;

public class LoginResponseDto extends DtoBase {

	private static final long serialVersionUID = -4360169674961128127L;
	
	private Integer role;
	private Integer sessionId;
	/**
	 * @return the role
	 */
	public Integer getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(Integer role) {
		this.role = role;
	}
	/**
	 * @return the sessionId
	 */
	public Integer getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	
}
