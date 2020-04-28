package main.java;

import java.util.Random;

public class FarmControl {
    
    //defaults
    private final int INITIAL_FARMERS = 3;
    private final double MAX_ACREAGE = 1000;
    private final int MAX_MONEY = 100000;
    private final double STARTING_ACREAGE = 1;
    private final int ACRE_COST = 1000;
    
    private Random rand;
    private Farm farm;
    private FarmerControl fc;
    
    
    //the only farm control - singleton
    private static FarmControl theOnlyFarmControl;
    
    
    /**
     * Private constructor
     */
    private FarmControl() {
        farm = Farm.makeFarm(STARTING_ACREAGE);
        fc = FarmerControl.createFarmerControl();
        generateInitialFarmers(farm);
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
    private void generateInitialFarmers(Farm farm) {
        for (int i = 0; i <= INITIAL_FARMERS; i++) {
            farm.addFarmer(fc.randomFarmer());
        }
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
        farm.addFarmer(fc.randomFarmer());
    }
    
    /**
     * Add a random asset to your farm
     * @param Farm to add the asset to
     * @param String representing asset type
     */
    public void purchaseRandomAsset(Farm farm) {
        
    }
    
    private void runDay(Farm farm) {
        
        //collect revenue
        //reorder dead assets (do not clear acreage)
        //receive delivery of replacement assets on order (do not change acreage)
        /*
         * check money, farmers, acreage, assets, acreage capacity
         * decide on purchases/hiring
         * check for diseased assets
         * attempt to cure disease
         * change status to dead, or alive
         */
    }
    
    private void runNight(Farm farm) {
        //clear dead assets
        //chance for disease (one living asset per acre)
        //chance for predator (one living asset per acre)
    }
    
    private boolean shouldBuyAcre(Farm farm) {
        if (farm.getFarmerCount() / farm.getSize() < 0.1 ) {
            return true;
        } //todo - more logic
        return false;
    }
    
    /*
    private boolean shouldHireFarmer() {
        
    }
    */
    
    //TODO: write after state pattern is put together
    public void predatorAttack() {
        
    }
    
    
}
