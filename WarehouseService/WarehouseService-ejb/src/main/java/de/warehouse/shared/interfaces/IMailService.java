package de.warehouse.shared.interfaces;

public interface IMailService {
	public boolean Send(String recipient, String subject, String message);
	public boolean Send(String recipients[], String subject, String message);
}
