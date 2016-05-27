package de.warehouse.test.mail;

import static org.junit.Assert.*;

import org.junit.Test;

import de.warehouse.mail.MailService;

public class MailServiceTest {
	
	@Test
	public void testIsOverdueFalse() {
		MailService ms = new MailService();

		String[] recps = { "melcherdavid@t-online.de" };
		boolean sent = ms.Send(recps, "TEST", "Testmail from JavaMail-API");
		
		assertTrue(sent);
	}
}
