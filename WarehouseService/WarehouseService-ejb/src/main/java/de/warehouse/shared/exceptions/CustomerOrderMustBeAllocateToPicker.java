package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class CustomerOrderMustBeAllocateToPicker extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7666803980361857449L;

	public CustomerOrderMustBeAllocateToPicker() {
	}

	public CustomerOrderMustBeAllocateToPicker(String s) {
		super(s);
	}

	public CustomerOrderMustBeAllocateToPicker(String s, Throwable cause) {
		super(s, cause);
	}

}
