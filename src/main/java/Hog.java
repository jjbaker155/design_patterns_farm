package main.java;

public class Hog extends Animal{
    private final static double LAND_NEEDED = 0.05;
    private final static int COST = 150;
    private final static int PROFIT = 225;
    private final static double DISEASE_DEATH_RATE = 0.25;
    private final static HarvestStrategy HS = TerminalHarvest.getTerminalHarvest();
    
    /**
     * Constructor sets default properties for a Chicken
     */
    public Hog() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }
}
