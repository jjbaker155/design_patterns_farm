package exceptions;

public class AssetAlreadyDeadException extends Exception{

    /**
     * Throw with default message 
     */
    public AssetAlreadyDeadException() {
        super("Asset is dead. State cannot change.");
    }
    
    /**
     * Throw with custom message
     * @param message the custom message as a String
     */
    public AssetAlreadyDeadException(String message) {
        super(message);
    }

}
