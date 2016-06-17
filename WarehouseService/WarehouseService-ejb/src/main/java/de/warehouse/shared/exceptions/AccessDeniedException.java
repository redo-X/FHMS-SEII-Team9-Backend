package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class AccessDeniedException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5719888236123672592L;

	public static final int ERROR_CODE = 401;

	public AccessDeniedException() {
	}

	public AccessDeniedException(String message) {
		super(message);
	}

	
	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}


}
