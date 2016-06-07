package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class UsernamePasswordMismatchException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4474583097066787062L;

	public UsernamePasswordMismatchException() {
	}

	public UsernamePasswordMismatchException(String s) {
		super(s);
	}

	public UsernamePasswordMismatchException(String s, Throwable cause) {
		super(s, cause);
	}

}
