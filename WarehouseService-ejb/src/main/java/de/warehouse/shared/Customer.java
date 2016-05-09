package de.warehouse.shared;

import java.util.HashMap;

public class Customer {
	private Integer code;
	
	private String name1;
	private String name2;
	private String mailAddress;
	
	private Address address;
	
	private Integer deliveryToleranceInDays;
	
	private HashMap<Integer, CustomerOrder> orders;

	/**
	 * @return the deliveryToleranceInDays
	 */
	public Integer getDeliveryToleranceInDays() {
		return deliveryToleranceInDays;
	}
	
	
}
