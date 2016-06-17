package de.warehouse.dto.responses.commission;

import javax.xml.bind.annotation.XmlType;

import de.warehouse.dto.CommissionPositionTO;
import de.warehouse.dto.ResponseBase;

/**
 * @author David
 */
@XmlType(name="GetCommissionPositionsResponse")
public class GetCommissionPositionsResponse extends ResponseBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7694414160715754013L;
	
	private CommissionPositionTO[] commissionPositions;

	/**
	 * @return the commissionPositions
	 */
	public CommissionPositionTO[] getCommissionPositions() {
		return commissionPositions;
	}

	/**
	 * @param commissionPositions the commissionPositions to set
	 */
	public void setCommissionPositions(CommissionPositionTO[] commissionPositions) {
		this.commissionPositions = commissionPositions;
	}
}