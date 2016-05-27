package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class UsernamePasswordMismatchException extends RemoteException {

	public UsernamePasswordMismatchException() {
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordMismatchException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordMismatchException(String s, Throwable cause) {
		super(s, cause);
		// TODO Auto-generated constructor stub
	}

}
