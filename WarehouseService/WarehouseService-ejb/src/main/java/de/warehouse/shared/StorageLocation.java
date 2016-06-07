/**
 * 
 */
package de.warehouse.shared;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author David
 *
 */
@Entity
public class StorageLocation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -235818876179343635L;

	@Id
	private String code;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="storageLocation")
	private Set<Article> stockArticles;

	public StorageLocation() {
		
	}
	/**
	 * @param code
	 */
	public StorageLocation(String code) {
		this.code = code;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StorageLocation [code=" + code + "]";
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
