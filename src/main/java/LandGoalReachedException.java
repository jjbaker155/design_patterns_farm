package main.java;

/**
 * Farm won by reaching land goal. Throw to notify UI.
 * @author jjbaker4
 *
 */
public class LandGoalReachedException extends Exception {

    public LandGoalReachedException() {
        super("The farm has reached its acreage goal");
    }
    
    public LandGoalReachedException(String message) {
        super(message);
    }
    
    

}
