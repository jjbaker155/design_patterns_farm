package main.java;

public class Soy extends Crop {
    public final static double LAND_NEEDED = 0.2;
    public final static int COST = 125;
    public final static int PROFIT = 400;
    private final static HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    public Soy() {
        super(COST, PROFIT, LAND_NEEDED, HS);
    }

}
