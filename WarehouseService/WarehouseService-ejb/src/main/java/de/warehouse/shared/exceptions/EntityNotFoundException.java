package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class EntityNotFoundException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6019238292907395549L;

	public EntityNotFoundException() {
	
	}

	public EntityNotFoundException(String s) {
		super(s);
	}

	public EntityNotFoundException(String s, Throwable cause) {
		super(s, cause);
	}

}
