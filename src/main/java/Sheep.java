package main.java;

/**
 * Represents a Sheep.
 * @author jjbaker4
 * @version 1.0
 *
 */
public class Sheep extends Animal {
    public static final double LAND_NEEDED = 0.05;
    public static final int COST = 75;
    public static final int PROFIT = 175;
    public static final HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    /**
     * Constructor sets default properties for a Sheep.
     */
    public Sheep() {
        super(COST, PROFIT, LAND_NEEDED, HS);
    }

}
