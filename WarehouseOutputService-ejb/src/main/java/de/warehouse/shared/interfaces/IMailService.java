package de.warehouse.shared.interfaces;

import javax.ejb.Local;

@Local
public interface IMailService {
	/**
	 * Sends a plain text e-mail to one recipient. You can specify the subject and the message.
	 * @param recipient E-Mail address of the recipient
	 * @param subject Subject of the mail like 'INVOICE NUMBER 12345'
	 * @param message Multiline text of the e-mail.
	 * @return true => E-Mail transport successful
	 * 		   false => E-Mail transport failed
	 */
	public boolean send(String recipient, String subject, String message);
	/**
	 * Sends a plain text e-mail to one or more recipients. You can specify the subject and the message.
	 * @param recipient E-Mail address of the recipient
	 * @param subject Subject of the mail like 'INVOICE NUMBER 12345'
	 * @param message Multiline text of the e-mail.
	 * @return true => E-Mail transport successful
	 * 		   false => E-Mail transport failed
	 */
	public boolean send(String recipients[], String subject, String message);
}
