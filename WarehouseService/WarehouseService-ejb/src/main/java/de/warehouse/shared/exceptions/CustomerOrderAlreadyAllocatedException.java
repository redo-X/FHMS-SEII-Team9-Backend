package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class CustomerOrderAlreadyAllocatedException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8789078169288540431L;

	public CustomerOrderAlreadyAllocatedException() {
	}

	public CustomerOrderAlreadyAllocatedException(String s) {
		super(s);
	}

	public CustomerOrderAlreadyAllocatedException(String s, Throwable cause) {
		super(s, cause);
	}

}
