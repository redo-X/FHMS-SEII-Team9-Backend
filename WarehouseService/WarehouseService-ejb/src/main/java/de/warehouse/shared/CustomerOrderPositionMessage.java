/**
 * 
 */
package de.warehouse.shared;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @author David
 *
 */
@Entity
public class CustomerOrderPositionMessage implements Serializable {
	@Id
	@GeneratedValue
	private int customerOrderPositionMessageId;
	
	@ManyToOne
	private CustomerOrderPosition position;
	@Column(nullable=false)
	private String note;
	@Column(nullable=false)
	private Integer quantityDifference;
	
	@ManyToOne
	private Employee createdByEmployee;
	
	@ManyToOne
	private Employee responsibleEmployee;
}
