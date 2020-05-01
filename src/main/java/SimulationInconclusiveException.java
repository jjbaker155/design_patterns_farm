package main.java;

public class SimulationInconclusiveException extends EndSimulationException {

    public SimulationInconclusiveException() {
        super("Simulation has ended inconclusively");
    }
    
    public SimulationInconclusiveException(String message) {
        super(message);
    }

}
