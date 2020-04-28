package main.java;

import java.util.Random;

import exceptions.AssetAlreadyDeadException;
import exceptions.FarmIsBankruptException;

/**
 * Functionality for the Farm at a higher level of abstraction. Decision logic, etc.
 * @author jjbaker4
 * @version 1.0
 *
 */
public class FarmControl {
    
    //defaults
    public final static int INITIAL_FARMERS = 3;
    public final static int INITIAL_ASSETS = 4;
    public final static double MAX_ACREAGE = 100;
    public final static int MAX_MONEY = 10000;
    public final static int INITIAL_MONEY = 1000;
    public final static double STARTING_ACREAGE = 1.0;
    private final static int ACRE_COST = 1000;
    
    private Random rand;
    private Farm farm;
    private FarmerControl farmerControl;
    
    private AssetFactory af;
    
    //the only farm control - singleton
    private static FarmControl farmControlSoleInstance;
    
    /**
     * Private constructor
     */
    private FarmControl() {
        farm = Farm.makeFarm();
        af = AssetFactory.makeAssetFactory();
    }
    
    /**
     * Create singleton FarmControl
     * @return FarmControl
     */
    public static FarmControl createFarmControl() {
        if (farmControlSoleInstance == null) {
            farmControlSoleInstance = new FarmControl();
            farmControlSoleInstance.attachFarmerControl();
            farmControlSoleInstance.generateInitialFarmers();
        }
        return farmControlSoleInstance;
    }
    
    
    /**
     * Generate 3 random farmer types for your farm
     * @param farm
     */
    private void generateInitialFarmers() {
        for (int i = 0; i <= INITIAL_FARMERS - 1; i++) {
            farm.addFarmer(farmerControl.randomFarmer());
        }
    }
    
    /**
     * Generate initial assets for your farm
     */
    public void generateInitialAssets(Farm f) {
        for (int i = 0; i < INITIAL_ASSETS; i++) {
            farm.addAsset(af.createRandomAsset());
        }
    }
    
    /**
     * Adds a farmer (random type)
     * @param Farm to add a Farmer to
     */
    public void hireRandomFarmer() {
        farm.addFarmer(farmerControl.randomFarmer());
    }
    
    /**
     * Add a random asset to your farm
     * @param Farm to add the asset to
     * @param String representing asset type
     */
    public void purchaseRandomAsset() {
        
    }
    
    private void runDay() {
        
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
    
    private void runNight() {
        //clear dead assets
        //chance for disease (one living asset per acre)
        //chance for predator (one living asset per acre)
    }
    
    private boolean shouldBuyAcre() {
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
    
    /**
     * Returns the Farm object
     * @return Farm
     */
    public Farm getFarm() {
        return farm;
    }
    
  
    /**
     * "Pull yourself up by your bootstraps!"
     */
    private void attachFarmerControl() {
        if (farmerControl == null)
            farmerControl = FarmerControl.createFarmerControl(this);
    }
    
    /**
     * Reorder the animal. More formally, it sets it to alive and deducts the cost
     * @param a
     */
    private void reOrder(Asset a) {
        a.setAliveReorder();
        try {
            farm.deductMoney(a.getCost());
        }
        catch(FarmIsBankruptException e){
            //TODO: handle end of simulation
        }
    }
}
