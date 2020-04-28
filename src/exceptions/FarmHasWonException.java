package exceptions;

public class FarmHasWonException extends Exception {
    
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
