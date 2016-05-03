/**
 * 
 */
package de.warehouse.shared;

/**
 * @author David
 *
 */
public class CustomerOrderPositionMessage {
	private CustomerOrderPosition position;
	
	private String note;
	
	private Integer quantityDifference;
	
	private Employee createdByEmployee;
	private Employee responsibleEmployee;
}
