package de.warehouse.shared.exceptions;

import java.rmi.RemoteException;

public class PickedQuantityTooHighException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6353343278278793463L;

	public PickedQuantityTooHighException() {
	}

	public PickedQuantityTooHighException(String s) {
		super(s);
		}

	public PickedQuantityTooHighException(String s, Throwable cause) {
		super(s, cause);
	}

}
