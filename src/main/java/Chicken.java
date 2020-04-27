package main.java;

public class Chicken extends Animal{
    //chance to procreate on the correct cycle
    private final double CHANCE_TO_PROCREATE = .2;
    private final double LAND_NEEDED = 0.1;
    private final int COST = 10;
    private final int PROFIT = 14;
    
    /**
     * Constructor sets default properties for a Chicken
     */
    public Chicken() {
        setLandNeeded(LAND_NEEDED);
        setCost(COST);
        setProfit(PROFIT);
        setChanceToProcreate(CHANCE_TO_PROCREATE);
    }
}
