/**
 * Represents one field of a corn.
 * @jjbaker4
 * @version 1.0
 */

package main.java;

public class Corn extends Crop {
    
    //land needed for crop
    public static final double LAND_NEEDED = 0.2;
    //cost of one unit
    public static final int COST = 150;
    //profit per harvest
    public static final int PROFIT = 475;
    //how to harvest
    private static final HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    
    public Corn() {
        super(COST, PROFIT, LAND_NEEDED, HS);
    }

}
