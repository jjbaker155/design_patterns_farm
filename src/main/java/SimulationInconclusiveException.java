package main.java;

/**
 * Simulation ends after a number of turns. Throw this exception to notify UI. 
 * @author jjbaker4
 * @version 1.0
 *
 */
public class SimulationInconclusiveException extends EndSimulationException {

    public SimulationInconclusiveException() {
        super("Simulation has ended inconclusively");
    }
    
    public SimulationInconclusiveException(String message) {
        super(message);
    }

}
