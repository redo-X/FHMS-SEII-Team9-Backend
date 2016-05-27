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
public class StorageLocation implements Serializable{
	@Id
	private String code;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="storageLocation")
	@MapKey
	private Map<Integer, Article> stockArticles;

	public StorageLocation() {
		
	}
	/**
	 * @param code
	 */
	public StorageLocation(String code) {
		this.code = code;
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
}
