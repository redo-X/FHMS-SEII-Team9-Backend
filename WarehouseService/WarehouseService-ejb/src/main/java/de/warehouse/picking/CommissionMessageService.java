package de.warehouse.picking;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import de.warehouse.dao.interfaces.ICommissionMessagesDAO;
import de.warehouse.dao.interfaces.IEmployeeDAO;
import de.warehouse.messaging.OutputRequesterBean;
import de.warehouse.persistence.Employee;
import de.warehouse.persistence.Role;
import de.warehouse.shared.DocTypes;
import de.warehouse.shared.interfaces.ICommissionMessages;

@Stateless
@Local(ICommissionMessages.class)
public class CommissionMessageService implements ICommissionMessages {
	
	@EJB	
	private ICommissionMessagesDAO commissionMessageDAO;
	
	@EJB
	private IEmployeeDAO employeeDAO;

	@EJB
	private OutputRequesterBean requesterBean;

	/** 
	 * @see
	 * de.warehouse.shared.interfaces.ICommissionMessages#commitMessage(int, int, int, int, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void commitMessage(int sessionId, int customerOrderPositionId, int differenceQuantity, String note) {
		for(Employee e : this.employeeDAO.getByRole(Role.Lagerist)) {
			this.commissionMessageDAO.commitMessage(sessionId, customerOrderPositionId, e.getCode(), differenceQuantity, note);
		}
	}
}