package main.java;

import java.util.ArrayList;

/**
 * A single farm
 * @author jjbaker4
 *
 */
public class Farm {
    
       
    //list of Farmers
    private ArrayList<Farmer> farmers;
    //size of farm in square acres
    private double acres;
    //list of assets
    private ArrayList<Asset> assets;
    //odds of a predator attack per acre 
    private static final double PREDATOR_CHANCE = .25;
    
    //the only farm - singleton
    private static Farm theOnlyFarm;

    /**
     * Private constructor sets up new ArrayList of Farmer, and array list of Assets
     * Sets default acres.
     */
    private Farm(double a) {
        farmers = new ArrayList<Farmer>();
        assets = new ArrayList<Asset>();
        acres = a;
    }
    
    /**
     * Makes the only farm
     * @param double a acres
     * @return Farm
     */
    public static Farm makeFarm(double a) {
        if (theOnlyFarm == null) {
            theOnlyFarm = new Farm(a);
        }
        return theOnlyFarm;
    }
    
    /**
     * Makes a test farm (FOR UNIT TESTING ONLY)
     * @param double a acres
     * @return Farm
     */
    public static Farm makeTestFarm(double a) {
        return new Farm(a);
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
    public ArrayList<Farmer> getFarmerList(){
        return farmers;
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
    
}
