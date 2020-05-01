/**
 * Parent for all asset types. Contains and manages attributes that 
 * all Farm Assets share. Also has some creation methods.
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
    //disease death rate
    private double diseaseDeathRate;
    //state context for this asset
    private AssetStateContext sc;
    //harvest strategy
    private final HarvestStrategy HARVEST_STRATEGY;
    //age of asset in days
    private int age;
    //days until next harvest
    private int harvestDays;
    
    static Random rand = new Random();
    
    
    public Asset(int c, int p, double l, double deathRate, HarvestStrategy hs) {
        landNeeded = l;
        cost = c;
        profit = p;
        sc = new AssetStateContext();
        diseaseDeathRate = deathRate;
        HARVEST_STRATEGY = hs;
        harvestDays = FarmControl.DEFAULT_HARVEST_DAYS;
        setAlive();
    }

    /**
     * Gets the land needed
     * @return double the amount of land this animal occupies
     */
    public double getLandNeeded() {
        return landNeeded;
    }
    
    /**
     * Returns the cost to purchase this animal
     * @return
     */
    public int getCost() {
        return cost;
    }
    
    /**
     * Returns the profit this asset produces
     * @return
     */
    public int getProfit() {
        return profit;
    }
    
    /**
     * Calls harvest() from the appropriate HarvestStrategy object 
     */
    public int harvest() {
        return HARVEST_STRATEGY.harvest(this);
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
    
    public boolean isHealthy() {
        if (isAlive() && !isDiseased()) {
            return true;
        }
        return false;
    }
    
    /**
     * Set state of asset to diseased
     * @throws AssetAlreadyDeadException Asset is already dead. You cannot change state
     */
    public void setDiseased() {
        sc.setState(new StateDiseased());
    }
    
    /**
     * Set state of the asset to dead
     * @throws AssetAlreadyDeadException Asset is already dead. You cannot change state
     */
    public void setDead() {
        sc.setState(new StateDead());
    }
    
    /**
     * Sets the state of the asset to alive
     * 
     */
    public void setAlive() {
        sc.setState(new StateAlive());
    }
    
    /**
     * Set asset to alive in the case of a reorder
     */
    /*
    public void setAliveNewAsset() {
        sc.setState(new StateAlive());
        setAge(0);
        harvestDays = FarmControl.DEFAULT_HARVEST_DAYS;
    }
    */
    
    
    public void setAge(int a) {
        age = a;
    }
    
    public int getAge() {
        return age;
    }
    
    /**
     * Increase age of the asset by 1 day
     * Decrease countdown until next harvest
     */
    public void incrementDay() {
        age++;
        harvestDays--;
    }
    
    /**
     * Return number of days until harvest
     * @return
     */
    public int getHarvestDays() {
        return harvestDays;
    }
    
    /**
     * Get a String representation of the asset type
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
     * Indicates whether or not harvesting this asset will kill it
     * @return boolean true if terminal
     */
    public boolean isHarvestTerminal() {
        return HARVEST_STRATEGY.isHarvestTerminal();
    }
    
    
    public void setHarvestDays(int hd) {
        harvestDays = hd;
    }
}
