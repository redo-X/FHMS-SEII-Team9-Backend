package de.warehouse.mail;

import de.warehouse.shared.interfaces.IMailService;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * Session Bean implementation class MailService
 */
@Singleton
@Local(IMailService.class)
@LocalBean
public class MailService implements IMailService {
	
	@Resource
	private String username, password, smtpServer;
	
	@Override
	public boolean Send(String recipient, String subject, String message) {
		return this.Send(new String[] {recipient}, subject, message);
	}
	@Override
	public boolean Send(String[] recipients, String subject, String msg) {

		Properties props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));

			StringJoiner joiner = new StringJoiner(", ");
			for (String str : recipients) {
				joiner.add(str);
			}

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(joiner.toString()));
			message.setSubject(subject);
			message.setText(msg);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		return true;
	}

}
