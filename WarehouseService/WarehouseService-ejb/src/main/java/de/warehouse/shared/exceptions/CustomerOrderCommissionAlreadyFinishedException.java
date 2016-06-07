package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class CustomerOrderCommissionAlreadyFinishedException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -641342854536381184L;

	public CustomerOrderCommissionAlreadyFinishedException() {

	}

	public CustomerOrderCommissionAlreadyFinishedException(String s) {
		super(s);

	}

	public CustomerOrderCommissionAlreadyFinishedException(String s, Throwable cause) {
		super(s, cause);

	}

}
