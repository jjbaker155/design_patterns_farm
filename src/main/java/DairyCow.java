/**
 * Represents a dairy cow.
 * @jjbaker4
 * @version 1.0
 */

package main.java;

public class DairyCow extends Animal {
    public static final double LAND_NEEDED = 0.3;
    public static final int COST = 250;
    public static final int PROFIT = 100;
    private static final HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    /**
     * Constructor sets default properties for a DairyCow.
     */
    public DairyCow() {
        super(COST, PROFIT, LAND_NEEDED, HS);
    }
    
}
