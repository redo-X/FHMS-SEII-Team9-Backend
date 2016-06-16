package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class MustBeGreaterOrLessThanZeroException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5239396271709073522L;

	public MustBeGreaterOrLessThanZeroException() {
	}

	public MustBeGreaterOrLessThanZeroException(String s) {
		super(s);
	}

	public MustBeGreaterOrLessThanZeroException(String s, Throwable cause) {
		super(s, cause);
	}

}
