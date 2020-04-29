package main.java;

public class Cattle extends Animal {
    private static final double LAND_NEEDED = 0.1;
    private static final int COST = 250;
    private static final int PROFIT = 575;
    private static final double DISEASE_DEATH_RATE = 0.4;
    private final static HarvestStrategy HS = TerminalHarvest.getTerminalHarvest();
    
    /**
     * Constructor sets default properties for a Cattle
     */
    public Cattle() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }
}
