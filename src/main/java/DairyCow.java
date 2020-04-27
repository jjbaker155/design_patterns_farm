package main.java;

public class DairyCow extends Animal{
    private final static double CHANCE_TO_PROCREATE = 0.1;
    private final static double LAND_NEEDED = 0.3;
    private final static int COST = 250;
    private final static int PROFIT = 100;
    private final static double DISEASE_DEATH_RATE = .35;
    
    /**
     * Constructor sets default properties for a DairyCow
     */
    public DairyCow() {
        super(CHANCE_TO_PROCREATE, COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE);
    }
    
}
