/**
 * 
 */
package de.warehouse.shared;

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
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private CustomerOrder order;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private Article article;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="position")
	private Set<CustomerOrderPositionMessage> messages;
	
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerOrderPosition [customerOrderPositionId=" + customerOrderPositionId + ", article=" + article
				+ ", orderedQuantity=" + orderedQuantity + ", pickedQuantity=" + pickedQuantity + ", dateOfCommission="
				+ dateOfCommission + "]";
	}
	/**
	 * @return the customerOrderId
	 */
	public int getCustomerOrderPositionId() {
		return customerOrderPositionId;
	}
	/**
	 * @param customerOrderId the customerOrderId to set
	 */
	public void setCustomerOrderPositionId(int customerOrderId) {
		this.customerOrderPositionId = customerOrderId;
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
