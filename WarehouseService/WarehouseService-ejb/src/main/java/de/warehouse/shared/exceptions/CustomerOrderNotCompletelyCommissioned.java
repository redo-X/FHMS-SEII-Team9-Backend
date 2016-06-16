package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class CustomerOrderNotCompletelyCommissioned extends RemoteException {

	private static final long serialVersionUID = -4324976569372610312L;

	public CustomerOrderNotCompletelyCommissioned() {
	}

	public CustomerOrderNotCompletelyCommissioned(String s) {
		super(s);
	}

	public CustomerOrderNotCompletelyCommissioned(String s, Throwable cause) {
		super(s, cause);
	}

}
