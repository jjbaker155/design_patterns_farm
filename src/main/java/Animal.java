package main.java;

/**
 * Any farm animal.
 * @author jjbaker
 *
 */
public abstract class Animal extends Asset {
    
    //cycles to die
    public static final int AGE_TO_DIE = 14;
    
    /**
     * Sets required land, and base profit per animal.
     * @param cost integer cost of product
     * @param profit integer profit from product
     * @param land number of acres
     * @param hs HarvestStrategy
     */
    public Animal(int cost, int profit, double land, HarvestStrategy hs) {
        super(cost, profit, land, hs);
        setAge(0);
    }
    

    
}
