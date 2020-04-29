package main.java;

/**
 * Any farm animal
 * @author jjbaker
 *
 */
public abstract class Animal extends Asset{
    

    //how much revenue produced in a day
    private int producesProfit;
    //cycles to die
    public final int AGE_TO_DIE = 14;
    
    /**
     * Sets required land, and base profit per animal
     * @param l land
     * @param p profit
     */
    public Animal(int cost, int profit, double land, double diseaseDeathRate, HarvestStrategy hs) {
        super(cost, profit, land, diseaseDeathRate, hs);
        setAge(0);
    }
    

    
}
