package de.warehouse.messaging;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;

import org.jboss.logging.Logger;

import de.warehouse.picking.CommissionService;

/**
 * @author David
 */
@Stateless
@LocalBean
public class OutputRequesterBean {

	private static final Logger logger = Logger.getLogger(CommissionService.class);

	@Resource(lookup = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	@Resource(lookup = "java:/jms/queue/Queue1")
	private Queue queue;

	public void sendInfo(String docType, String recipient, String subject, String body) {
		try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
			MapMessage message = context.createMapMessage();

			message.setStringProperty("DocType", docType);
			message.setString("Recipient", recipient);
			message.setString("Subject", subject);
			message.setString("Body", body);

			context.createProducer().send(queue, message);
		} catch (JMSException jmsEx) {
			logger.error(jmsEx);
		}
	}
}
