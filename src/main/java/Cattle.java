package main.java;

public class Cattle extends Animal {
    public static final double LAND_NEEDED = 0.1;
    public static final int COST = 175;
    public static final int PROFIT = 525;
    public static final double DISEASE_DEATH_RATE = 0.4;
    private final static HarvestStrategy HS = TerminalHarvest.getTerminalHarvest();
    
    /**
     * Constructor sets default properties for a Cattle
     */
    public Cattle() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }
}
