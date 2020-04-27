package main.java;

public class DairyCow extends Animal{
    private final double CHANCE_TO_PROCREATE = 0.1;
    private final double LAND_NEEDED = 0.3;
    private final int COST = 250;
    private final int PROFIT = 100;
    
    /**
     * Constructor sets default properties for a DairyCow
     */
    public DairyCow() {
        setLandNeeded(LAND_NEEDED);
        setCost(COST);
        setProfit(PROFIT);
        setChanceToProcreate(CHANCE_TO_PROCREATE);
    }
    
}
