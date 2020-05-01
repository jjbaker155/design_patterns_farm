package main.java;

public class FarmHasWonException extends EndSimulationException {
    
    /**
     * Default message
     */
    public FarmHasWonException() {
        super("Farm has succeeded.");
    }

    /**
     * Custom message
     * @param message
     */
    public FarmHasWonException(String message) {
        super(message);
    }

}
