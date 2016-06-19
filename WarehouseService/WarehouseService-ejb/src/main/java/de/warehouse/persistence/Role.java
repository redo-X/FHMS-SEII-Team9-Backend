/**
 * 
 */
package de.warehouse.persistence;

/**
 * @author David
 */
public enum Role {
	Kommissionierer(0),
	Lagerist(1),
	Administrator(2);
	
	private final int code;
    private Role(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public String toString() {
        return String.format("%s: %s", this.name(), String.valueOf(this.code));
    }
}
