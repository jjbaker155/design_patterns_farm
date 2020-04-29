package main.java;

public class Chicken extends Animal{
    private final static double LAND_NEEDED = 0.1;
    private final static int COST = 10;
    private final static int PROFIT = 14;
    private final static double DISEASE_DEATH_RATE = 0.25;
    private final static HarvestStrategy HS = TerminalHarvest.getTerminalHarvest();
    
    /**
     * Constructor sets default properties for a Chicken
     */
    public Chicken() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }
}
