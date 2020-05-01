package main.java;

public class FarmIsBankruptException extends EndSimulationException{
    
    
    public FarmIsBankruptException() {
        super("The farm has gone bankrupt.");
    }
    
    
    public FarmIsBankruptException(String message) {
        super(message);
    }

}
