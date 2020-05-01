package main.java;

public class Corn extends Crop{
    
    //land needed for crop
    public final static double LAND_NEEDED = 0.2;
    //cost of one unit
    public final static int COST = 150;
    //profit per harvest
    public final static int PROFIT = 475;
    //how to harvest
    private final static HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    
    public Corn() {
        super(COST, PROFIT, LAND_NEEDED, HS);
    }

}
