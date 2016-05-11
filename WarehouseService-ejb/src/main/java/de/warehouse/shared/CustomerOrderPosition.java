/**
 * 
 */
package de.warehouse.shared;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * @author David
 *
 */
@Entity
public class CustomerOrderPosition implements Serializable {
	
	@Id
	@GeneratedValue
	private int customerOrderId;
	
	@ManyToOne
	private CustomerOrder order;
	@ManyToOne
	private Article article;
	@Column(nullable=false)
	private Integer orderedQuantity;
	@Column(nullable=false)
	private Integer pickedQuantity;
	
	private LocalDateTime dateOfCommission;
	
	
	public Double getProgress() {
		if (orderedQuantity == 0) {
			return 0.0;
		}
		
		return Double.valueOf(pickedQuantity) / Double.valueOf(orderedQuantity);
	}
}
