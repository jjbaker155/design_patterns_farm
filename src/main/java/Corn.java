package main.java;

public class Corn extends Crop{
    
    //land needed for crop
    private final static double LAND_NEEDED = 0.2;
    //cost of one unit
    private final static int COST = 150;
    //profit per harvest
    private final static int PROFIT = 475;
    //base disease death rate
    private final static double DISEASE_DEATH_RATE = .45;
    private final static HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    
    public Corn() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }

}
