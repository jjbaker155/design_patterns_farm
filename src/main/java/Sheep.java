package main.java;

public class Sheep extends Animal {
    private final static double LAND_NEEDED = 0.2;
    private final static int COST = 150;
    private final static int PROFIT = 50;
    private final static double DISEASE_DEATH_RATE = 0.15;
    private final static HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    /**
     * Constructor sets default properties for a Sheep
     */
    public Sheep() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }

}
