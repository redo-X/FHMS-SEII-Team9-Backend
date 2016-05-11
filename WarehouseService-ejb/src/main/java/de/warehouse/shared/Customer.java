package de.warehouse.shared;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.*;

@Entity
public class Customer implements Serializable{
	@Id
	private Integer code;
	@Column(nullable=false)
	private String name1;
	private String name2;
	@Column(nullable=false)
	private String mailAddress;
	
	@OneToOne(mappedBy="customer", optional=false)
	private Address address;

	@Column(nullable=false)
	private Integer deliveryToleranceInDays;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="customer", fetch=FetchType.LAZY)
	@MapKey
	private Map<Integer, CustomerOrder> orders;

	/**
	 * @return the deliveryToleranceInDays
	 */
	public Integer getDeliveryToleranceInDays() {
		return deliveryToleranceInDays;
	}
	
	
}
