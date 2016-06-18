package de.warehouse.jobs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;

import de.warehouse.dao.interfaces.ICommissionMessagesDAO;
import de.warehouse.messaging.OutputRequesterBean;
import de.warehouse.persistence.Article;
import de.warehouse.persistence.CustomerOrderPositionMessage;
import de.warehouse.persistence.Employee;
import de.warehouse.shared.DocTypes;

/**
 * @author David
 */
@Singleton
@Startup
public class MessageEmployeesGeneratorBean {

	private static final Logger logger = Logger.getLogger(MessageEmployeesGeneratorBean.class);

	@EJB
	private ICommissionMessagesDAO commissionMessageDAO;

	@EJB
	private OutputRequesterBean outputRequesterBean;

	@Lock(LockType.WRITE)
	@Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
	public void generateNotificationsForMessages() {

		logger.info("Check for pending messages");

		List<CustomerOrderPositionMessage> messages = this.commissionMessageDAO.getPendingMessages();

		if (messages.isEmpty()) {
			logger.info("No pending messages.");
		} else {
			logger.info(String.format("%d pending messages.", messages.size()));
			
			for (CustomerOrderPositionMessage message : messages) {
				Employee creator = message.getCreatedByEmployee();
				Employee responsibleEmployee = message.getResponsibleEmployee();

				Article a = message.getPosition().getArticle();

				int difference = message.getQuantityDifference();

				String note = message.getNote();

				String recipient = responsibleEmployee.getMailAddress();
				String subject = "PROBLEM: Soll-/Istdifferenz";
				String body = String.format(
						"Ersteller: %s\nArtikel: %s\nNotiz: %s\nVorhandene Menge: %d\tGeforderte Menge: %d\nLagerort: %s",
						creator.getFullName(), a.getDisplayName(), note, difference,
						message.getPosition().getRemainingQuantity(), a.getStorageLocation().getCode());

				logger.info(String.format("Send body '%s' to '%s'", body, recipient));

				this.commissionMessageDAO.updateMailFlag(message.getCustomerOrderPositionMessageId());

				this.outputRequesterBean.sendInfo(DocTypes.CommissionInfo.name(), recipient, subject, body);
			}
		}
	}
}