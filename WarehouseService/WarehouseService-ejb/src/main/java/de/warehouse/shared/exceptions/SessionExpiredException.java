package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class SessionExpiredException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7456097254280414753L;
	
	public static final int ERROR_CODE = 400;


	public SessionExpiredException() {
	}

	public SessionExpiredException(String message) {
		super(message);
	}

	
	public SessionExpiredException(String message, Throwable cause) {
		super(message, cause);
	}


}
