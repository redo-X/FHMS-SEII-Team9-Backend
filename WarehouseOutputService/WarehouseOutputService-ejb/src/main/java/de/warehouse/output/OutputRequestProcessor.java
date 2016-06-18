package de.warehouse.output;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.logging.Logger;

import de.warehouse.shared.interfaces.IMailService;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/Queue1"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "DocType LIKE 'CommissionInfo'") })
public class OutputRequestProcessor implements MessageListener {

	private static final Logger logger = Logger.getLogger(OutputRequestProcessor.class);

	@EJB
	private IMailService mailService;

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			MapMessage msg = (MapMessage) message;

			String recipient = msg.getString("Recipient");
			String subject = msg.getString("Subject");
			String body = msg.getString("Body");

			this.mailService.send(recipient, subject, body);

			logger.info("Received message from java:/jms/queue/Queue1: " + body);
		} catch (JMSException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
