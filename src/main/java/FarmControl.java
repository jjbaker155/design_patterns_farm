package main.java;

import java.util.Random;

public class FarmControl {
    
    //defaults
    private final int INITIAL_FARMERS = 3;
    private final double MAX_ACREAGE = 1000;
    private final int MAX_MONEY = 100000;
    private final double STARTING_ACREAGE = 1;
    
    private Random rand;
    private Farm farm;
    private FarmerControl farmerControl;
    
    
    //the only farm control - singleton
    private static FarmControl theOnlyFarmControl;
    
    
    /**
     * Private constructor
     */
    private FarmControl() {
        farm = Farm.makeFarm(STARTING_ACREAGE);
        farmerControl = FarmerControl.createFarmerControl();
    }
    
    /**
     * Create singleton FarmControl
     * @return FarmControl
     */
    public static FarmControl createFarmControl() {
        if (theOnlyFarmControl == null) {
            theOnlyFarmControl = new FarmControl();
        }
        return theOnlyFarmControl;
    }
    
    
    /**
     * Generate 3 random farmer types for your farm
     * @param farm
     */
    public void generateInitialFarmers(Farm farm) {
        
    }
    
    /**
     * Generate 4 random initial assets for your farm
     */
    public void generateInitialAssets(Farm farm) {
        
    }
    
    /**
     * Adds a farmer (random type)
     * @param Farm to add a Farmer to
     */
    public void hireRandomFarmer(Farm farm) {
        farm.addFarmer(farmerControl.randomFarmer());
    }
    
    /**
     * Add a random asset to your farm
     * @param Farm to add the asset to
     * @param String representing asset type
     */
    public void purchaseRandomAsset(Farm farm) {
        
    }
    
    
}
