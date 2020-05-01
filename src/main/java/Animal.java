package main.java;

/**
 * Any farm animal
 * @author jjbaker
 *
 */
public abstract class Animal extends Asset{
    
    //cycles to die
    public final static int AGE_TO_DIE = 14;
    
    /**
     * Sets required land, and base profit per animal
     * @param l land
     * @param p profit
     */
    public Animal(int cost, int profit, double land, HarvestStrategy hs) {
        super(cost, profit, land, hs);
        setAge(0);
    }
    

    
}
