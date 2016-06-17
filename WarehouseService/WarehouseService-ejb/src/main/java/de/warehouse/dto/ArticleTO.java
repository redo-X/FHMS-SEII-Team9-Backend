package de.warehouse.dto;

/**
 * @author David, Florian, Thomas, Marco
 */
public class ArticleTO  extends ResponseBase {

	private static final long serialVersionUID = -4195469030405691860L;
	
	private String code;
	private String name;
	
	private Integer quantityOnStock;
	
	private String storageLocation;
	
	
	public ArticleTO() {
		
	}	
	/**
	 * @param code
	 * @param name
	 * @param quantityOnStock
	 * @param storageLocation
	 */
	public ArticleTO(String code, String name, Integer quantityOnStock, String storageLocation) {
		this.code = code;
		this.name = name;
		this.quantityOnStock = quantityOnStock;
		this.storageLocation = storageLocation;
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
	 * @return the quantityOnStock
	 */
	public Integer getQuantityOnStock() {
		return quantityOnStock;
	}

	/**
	 * @param quantityOnStock the quantityOnStock to set
	 */
	public void setQuantityOnStock(Integer quantityOnStock) {
		this.quantityOnStock = quantityOnStock;
	}

	/**
	 * @return the storageLocation
	 */
	public String getStorageLocation() {
		return storageLocation;
	}

	/**
	 * @param storageLocation the storageLocation to set
	 */
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	
	
	
}
