/**
 * 
 */
package de.warehouse.shared;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.*;

/**
 * @author David
 *
 */
@Entity
public class Article implements Serializable{
	@Id
	private String code;
	@Column(nullable=false)
	private String name;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private StorageLocation storageLocation;
	
	@Column(nullable=false)
	private Integer quantityOnStock;
	@Column(nullable=false)
	private Integer quantityOnCommitment;
	
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="article")
	private Map<Integer, CustomerOrderPosition> customerOrderPositions;

	public Article() {
		
	}	
	/**
	 * @param code
	 * @param name
	 */
	public Article(String code, String name) {
		this.code = code;
		this.name = name;
	}

	
	public Integer getFreeQuantity() {
		return this.quantityOnStock - this.quantityOnCommitment;
	}
	

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the storageLocation
	 */
	public StorageLocation getStorageLocation() {
		return storageLocation;
	}
	/**
	 * Overwrites the existing storage location!
	 * @param storageLocation the storageLocation to set
	 */
	public void setStorageLocation(StorageLocation storageLocation) {
		this.storageLocation = storageLocation;
	}
	/**
	 * @return the quantityOnStock
	 */
	public Integer getQuantityOnStock() {
		return quantityOnStock;
	}
	/**
	 * @param quantityOnStock the quantityOnStock to set
	 * @throws IllegalArgumentException If the quantity is less than 0
	 */
	public void setQuantityOnStock(Integer quantityOnStock) {
		if (quantityOnStock < 0) {
			throw new IllegalArgumentException("The quantity must be greater or equals 0.");
		}
		this.quantityOnStock = quantityOnStock;
	}
	/**
	 * @return the quantityOnCommitment
	 */
	public Integer getQuantityOnCommitment() {
		return quantityOnCommitment;
	}
	/**
	 * @param quantityOnCommitment the quantityOnCommitment to set
	 * @throws IllegalArgumentException If the commitment quantity is greater than the quantity on stock
	 */
	public void setQuantityOnCommitment(Integer quantityOnCommitment) throws IllegalArgumentException {
		if (this.quantityOnCommitment > this.quantityOnStock) {
			throw new IllegalArgumentException("The quantity to commit must be less or equals the quantity on stock.");
		}
		
		this.quantityOnCommitment = quantityOnCommitment;
	}
}
