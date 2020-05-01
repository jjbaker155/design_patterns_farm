package main.java;

public class LandGoalReachedException extends Exception{

    public LandGoalReachedException() {
        super("The farm has reached its acreage goal");
    }
    
    public LandGoalReachedException(String message) {
        super(message);
    }
    
    

}
