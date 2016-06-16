package de.warehouse.dto.responses.commission;

import javax.xml.bind.annotation.XmlType;

import de.warehouse.dto.CommissionTO;
import de.warehouse.dto.ResponseBase;

@XmlType(name="GetCommissionsResponse")
public class GetCommissionsResponse extends ResponseBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -197858659152113603L;

	private CommissionTO[] commissions;

	/**
	 * @return the commissions
	 */
	public CommissionTO[] getCommissions() {
		return commissions;
	}

	/**
	 * @param commissions the commissions to set
	 */
	public void setCommissions(CommissionTO[] commissions) {
		this.commissions = commissions;
	}
}
