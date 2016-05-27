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
	public Integer getRemainingQuantity() {
		return this.orderedQuantity - this.pickedQuantity;
	}
	/**
	 * @return the customerOrderId
	 */
	public int getCustomerOrderId() {
		return customerOrderId;
	}
	/**
	 * @param customerOrderId the customerOrderId to set
	 */
	public void setCustomerOrderId(int customerOrderId) {
		this.customerOrderId = customerOrderId;
	}
	/**
	 * @return the order
	 */
	public CustomerOrder getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(CustomerOrder order) {
		this.order = order;
	}
	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}
	/**
	 * @param article the article to set
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
	 * @param orderedQuantity the orderedQuantity to set
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
	 * @param pickedQuantity the pickedQuantity to set
	 */
	public void setPickedQuantity(Integer pickedQuantity) {
		this.pickedQuantity = pickedQuantity;
	}
	/**
	 * @return the dateOfCommission
	 */
	public LocalDateTime getDateOfCommission() {
		return dateOfCommission;
	}
	/**
	 * @param dateOfCommission the dateOfCommission to set
	 */
	public void setDateOfCommission(LocalDateTime dateOfCommission) {
		this.dateOfCommission = dateOfCommission;
	}
	
	
	
}
