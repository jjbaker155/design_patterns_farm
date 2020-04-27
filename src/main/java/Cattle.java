package main.java;

public class Cattle extends Animal {
    
    private static final double CHANCE_TO_PROCREATE = 0.1;
    private static final double LAND_NEEDED = 0.3;
    private static final int COST = 250;
    private static final int PROFIT = 500;
    private static final double DISEASE_DEATH_RATE = 0.4;
    
    /**
     * Constructor sets default properties for a Cattle
     */
    public Cattle() {
        super(CHANCE_TO_PROCREATE, COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE);
    }
}
