/**
 * 
 */
package de.warehouse.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.warehouse.shared.exceptions.CustomerOrderMustBeAllocateToPicker;
import de.warehouse.shared.exceptions.NegativeQuantityException;
import de.warehouse.shared.exceptions.PickedQuantityTooHighException;

/**
 * @author David
 *
 */
@Entity
public class CustomerOrderPosition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2225969816208146650L;

	@Id
	@GeneratedValue
	private int customerOrderPositionId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private CustomerOrder order;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Article article;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "position", fetch=FetchType.LAZY)
	private Set<CustomerOrderPositionMessage> messages;

	@Column(nullable = false)
	private Integer orderedQuantity;

	@Column(nullable = false)
	private Integer pickedQuantity = 0;

	private LocalDateTime dateOfCommission;

	public CustomerOrderPosition() {

	}

	public CustomerOrderPosition(int customerOrderPositionId) {
		this.customerOrderPositionId = customerOrderPositionId;
	}

	public Double getProgress() {
		if (orderedQuantity == 0) {
			return 0.0;
		}

		return Double.valueOf(pickedQuantity) / Double.valueOf(orderedQuantity);
	}

	public Integer getRemainingQuantity() {
		return this.orderedQuantity - this.pickedQuantity;
	}

	@Override
	public String toString() {
		return "CustomerOrderPosition [customerOrderPositionId=" + customerOrderPositionId + ", article=" + article
				+ ", orderedQuantity=" + orderedQuantity + ", pickedQuantity=" + pickedQuantity + ", dateOfCommission="
				+ dateOfCommission + "]";
	}

	/**
	 * @return the customerOrderPositionId
	 */
	public int getCustomerOrderPositionId() {
		return customerOrderPositionId;
	}

	/**
	 * @return the order
	 */
	public CustomerOrder getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(CustomerOrder order) {
		this.order = order;
		if (this.order.getPositions().containsKey(this.customerOrderPositionId) == false) {
			this.order.getPositions().put(this.customerOrderPositionId, this);
		}
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article
	 *            the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * @return the orderedQuantity
	 */
	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}

	/**
	 * @param orderedQuantity
	 *            the orderedQuantity to set
	 */
	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	/**
	 * @return the pickedQuantity
	 */
	public Integer getPickedQuantity() {
		return pickedQuantity;
	}

	/**
	 * @param pickedQuantity
	 *            the pickedQuantity to set
	 * @throws NegativeQuantityException
	 *             Picked quantity must be positive
	 * @throws CustomerOrderMustBeAllocateToPicker
	 *             Customer order must be allocated first
	 * @throws PickedQuantityTooHighException
	 *             Picked quantity is higher than the remaining quantity
	 */
	public void setPickedQuantity(Integer pickedQuantity)
			throws NegativeQuantityException, CustomerOrderMustBeAllocateToPicker, PickedQuantityTooHighException {
		if (this.getOrder().getPicker() == null) {
			throw new CustomerOrderMustBeAllocateToPicker("Customer order must be allocated to a picker.");
		}
		if (pickedQuantity < 0) {
			throw new NegativeQuantityException("Picked quantity must not be negative.");
		}
		if (pickedQuantity > this.orderedQuantity) {
			throw new PickedQuantityTooHighException("Picked quantity must be less than or equals ordered quantity.");
		}
		if (pickedQuantity > this.getRemainingQuantity()) {
			throw new PickedQuantityTooHighException("Picked quantity must be less or equals the remaining quantity.");
		}
		this.pickedQuantity += pickedQuantity;
	}

	/**
	 * @return the dateOfCommission
	 */
	public LocalDateTime getDateOfCommission() {
		return dateOfCommission;
	}

	/**
	 * @param dateOfCommission
	 *            the dateOfCommission to set
	 */
	public void setDateOfCommission(LocalDateTime dateOfCommission) {
		this.dateOfCommission = dateOfCommission;
	}

}
