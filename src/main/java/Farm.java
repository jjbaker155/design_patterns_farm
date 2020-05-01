package main.java;

import java.util.ArrayList;

/**
 * The single farm
 * @author jjbaker4
 *
 */
public class Farm {
    
       
    //list of Farmers
    private ArrayList<Farmer> farmers;
    //size of farm in square acres
    private double acres;
    //amount of land currently available in sq acres
    private double spaceAvailable;
    //list of assets
    private ArrayList<Asset> assets;
    //odds of a predator attack per acre 
    private static final double PREDATOR_CHANCE = .25;
    //amount of money
    private int currentMoney;
    //the only farm - singleton
    private static Farm farmSoleInstance;
    

    /**
     * Private constructor sets up new ArrayList of Farmer, and array list of Assets
     * Sets default acres.
     */
    private Farm() {
        farmers = new ArrayList<Farmer>();
        assets = new ArrayList<Asset>();
        acres = FarmControl.STARTING_ACREAGE;
        spaceAvailable = acres;
        currentMoney = FarmControl.INITIAL_MONEY;
    }
    
    /**
     * Private constructor foe making test Farm
     * @param test
     */
    private Farm (boolean test) {
        farmers = new ArrayList<Farmer>();
        assets = new ArrayList<Asset>();
        acres = FarmControl.STARTING_ACREAGE;
        spaceAvailable = acres;
        currentMoney = FarmControl.INITIAL_MONEY;
    }
    
    /**
     * Makes the only farm
     * @param double a acres
     * @return Farm
     */
    public static Farm makeFarm() {
        if (farmSoleInstance == null) {
            farmSoleInstance = new Farm();
        }
        return farmSoleInstance;
    }
    /**
     * Makes a test farm (FOR UNIT TESTING ONLY)
     * This will destroy the existing singleton Farm!
     * @param double a acres
     * @return Farm
     */
    public static Farm makeTestFarm() {
        farmSoleInstance = new Farm(true);
        return farmSoleInstance;
    }
    
    /**
     * 
     * @return size of farm in acres
     */
    public double getSize() {
        return acres;
    }
    
    /**
     * Add a Farmer
     * @param f Farmer to add
     */
    public void addFarmer(Farmer f) {
        farmers.add(f);
    }
    
    /**
     * Returns list of farmers 
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Farmer> getFarmerList(){
        return (ArrayList<Farmer>) farmers.clone();
    }
    
    /**
     * Returns number of farmers on this farm
     * @return
     */
    public int getFarmerCount() {
        return farmers.size();
    }
    
    /**
     * Returns an acre productivity factor ranging from 0.5 to 1
     * @return
     */
    public double acreProductivity() {
        double prod = 0.5 + (farmers.size()/acres)/20;
        if (prod > 1.0) {
            return 1.0;
        }
        else {
            return prod;
        }
    }
     
    /**
     * Adds 1 acre
     */
    public void addAcre() {
        acres = acres + 1.0;
        spaceAvailable = spaceAvailable + 1.0;
    }
    
    /**
     * Sets acres
     * @param a double acres to be set
     */
    public void setAcreage(double a) {
        acres = a;
    }
    
    /**
     * Gets size of farm in acres
     * @return
     */
    public double getAcreage() {
        return acres;
    }
    
    /**
     * Removed instance of Farmer specified
     * @param f Farmer to remove
     * @return true if successful
     */
    public boolean removeFarmer(Farmer f) {
        return farmers.remove(f);
    }
    
    /**
     * Removes the specific asset from the list if it is present
     * @param a
     * @return true if successful
     */
    public boolean removeAsset(Asset a) {
        return assets.remove(a);
    }
    
    public boolean addAsset(Asset a) {
        spaceAvailable = spaceAvailable - a.getLandNeeded();
        return assets.add(a);
    }
    
    
    public void setMoney(int money) throws IllegalArgumentException {
        if(money >= FarmControl.MAX_MONEY) {
            throw new IllegalArgumentException("Farm cannot have more than " + 
                    FarmControl.MAX_MONEY + " dollars.");
        }
        currentMoney = money;
    }
    
    /**
     * Deduct money. Throws exception if Farm is bankrupt.
     * @param d amount of money to deduct
     */
    public void deductMoney(int d) {
        currentMoney -= d;
        //if (currentMoney < 0) {
        //    throw new FarmIsBankruptException();
        //}
    }
    
    /**
     * Add money. Throws exception of Farm has won the simulation.
     * @param a
     * @throws FarmHasWonException
     */
    public void addMoney(int a) throws FarmHasWonException {
        currentMoney += a;
        if (currentMoney >= FarmControl.MAX_MONEY) {
            throw new FarmHasWonException();
        }
    }
    
    public int getMoney() {
        return currentMoney;
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Asset> getAssetList() {
        return (ArrayList<Asset>) assets.clone();
    }
    
    public Asset getAssetByIndex(int index) {
        return assets.get(index);
    }
    
    public Asset removeAssetByIndex(int index) {
        return assets.remove(index);
    }
    
    public double getSpaceAvailable() {
        return spaceAvailable;
    }
}
