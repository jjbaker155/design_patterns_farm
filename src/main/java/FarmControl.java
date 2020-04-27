package main.java;

import java.util.Random;

import exceptions.NoSuchFarmTypeException;

public class FarmControl {
    
    private final int INITIAL_FARMERS = 3;
    private final int MAX_ACREAGE = 1000;

    private Random rand;
    
    
    //the only farm control - singleton
    private static FarmControl theOnlyFarmControl;
    
    
    /**
     * Private constructor
     */
    private FarmControl() {   
    }
    
    /**
     * Create singleton FarmControl
     * @return FamrControl
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
    private void generateInitialFarmers(Farm farm) {
        
    }
    
    /**
     * Generate 4 random initial assets for your farm
     */
    private void generateInitialAssets() {
        
    }
    
    /**
     * Generate 1 random asset for your farm
     */
    private void generateAsset() {
        
    }
}
