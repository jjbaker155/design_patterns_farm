package main.java;

public class Corn extends Crop{
    
    //land needed for crop
    private final static double LAND_NEEDED = 0.4;
    private final static int COST = 300;
    private final static int PROFIT = 650;
    private final static double DISEASE_DEATH_RATE = .35;
    
    
    public Corn() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE);
    }

}
