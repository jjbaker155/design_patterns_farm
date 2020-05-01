/**
 * Throw this to signal to UI that the simulation should end. See child classes.
 * @author jjbaker4
 * @version 1.0
 */

package main.java;

public class EndSimulationException extends Exception {

    public EndSimulationException() {
        super("Simulation has ended.");
    }

    public EndSimulationException(String message) {
        super(message);
       
    }

}
