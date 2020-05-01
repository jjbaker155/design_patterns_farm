package main.java;

public class Hog extends Animal{
    public final static double LAND_NEEDED = 0.05;
    public final static int COST = 100;
    public final static int PROFIT = 225;
    private final static HarvestStrategy HS = TerminalHarvest.getTerminalHarvest();
    
    /**
     * Constructor sets default properties for a Chicken
     */
    public Hog() {
        super(COST, PROFIT, LAND_NEEDED, HS);
    }
}
