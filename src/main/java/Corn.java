package main.java;

public class Corn extends Crop{
    
    //land needed for crop
    private final static double LAND_NEEDED = 0.4;
    //cost of one unit
    private final static int COST = 300;
    //profit per harvest
    private final static int PROFIT = 650;
    //base disease death rate
    private final static double DISEASE_DEATH_RATE = .45;
    
    
    public Corn() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE);
    }

}
