/**
 * Represents a pig.
 * @author jjbaker4
 * @version 1.0
 */

package main.java;

public class Hog extends Animal {
    public static final double LAND_NEEDED = 0.05;
    public static final int COST = 100;
    public static final int PROFIT = 225;
    private static final HarvestStrategy HS = TerminalHarvest.getTerminalHarvest();
    
    /**
     * Constructor sets default properties for a Chicken.
     */
    public Hog() {
        super(COST, PROFIT, LAND_NEEDED, HS);
    }
}
