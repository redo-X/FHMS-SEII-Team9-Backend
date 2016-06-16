package de.warehouse.dto.requests.commission;

import de.warehouse.dto.DataTransferObjectBase;

public class GetPendingCustomerOrdersByPickerIdRequest extends DataTransferObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5517554570107163841L;
	
	private int pickerId;

	/**
	 * @return the pickerId
	 */
	public int getPickerId() {
		return pickerId;
	}

	/**
	 * @param pickerId the pickerId to set
	 */
	public void setPickerId(int pickerId) {
		this.pickerId = pickerId;
	}
	
	

}
