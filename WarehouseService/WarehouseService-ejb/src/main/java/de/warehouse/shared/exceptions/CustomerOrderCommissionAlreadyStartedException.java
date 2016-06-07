package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class CustomerOrderCommissionAlreadyStartedException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -570619604147158944L;

	public CustomerOrderCommissionAlreadyStartedException() {
	}

	public CustomerOrderCommissionAlreadyStartedException(String s) {
		super(s);
	}

	public CustomerOrderCommissionAlreadyStartedException(String s, Throwable cause) {
		super(s, cause);
	}

}
