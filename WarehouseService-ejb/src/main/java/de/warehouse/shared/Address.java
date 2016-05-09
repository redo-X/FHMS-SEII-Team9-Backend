/**
 * 
 */
package de.warehouse.shared;

/**
 * @author David
 *
 */
public class Address {
	private String zipCode;
	private String city;
	
	private String street;
	private String houseNumber;
	
	/**
	 * Default Constructor
	 */
	public Address() {
	
	}
	
	/**
	 * @param zipCode
	 * @param city
	 * @param street
	 * @param houseNumber
	 */
	public Address(String zipCode, String city, String street, String houseNumber) {
		this.zipCode = zipCode;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}
	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	
}
