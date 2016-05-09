/**
 * 
 */
package de.warehouse.shared;

import java.util.HashMap;

/**
 * @author David
 *
 */
public class Article {

	private String code;
	private String name;
	
	private StorageLocation storageLocation;

	private Integer quantityOnStock;
	private Integer quantityOnCommitment;
	

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
