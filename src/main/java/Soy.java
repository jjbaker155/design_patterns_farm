package main.java;

public class Soy extends Crop {
    private final static double LAND_NEEDED = 0.2;
    private final static int COST = 125;
    private final static int PROFIT = 400;
    private final static double DISEASE_DEATH_RATE = .25;
    private final static HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    public Soy() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }

}
