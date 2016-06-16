package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class EntityWithIdentifierAlreadyExistsException extends RemoteException {

	private static final long serialVersionUID = -6916221102788640903L;

	public EntityWithIdentifierAlreadyExistsException() {
	}

	public EntityWithIdentifierAlreadyExistsException(String s) {
		super(s);
	}

	public EntityWithIdentifierAlreadyExistsException(String s, Throwable cause) {
		super(s, cause);
	}

}
