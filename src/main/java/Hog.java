package main.java;

public class Hog extends Animal{
    public final static double LAND_NEEDED = 0.05;
    public final static int COST = 100;
    public final static int PROFIT = 225;
    public final static double DISEASE_DEATH_RATE = 0.25;
    private final static HarvestStrategy HS = TerminalHarvest.getTerminalHarvest();
    
    /**
     * Constructor sets default properties for a Chicken
     */
    public Hog() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }
}
