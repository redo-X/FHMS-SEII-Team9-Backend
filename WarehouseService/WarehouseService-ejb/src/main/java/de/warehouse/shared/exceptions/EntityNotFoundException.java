package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class EntityNotFoundException extends RemoteException {

	public EntityNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public EntityNotFoundException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public EntityNotFoundException(String s, Throwable cause) {
		super(s, cause);
		// TODO Auto-generated constructor stub
	}

}
