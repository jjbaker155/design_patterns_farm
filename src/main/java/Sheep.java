package main.java;

public class Sheep extends Animal {
    public final static double LAND_NEEDED = 0.05;
    public final static int COST = 75;
    public final static int PROFIT = 175;
    public final static double DISEASE_DEATH_RATE = 0.15;
    public final static HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    /**
     * Constructor sets default properties for a Sheep
     */
    public Sheep() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }

}
