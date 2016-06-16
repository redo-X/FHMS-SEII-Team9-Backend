package de.warehouse.shared;

/**
 * @author David
 */
public enum DocTypes {
	CommissionInfo,
	CommissionPositionWarning;
	
    public String toString() {
        return this.name();
    }
}
