package main.java;

public class Chicken extends Animal{
    //chance to procreate on the correct cycle
    private final static double CHANCE_TO_PROCREATE = .2;
    private final static double LAND_NEEDED = 0.1;
    private final static int COST = 10;
    private final static int PROFIT = 14;
    private final static double DISEASE_DEATH_RATE = 0.25;
    
    /**
     * Constructor sets default properties for a Chicken
     */
    public Chicken() {
        super(CHANCE_TO_PROCREATE, COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE);
    }
}
