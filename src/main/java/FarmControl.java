package main.java;

import java.util.ArrayList;
import java.util.Random;

import exceptions.AssetAlreadyDeadException;
import exceptions.FarmIsBankruptException;
import main.java.FarmerControl.FarmerKind;

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
    private final static int FARMER_PER_DIEM = 50;
    
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
        //try to heal
        //payroll
        //should hire farmer?
        //should buy acre?
        
    }
    
    private void runNight() {
        //clear dead assets
        //chance for disease (one living asset per acre)
        //chance for predator (one living asset per acre)
    }
    
    
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
        if(a instanceof Animal) {
           Animal animal = (Animal) a;
           animal.setAge(0);
        }
        try {
            farm.deductMoney(a.getCost());
        }
        catch(FarmIsBankruptException e){
            //TODO: handle end of simulation
        }
    }
    
    /**
     * Logic to decide if farm should buy an acre
     * @return
     */
    private boolean shouldBuyAcre() {
        //if total farmers add up to more than 10 per acre
        //or if you have 25% more money than needed to buy acre
        if (farm.getFarmerCount() / farm.getSize() < 0.1
                || farm.getMoney() >= ACRE_COST * 1.25) {
            return true;
        }
        return false;
    }
    
    /**
     * Logic to decide if farmer should be hired
     * @return true if farmer should be hired
     */
    private boolean shouldHireFarmer() {
        //if farmer coverage bonus falls below 0.5
        if (farmerCoverageBonus() < 0.10) {
            return true;
        }
        else
            return false;
    }
    
    /**
     * 
     * @return double a multiplier for coverage bonus
     */
    private double farmerCoverageBonus() {
        double d = farm.getFarmerCount() / farm.getAcreage() / 50;
        return d;
    }
    
    /**
     * Increase harvest price for animals (a multiplier)
     * @return double a multiplier for animal sale
     */
    private double animalHarvestBonus() {
        return numberOfAnimalFarmers() / numberOfAnimals() / 25;
    }
    
    /**
     * Increase harvest price for crops (a multiplier)
     * @return double a multiplier for crop sale
     */
    private double cropHarvestBonues() {
        return numberOfCropFarmers() / numberOfCrops() / 25;
    }
    
    /**
     * Increase sale price of all commodities (a multiplier) 
     * @return double a multiplier for sale price
     */
    private double assetSaleBonus() {
        return numberOfMerchantFarmers() / farm.getAcreage() / 25;
    }
    
    /**
     * Increase odds of animal survival when healing
     * @return
     */
    private double veterinaryBonus() {
        return numberOfVeterinaryFarmers() / numberOfAnimals() / 25;
    }
    
    /**
     * Increse odds of healing crops
     * @return
     */
    private double cropHealBonus() {
        return numberOfCropFarmers() / numberOfCrops() / 15.0;
    }
    
    /**
     * Returns number of living animals on the farm
     * @return
     */
    private int numberOfAnimals() {
        int num = 0;
        ArrayList<Asset> list = farm.getAssetList();
        for(Asset a : list) {
            if (a instanceof Animal) {
                if(a.isAlive()) {
                    num++;
                }
            }
        }
        return num;
    }
    
    /**
     * Returns the number of living crops on the farm
     * @return
     */
    private int numberOfCrops() {
        int num = 0;
        ArrayList<Asset> list = farm.getAssetList();
        for(Asset a : list) {
            if (a instanceof Crop) {
                if(a.isAlive()) {
                    num++;
                }
            }
        }
        return num;
    }
    
    private int numberOfAnimalFarmers() {
        int num = 0;
        ArrayList<Farmer> list = farm.getFarmerList();
        for(Farmer f : list) {
            if (f.getFarmerKind().equals(FarmerKind.ANIMAL)) {
                num++;
            }
        }
        return num;
    }
    
    private int numberOfCropFarmers() {
        int num = 0;
        ArrayList<Farmer> list = farm.getFarmerList();
        for(Farmer f : list) {
            if (f.getFarmerKind().equals(FarmerKind.CROPS)) {
                num++;
            }
        }
        return num;
    }
    
    private int numberOfVeterinaryFarmers() {
        int num = 0;
        ArrayList<Farmer> list = farm.getFarmerList();
        for(Farmer f : list) {
            if (f.getFarmerKind().equals(FarmerKind.VETERINARY)) {
                num++;
            }
        }
        return num;
    }
    
    private int numberOfMerchantFarmers() {
        int num = 0;
        ArrayList<Farmer> list = farm.getFarmerList();
        for(Farmer f : list) {
            if (f.getFarmerKind().equals(FarmerKind.MERCHANT)) {
                num++;
            }
        }
        return num;
    }
    
}
