package main.java;

public class Sheep extends NonMeatAnimal {
    private final double CHANCE_TO_PROCREATE = 0.3;
    private final double LAND_NEEDED = 0.2;
    private final int COST = 150;
    private final int PROFIT = 50;
    
    /**
     * Constructor sets default properties for a Sheep
     */
    public Sheep() {
        setLandNeeded(LAND_NEEDED);
        setCost(COST);
        setProfit(PROFIT);
        setChanceToProcreate(CHANCE_TO_PROCREATE);
    }

}
