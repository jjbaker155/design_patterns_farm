package main.java;

public class DairyCow extends Animal {
    public final static double LAND_NEEDED = 0.3;
    public final static int COST = 250;
    public final static int PROFIT = 100;
    public final static double DISEASE_DEATH_RATE = .35;
    private final static HarvestStrategy HS = RegularHarvest.getRegularHarvest();
    
    /**
     * Constructor sets default properties for a DairyCow
     */
    public DairyCow() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE, HS);
    }
    
}
