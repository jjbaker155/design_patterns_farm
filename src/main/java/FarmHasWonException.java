/**
 * Farm has won! Throw this exception to notify the UI. Put a description.
 * @jjbaker4
 * @version 1.0
 */

package main.java;

public class FarmHasWonException extends EndSimulationException {
    
    /**
     * Default message.
     */
    public FarmHasWonException() {
        super("Farm has succeeded.");
    }

    /**
     * Custom message.
     * @param message String
     */
    public FarmHasWonException(String message) {
        super(message);
    }

}
