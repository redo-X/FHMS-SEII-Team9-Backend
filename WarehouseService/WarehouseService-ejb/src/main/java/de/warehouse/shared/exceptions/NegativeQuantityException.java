package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class NegativeQuantityException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7976235088643784997L;

	public NegativeQuantityException() {
	}

	public NegativeQuantityException(String s) {
		super(s);
	}

	public NegativeQuantityException(String s, Throwable cause) {
		super(s, cause);
	}

}
