/**
 * Represents a steer.
 * @author jjbaker4
 * @version 1.0
 */

package main.java;

public class Cattle extends Animal {
    public static final double LAND_NEEDED = 0.1;
    public static final int COST = 175;
    public static final int PROFIT = 525;
    private static final HarvestStrategy HS = TerminalHarvest.getTerminalHarvest();
    
    /**
     * Constructor sets default properties for a Cattle.
     */
    public Cattle() {
        super(COST, PROFIT, LAND_NEEDED, HS);
    }
}
