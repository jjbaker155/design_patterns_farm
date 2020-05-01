/**
 * Parent for all asset types. Contains and manages attributes that 
 * all Farm Assets share. Also has some creation methods.
 * @jjbaker4
 * @version 1.0
 */

package main.java;

import java.util.Random;

public abstract class Asset {
        
    //How many acres does it take up
    private double landNeeded;
    //how much revenue produced in its cycle
    private int profit;
    //cost to purchase asset
    private int cost;
    //state context for this asset
    private AssetStateContext sc;
    //harvest strategy
    private final HarvestStrategy harvestStrategy;
    //age of asset in days
    private int age;
    //days until next harvest
    private int harvestDays;
    
    static Random rand = new Random();
    
    
    /**
     * Contructor for Asset. Attributes are passed up from subclasses.
     * @param c int cost
     * @param p int profit
     * @param l land needed
     * @param hs harvest strategy
     */
    public Asset(int c, int p, double l, HarvestStrategy hs) {
        landNeeded = l;
        cost = c;
        profit = p;
        sc = new AssetStateContext();
        harvestStrategy = hs;
        harvestDays = FarmControl.DEFAULT_HARVEST_DAYS;
        setAlive();
    }

    /**
     * Gets the land needed.
     * @return double the amount of land this animal occupies
     */
    public double getLandNeeded() {
        return landNeeded;
    }
    
    /**
     * Returns the cost to purchase this animal.
     * @return
     */
    public int getCost() {
        return cost;
    }
    
    /**
     * Returns the profit this asset produces.
     * @return
     */
    public int getProfit() {
        return profit;
    }
    
    /**
     * Calls harvest() from the appropriate HarvestStrategy object.
     */
    public int harvest() {
        return harvestStrategy.harvest(this);
    }
    
    /**
     * TODO: May not need this.... 
     * @return a state context to be utilized by control class
     */
    public AssetStateContext getStateContext() {
        return sc;
    }
    
    public boolean isAlive() {
        return sc.isAlive();
    }
    
    public boolean isDiseased() {
        return sc.isDiseased();
    }
    
    public boolean isDead() {
        return sc.isDead();
    }
    
    /**
     * Returns of the asset is alive and not sick.
     * @return boolean true if asset is healthy
     */
    public boolean isHealthy() {
        if (isAlive() && !isDiseased()) {
            return true;
        }
        return false;
    }
    
    /**
     * Set state of asset to diseased.
     * @throws AssetAlreadyDeadException Asset is already dead. You cannot change state
     */
    public void setDiseased() {
        sc.setState(new StateDiseased());
    }
    
    /**
     * Set state of the asset to dead.
     * @throws AssetAlreadyDeadException Asset is already dead. You cannot change state
     */
    public void setDead() {
        sc.setState(new StateDead());
    }
    
    /**
     * Sets the state of the asset to alive.
     * 
     */
    public void setAlive() {
        sc.setState(new StateAlive());
    }
    
    
    public void setAge(int a) {
        age = a;
    }
    
    public int getAge() {
        return age;
    }
    
    /**
     * Increase age of the asset by 1 day.
     * Decrease countdown until next harvest
     */
    public void incrementDay() {
        age++;
        harvestDays--;
    }
    
    /**
     * Return number of days until harvest.
     * @return
     */
    public int getHarvestDays() {
        return harvestDays;
    }
    
    /**
     * Get a String representation of the asset type.
     * @return
     */
    public String getTypeName() {
        if (this instanceof Cattle) {
            return "Cattle";
        }
        if (this instanceof Hog) {
            return "Hog";
        }
        if (this instanceof Sheep) {
            return "Sheep";
        }
        if (this instanceof DairyCow) {
            return "Dairy Cow";
        }
        if (this instanceof Corn) {
            return "Corn";
        }
        if (this instanceof Soy) {
            return "Soy";
        }
        return null;
    }
    
    /**
     * Indicates whether or not harvesting this asset will kill it.
     * @return boolean true if terminal
     */
    public boolean isHarvestTerminal() {
        return harvestStrategy.isHarvestTerminal();
    }
    
    
    public void setHarvestDays(int hd) {
        harvestDays = hd;
    }
}
