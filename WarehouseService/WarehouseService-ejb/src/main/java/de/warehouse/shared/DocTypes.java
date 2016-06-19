package de.warehouse.shared;

/**
 * Strongly typed names for the message driven bean.
 * @author David
 */
public enum DocTypes {
	CommissionInfo,
	CommissionPositionWarning;
	
    public String toString() {
        return this.name();
    }
}
