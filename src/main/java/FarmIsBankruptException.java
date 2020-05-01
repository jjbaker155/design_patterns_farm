/**
 * Farm has gone bankrupt. Notify the UI by throwing this exception
 * @jjbaker4
 * @version 1.0
 */

package main.java;

public class FarmIsBankruptException extends EndSimulationException {
    
    
    public FarmIsBankruptException() {
        super("The farm has gone bankrupt.");
    }
    
    
    public FarmIsBankruptException(String message) {
        super(message);
    }

}
