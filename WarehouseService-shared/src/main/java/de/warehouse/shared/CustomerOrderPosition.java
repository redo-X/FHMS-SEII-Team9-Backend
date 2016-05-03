/**
 * 
 */
package de.warehouse.shared;

import java.time.LocalDateTime;

/**
 * @author David
 *
 */
public class CustomerOrderPosition {
	private CustomerOrder order;
	
	private Article article;
	
	private Integer orderedQuantity;
	private Integer pickedQuantity;
	
	private LocalDateTime dateOfCommission;
	
	
	public Double getProgress() {
		if (orderedQuantity == 0) {
			return 0.0;
		}
		
		return Double.valueOf(pickedQuantity) / Double.valueOf(orderedQuantity);
	}
}
