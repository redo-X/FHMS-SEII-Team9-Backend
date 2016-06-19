package de.warehouse.persistence;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author David, Florian
 */
@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = -372054446957844789L;

	@Id
	private Integer code;
	@Column(nullable = false)
	private String name1;
	private String name2;
	@Column(nullable = false)
	private String mailAddress;

	@Column(nullable = false)
	private Integer deliveryToleranceInDays;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.LAZY)
	private Set<CustomerOrder> orders;

	public Customer() {

	}

	public Customer(int code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @return the deliveryToleranceInDays
	 */
	public Integer getDeliveryToleranceInDays() {
		return deliveryToleranceInDays;
	}

	/**
	 * @return the name1
	 */
	public String getName1() {
		return name1;
	}

	/**
	 * @param name1
	 *            the name1 to set
	 */
	public void setName1(String name1) {
		this.name1 = name1;
	}

	/**
	 * @return the name2
	 */
	public String getName2() {
		return name2;
	}

	/**
	 * @param name2
	 *            the name2 to set
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress
	 *            the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the orders
	 */
	public Set<CustomerOrder> getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 *            the orders to set
	 */
	public void setOrders(Set<CustomerOrder> orders) {
		this.orders = orders;
	}

	/**
	 * @param deliveryToleranceInDays
	 *            the deliveryToleranceInDays to set
	 */
	public void setDeliveryToleranceInDays(Integer deliveryToleranceInDays) {
		this.deliveryToleranceInDays = deliveryToleranceInDays;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [code=" + code + ", name1=" + name1 + ", name2=" + name2 + ", mailAddress=" + mailAddress
				+ ", deliveryToleranceInDays=" + deliveryToleranceInDays + "]";
	}
}
