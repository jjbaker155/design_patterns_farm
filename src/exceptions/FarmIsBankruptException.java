package exceptions;

public class FarmIsBankruptException extends Exception{
    
    
    public FarmIsBankruptException() {
        super("The farm has gone bankrupt.");
    }
    
    
    public FarmIsBankruptException(String message) {
        super(message);
    }

}
