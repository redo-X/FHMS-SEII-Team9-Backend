package de.warehouse.dto.requests.commission;

import de.warehouse.dto.DataTransferObjectBase;

public class UpdatePickedQuantityRequest extends DataTransferObjectBase {

	private static final long serialVersionUID = 296896664632877622L;

	private int customerOrderPositionId;
	private int pickedQuantity;
	
	
	/**
	 * @return the customerOrderPositionId
	 */
	public int getCustomerOrderPositionId() {
		return customerOrderPositionId;
	}
	/**
	 * @param customerOrderPositionId the customerOrderPositionId to set
	 */
	public void setCustomerOrderPositionId(int customerOrderPositionId) {
		this.customerOrderPositionId = customerOrderPositionId;
	}
	/**
	 * @return the pickedQuantity
	 */
	public int getPickedQuantity() {
		return pickedQuantity;
	}
	/**
	 * @param pickedQuantity the pickedQuantity to set
	 */
	public void setPickedQuantity(int pickedQuantity) {
		this.pickedQuantity = pickedQuantity;
	}
}