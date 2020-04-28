package main.java;

/**
 * Any farm animal
 * @author jjbaker
 *
 */
public abstract class Animal extends Asset{
    

    //how much revenue produced in a day
    private int producesProfit;
    //cycles to mature
    private final int TIME_TO_MATURE = 3;
    //cycles to die
    private final int AGE_TO_DIE = 14;
    //age of animal -> used to determine juvenile/adult
    private int age;
    //chance to procreate
    //private double procreateChance;
    
    /**
     * Sets required land, and base profit per animal
     * @param l land
     * @param p profit
     */
    public Animal(double procreate, int cost, int profit, double land, double diseaseDeathRate) {
        super(cost, profit, land, diseaseDeathRate);
        //procreateChance = procreate;
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
     * sets the animals chance to procreate on each 4th cycle
     * @param p chance to procreate
     */
    protected void setChanceToProcreate(double p) {
        //procreateChance = p;
    }
    
    /**
     * Increase age of the animal by 1
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
