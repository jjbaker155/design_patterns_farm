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
    //age of animal -> used to determine juvenile/adult
    private int age;
    
    /**
     * Sets required land, and base profit per animal
     * @param l land
     * @param p profit
     */
    public Animal(int cost, int profit, double land, double diseaseDeathRate, HarvestStrategy hs) {
        super(cost, profit, land, diseaseDeathRate, hs);
        age = 0;
    }
    
    /**
     * initiates age - should set to 0. Only for subclass use
     * @param int age
     */
    protected void setAge(int age) {
        this.age = age;
    }
    
    /**
     * Increase age of the animal by 1 day
     */
    public void incrementAge() {
        age++;
    }
  
    /**
     * Get age of animal in cycles
     * @return
     */
    public int getAge() {
        return age;
    }
    

    
}
