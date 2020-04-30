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
    //wait time for regular harvest
    public final static int REG_HARVEST_WAIT = 2;
    //cycles to mature
    public final static int TIME_TO_MATURE = 3;
    //number farmers you begin with
    public final static int INITIAL_FARMERS = 3;
    //number assets you begin with
    public final static int INITIAL_ASSETS = 4;
    //acreage to win
    public final static double MAX_ACREAGE = 100;
    //money to win
    public final static int MAX_MONEY = 10000;
    //money to start with
    public final static int INITIAL_MONEY = 1000;
    //acres to start with
    public final static double STARTING_ACREAGE = 1.0;
    //cost to buy an acre
    private final static int ACRE_COST = 1000;
    //farmer daily pay
    private final static int FARMER_PER_DIEM = 50;
    //Farmer to Acre ratio
    private final static double FARMER_PURCHASE_TRIGGER = 0.4;
    //days between harvests for renewable assets
    public static final int DEFAULT_HARVEST_DAYS = 3;
    //for report building
    private final String REPORT_ITEM_SEPERATOR = "\n-------------------";
    private final String REPORT_STAR_SEPERATOR = "\n*****************" + 
    "*************";
    
    private Random rand;
    private Farm farm;
    private FarmerControl farmerControl;
    private int day;
    
    private StringBuilder dayReport;
    private StringBuilder nightReport;
    
    private AssetFactory af;
    
    //the only farm control - singleton
    private static FarmControl farmControlSoleInstance;
    
    /**
     * Private constructor
     */
    private FarmControl() {
        farm = Farm.makeFarm();
        af = AssetFactory.makeAssetFactory();
        day = 0;
        resetCycleReports();
    }
    
    /**
     * Private constructor for making test FarmControl
     * @param boolean to indicate using this contructor
     */
    private FarmControl(boolean test) {
        farm = Farm.makeTestFarm();
        af = AssetFactory.makeAssetFactory();
        day = 0;
        resetCycleReports();
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
     * This method is for unit testing ONLY!
     * Invoking it will DESTROY and replace the singleton FarmControl
     * @return
     */
    public static FarmControl createTestFarmControl() {
        farmControlSoleInstance = new FarmControl(true);
        farmControlSoleInstance.attachFarmerControl();
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
     * @throws AssetAlreadyDeadException 
     */
    public void generateInitialAssets(Farm f) throws AssetAlreadyDeadException {
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
     * @throws AssetAlreadyDeadException 
     */
    public void purchaseRandomAsset() throws AssetAlreadyDeadException {
        Asset a = af.createRandomAsset();
        if(farm.getSpaceAvailable() < a.getLandNeeded()) {
            buyAcre();
        }
        farm.addAsset(a);
    }
    
    
    
    private void runDay() throws AssetAlreadyDeadException {
        int totalCropEarnings = harvestCrops();
        
        
        //harvest and collect
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
    
    /**
     * Returns the Farm object
     * @return Farm
     */
    public Farm getFarm() {
        return farm;
    }
    
    /**
     * Purchase an acre
     *  
     */
    public void buyAcre() {
        farm.deductMoney(ACRE_COST);
        farm.addAcre();
    }
  
    
    public int harvestCrops() throws AssetAlreadyDeadException {
        dayReportAdd("Crop Harvest:\n" + REPORT_STAR_SEPERATOR);
        double bonus = cropHarvestBonus();
        double merchantBonus = merchantFarmerBonus();
        int proceeds = 0;
        ArrayList<Crop> list = getCropsForHarvest();
        for(Crop c : list) {
            dayReportAdd(REPORT_ITEM_SEPERATOR);
            String harvestType;
            if (c.isHarvestTerminal()) {
                harvestType = " Terminal \n";
            }
            else
            {
                harvestType = " Normal \n";
            }
            int assetValue = c.harvest(); 
            proceeds += assetValue;
            dayReportAdd("Harvest " + c.getTypeName() + "\n" + "Harvest Type:"
            + harvestType + "Value: " + assetValue);
        }
        dayReportAdd("Crop farmer bonus = " + bonus + "\n");
        dayReportAdd("Merchant farmer bonus = " + merchantBonus + "\n");
        double multiplier = bonus + merchantBonus + 1;
        double p = proceeds * multiplier;
        return (int) p;
    }
    
    public int harvestAnimals() throws AssetAlreadyDeadException {
        double bonus = animalHarvestBonus();
        double merchantBonus = merchantFarmerBonus();
        int proceeds = 0;
        ArrayList<Animal> list = getAnimalsForHarvest();
        for(Animal a : list) {
            dayReportAdd(REPORT_ITEM_SEPERATOR);
            String harvestType;
            if (a.isHarvestTerminal()) {
                harvestType = " Terminal \n";
            }
            else
            {
                harvestType = " Normal \n";
            }
            int assetValue = a.harvest(); 
            proceeds += assetValue;
            dayReportAdd("Harvest " + a.getTypeName() + "\n" + "Harvest Type:"
            + harvestType + "Value: " + assetValue);
        }
        dayReportAdd("Animal farmer bonus = " + bonus + "\n");
        dayReportAdd("Merchant farmer bonus = " + merchantBonus + "\n");
        double multiplier = bonus + merchantBonus + 1;
        double p = proceeds * multiplier;
        return (int) p;
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
    public void reOrder(Asset a) {
        a.setAliveNewAsset();
        farm.deductMoney(a.getCost());
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
        if (farm.getFarmerCount()/farm.getAcreage() < 0.4) {
            return true;
        }
        else
            return false;
    }
    
    /**
     * If acreage available exceeds .5
     * @return
     */
    private boolean shouldBuyAsset() {
        if (farm.getSpaceAvailable() > .5) {
            return true;
        }
        return false;
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
    private double cropHarvestBonus() {
        return numberOfCropFarmers() / numberOfCrops() / 25;
    }
       
    /**
     * Increase odds of animal survival when healing
     * @return
     */
    private double veterinaryBonus() {
        return numberOfVeterinaryFarmers() / numberOfAnimals() / 25;
    }
    
    /**
     * Increase odds of healing crops
     * @return
     */
    private double cropHealBonus() {
        return numberOfCropFarmers() / numberOfCrops() / 15.0;
    }
    
    /**
     * 
     * @return a % bonus for merchant farmer bonus
     */
    private double merchantFarmerBonus() {
        return numberOfMerchantFarmers() * .04; 
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
    
    /**
     * Sets reports up for a new cycle
     */
    private void resetCycleReports() {
        String dayInit = new String(REPORT_STAR_SEPERATOR + REPORT_STAR_SEPERATOR +
                "\n" + "DayReport:\n\n");
        dayReport = new StringBuilder(dayInit);
        String nightInit = new String(REPORT_STAR_SEPERATOR + REPORT_STAR_SEPERATOR + 
                "\n" + "NightReport:\n");
        nightReport = new StringBuilder(nightInit);
    }
    
    /**
     * 
     * @param String to add to report
     */
    public void dayReportAdd(String appendation) {
        dayReport.append(appendation + "\n");
    }
    
    /**
     * 
     * @param String to add to report
     */
    public void nightReportAdd(String appendation) {
        nightReport.append(appendation + "\n");
    }
    
    private ArrayList<Crop> getCropsForHarvest() {
        ArrayList<Asset> assetList = farm.getAssetList();
        ArrayList<Crop> harvestList = new ArrayList<Crop>();
        for (Asset a : assetList) {
            if (a.getHarvestDays() <= 0 && a instanceof Crop && a.isAlive()) {
                harvestList.add((Crop) a);
            }
        }
        return harvestList;
    }
    
    private ArrayList<Animal> getAnimalsForHarvest() {
        ArrayList<Asset> assetList = farm.getAssetList();
        ArrayList<Animal> harvestList = new ArrayList<Animal>();
        for (Asset a : assetList) {
            if ((a instanceof Animal && a.getHarvestDays() == 0) && a.isAlive()) {
                harvestList.add((Animal) a);
            }
        }
        return harvestList;
    }    
    
}
