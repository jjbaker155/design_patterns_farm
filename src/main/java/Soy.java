package main.java;

/**
 * Represents a field of soy beans.
 * @author jjbaker4
 * @version 1.0
 */
public class Soy extends Crop {
    public static final double LAND_NEEDED = 0.2;
    public static final int COST = 125;
    public static final int PROFIT = 400;
    private static final HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    public Soy() {
        super(COST, PROFIT, LAND_NEEDED, HS);
    }

}
