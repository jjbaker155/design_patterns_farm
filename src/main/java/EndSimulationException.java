package main.java;

public class EndSimulationException extends Exception {

    public EndSimulationException() {
        super("Simulation has ended.");
    }

    public EndSimulationException(String message) {
        super(message);
       
    }

}
