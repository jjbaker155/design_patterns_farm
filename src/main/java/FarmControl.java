package main.java;

import java.util.ArrayList;
import java.util.Random;

import main.java.FarmerControl.FarmerKind;

/**
 * Functionality for the Farm at a higher level of abstraction. Decision logic, etc.
 * This basically IS the sim, for better or worse.
 * @author jjbaker4
 * @version 1.0
 *
 */
public class FarmControl {
    
    //defaults
    //wait time for regular harvest
    public static final int REG_HARVEST_WAIT = 2;
    //cycles to mature
    public static final int TIME_TO_MATURE = 3;
    //number farmers you begin with
    public static final int INITIAL_FARMERS = 3;
    //number assets you begin with
    public static final int INITIAL_ASSETS = 4;
    //number of cycles before simulation ends
    public static final int MAX_CYCLES = 25;
    //acreage to win
    public static final double MAX_ACREAGE = 100.0;
    //money to win
    public static final int MAX_MONEY = 10000;
    //money to start with
    public static final int INITIAL_MONEY = 2000;
    //acres to start with
    public static final double STARTING_ACREAGE = 1.0;
    //cost to buy an acre
    private static final int ACRE_COST = 1000;
    //farmer daily pay
    private static final int FARMER_PER_DIEM = 50;
    //Farmer to Acre ratio
    private static final double FARMER_PURCHASE_TRIGGER = 0.4;
    //days between harvests for renewable assets
    public static final int DEFAULT_HARVEST_DAYS = 3;
    //chance animal will get sick
    private static final double ANIMAL_SICKNESS_CHANCE = 0.70;
    //chance crop will get sick
    private static final double CROP_SICKNESS_CHANCE = 0.60;
    //chance of healing asset
    private static final double ASSET_HEAL_CHANCE = 0.15;
    //for report building
    private static final String REPORT_ITEM_SEPERATOR = "-------------------";
    private static final String REPORT_STAR_SEPERATOR = "*****************" 
        + "*************";
    
    private int cycles;
    
    private static Random rand = new Random();
    private Farm farm;
    private FarmerControl farmerControl;
    
    private StringBuilder dayReport;
    
    private AssetFactory af;
    
    //the only farm control - singleton
    private static FarmControl farmControlSoleInstance;
    
    private boolean gameOn;
    
    /**
     * Private constructor.
     * @throws FarmIsBankruptException if farm is out of money
     */
    private FarmControl() {
        farm = Farm.makeFarm();
        af = AssetFactory.makeAssetFactory();
        dayReport = new StringBuilder();;
        gameOn = true;
        cycles = 0;
        attachFarmerControl();
        generateInitialFarmers();
        generateInitialAssets();
    }
    
    /**
     * Private constructor for making test FarmControl.
     * @param boolean to indicate using this constructor
     */
    private FarmControl(boolean test) {
        this.farm = Farm.makeTestFarm();
        this.af = AssetFactory.makeAssetFactory();
        dayReport = new StringBuilder();
        this.gameOn = true;
        this.cycles = 0;
        this.attachFarmerControl();
    }
    
    /**
     * Create singleton FarmControl.
     * @return FarmControl
     * @throws FarmIsBankruptException  if farm runs out of money
     */
    public static FarmControl createFarmControl() {
        if (farmControlSoleInstance == null) {
            farmControlSoleInstance = new FarmControl();
        }
        return farmControlSoleInstance;
    }
    
    /**
     * This method is for unit testing ONLY!.
     * Invoking it will DESTROY and replace the singleton FarmControl
     * @return
     */
    public static FarmControl createTestFarmControl() {
        farmControlSoleInstance = new FarmControl(true);
        return farmControlSoleInstance;
    }
    
    /**
     * Create a FarmerControl singleton if it is needed and attach it.
     */
    private void attachFarmerControl() {
        if (farmerControl == null) {
            farmerControl = FarmerControl.createFarmerControl();
        }
    }

    /**
     * Generate 3 random farmer types for your farm.
     */
    private void generateInitialFarmers() {
        dayReportAdd("///////////////////////////");
        dayReportAdd("Initial Farmer Hires:");
        dayReportAdd(REPORT_ITEM_SEPERATOR);
        for (int i = 0; i < INITIAL_FARMERS; i++) {
            hireRandomFarmer();
        }
        dayReportAdd("\n");
    }
    
    /**
     * Generate initial assets for your farm.
     * @throws FarmIsBankruptException if farm is out of money
     */
    public void generateInitialAssets() {
        dayReportAdd("///////////////////////////");
        dayReportAdd("Initial Asset Purchase:");
        dayReportAdd(REPORT_ITEM_SEPERATOR);
        for (int i = 0; i < INITIAL_ASSETS; i++) {
            try {
                purchaseRandomAsset();
            } catch (FarmIsBankruptException e) {
                e.printStackTrace();
            }
        }
        dayReportAdd("\n");
    }
    
    /**
     * Adds a farmer (random type).
     */
    public void hireRandomFarmer() {
        Farmer f = farmerControl.randomFarmer();
        farm.addFarmer(f);
        dayReportAdd("Hired new " + f.getTypeName() + " farmer.");
    }
    
    /**
     * Add a random asset to your farm.
     * @throws FarmIsBankruptException  if farm is out of money
     */
    public void purchaseRandomAsset() throws FarmIsBankruptException {
        Asset a = af.createRandomAsset();
        if (farm.getSpaceAvailable() < a.getLandNeeded()) {
            buyAcre();
        }
        farm.addAsset(a);
        this.dayReportAdd(a.getTypeName() + " purchased. \nDeduct " + a.getCost());
        farm.deductMoney(a.getCost());
    }
    
    
    /**
     * Run through Day Sequence.
     * @throws FarmHasWonException  for various win circumstances
     * @throws FarmIsBankruptException  farm is out of money
     */
    public void runDay() throws FarmHasWonException, FarmIsBankruptException, 
        SimulationInconclusiveException {
        //todo: add some summary to day report
        int day = cycles + 1;
        dayReportAdd(REPORT_STAR_SEPERATOR + "\n" 
            +  "Day number " + day);
        healAnimal();
        healCrop();
        harvestCrops();
        harvestAnimals();
        payFarmers();
        if (shouldHireFarmer()) {
            hireRandomFarmer();
        }
        if (shouldBuyAsset()) {
            purchaseRandomAsset();
        }
        if (shouldBuyAcre()) {
            buyAcre();
        }
        clearOlderAnimals();
        incrementDay();
        if (cycles > MAX_CYCLES) {
            throw new SimulationInconclusiveException("Reached max days. Simulation has" 
                    + " ended inconclusively.");  
        }
        dayReportAdd("\nThat night...\n" + REPORT_STAR_SEPERATOR);
        makeAnimalSick();
        makeCropSick();
    }
    
    /**
     * Pay the farmers for their work on the farm.
     * @throws FarmIsBankruptException farm runs out of money
     */
    public void payFarmers() throws FarmIsBankruptException {
        int payRoll = farm.getFarmerCount() * FARMER_PER_DIEM;
        dayReportAdd("\n" + farm.getFarmerCount() + " farmers paid.\nTotal: " + payRoll);
        farm.deductMoney(payRoll);
    }
    
    /**
     * Returns the Farm object.
     * @return Farm
     */
    public Farm getFarm() {
        return farm;
    }
    
    /**
     * Purchase an acre of land.
     * @throws FarmIsBankruptException farm runs out of money
     */
    public void buyAcre() throws FarmIsBankruptException {
        farm.deductMoney(ACRE_COST);
        farm.addAcre();
    }
  
    /**
     * Harvest all crops that qualify and return the proceeds.
     * @return int the proceeds
     * @throws FarmHasWonException if max money is reached
     */
    public int harvestCrops() throws FarmHasWonException {
        dayReportAdd("Crop Harvest:\n" + REPORT_ITEM_SEPERATOR);
        double bonus = cropHarvestBonus();
        double merchantBonus = merchantFarmerBonus();
        int proceeds = 0;
        int count = 0;
        ArrayList<Crop> list = getCropsForHarvest();
        for (Crop c : list) {
            dayReportAdd(REPORT_ITEM_SEPERATOR);
            String harvestType;
            if (c.isHarvestTerminal()) {
                harvestType = " Terminal \n";
            }
            else {
                harvestType = " Normal \n";
            }
            int assetValue = c.harvest(); 
            proceeds += assetValue;
            count++;
            dayReportAdd("Harvest " + c.getTypeName() + "\n" + "Harvest Type:"
                + harvestType + "Value: " + assetValue);
        }
        dayReportAdd("Crop farmer harvest bonus = " + bonus);
        dayReportAdd("Merchant farmer harvest bonus = " + merchantBonus);
        double multiplier = bonus + merchantBonus + 1.0;
        double p = proceeds * multiplier;
        dayReportAdd("Harvested " + count + " crops.");
        dayReportAdd("Total crop earnings: " + p + "\n");
        farm.addMoney((int)p);
        return (int)p;
    }
    
    /**
     * Harvest all animals that qualify and return the proceeds.
     * @return int the proceeds
     * @throws FarmHasWonException Farm has won via one of a number of ways
     * @throws FarmIsBankruptException farm runs out of money
     */
    public int harvestAnimals() throws FarmHasWonException, FarmIsBankruptException {
        dayReportAdd("Animal Harvest:\n" + REPORT_ITEM_SEPERATOR);
        double bonus = animalHarvestBonus();
        double merchantBonus = merchantFarmerBonus();
        int proceeds = 0;
        int count = 0;
        ArrayList<Animal> list = getAnimalsForHarvest();
        for (Animal a : list) {
            dayReportAdd(REPORT_ITEM_SEPERATOR);
            String harvestType;
            if (a.isHarvestTerminal()) {
                harvestType = " Terminal (animal slaughtered)\n";
            }
            else {
                harvestType = " Normal \n";
            }
            int assetValue = a.harvest();
            proceeds += assetValue;
            count++;
            dayReportAdd("Harvest " + a.getTypeName() + "\n" + "Harvest Type:"
                + harvestType + "\nValue: " + assetValue);
            if (a.isHarvestTerminal()) {
                reOrder(a);
                dayReportAdd(a.getTypeName() + " has been reordered.");
            }
        }
        dayReportAdd("Animal farmer bonus = " + bonus);
        dayReportAdd("Merchant farmer bonus = " + merchantBonus);
        double multiplier = bonus + merchantBonus + 1;
        double p = proceeds * multiplier;
        dayReportAdd("Harvested " + count + " animals.");
        dayReportAdd("Total animal earnings: " + p);
        farm.addMoney((int)p);
        return (int)p;
    }
       
    /**
     * Reorder the asset.
     * @param a Asset
     * @throws FarmIsBankruptException farm runs out of money
     */
    public void reOrder(Asset a) throws FarmIsBankruptException {
        farm.removeAsset(a);
        Asset newAsset = af.createAssetOfType(a);
        farm.addReorder(newAsset);
        farm.deductMoney(a.getCost());
        this.dayReportAdd(a.getTypeName() + " reordered. \nDeduct " + a.getCost());
    }
    
    /**
     * Reorder all perished assets.
     * @throws FarmIsBankruptException farm runs out of money
     */
    public void reOrderAllPerished() throws FarmIsBankruptException {
        ArrayList<Asset> assetList = farm.getAssetList();
        for (Asset a : assetList) {
            if (a instanceof Animal && a.isDead()) {
                reOrder(a);
            }
        }
        for (Asset a : assetList) {
            if (a instanceof Crop && a.isDead()) {
                reOrder(a);
            }
        }
    }
    
    /**
     * Logic to decide if farm should buy an acre.
     * @return true if yes
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
     * Logic to decide if farmer should be hired.
     * @return true if farmer should be hired
     */
    private boolean shouldHireFarmer() {
        //if farmer coverage bonus falls below 0.5
        if (farm.getFarmerCount() / farm.getAcreage() < FARMER_PURCHASE_TRIGGER) {
            dayReportAdd("Hiring new farmer:");
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * If acreage available exceeds .5
     * @return true if yes
     */
    private boolean shouldBuyAsset() {
        if (farm.getSpaceAvailable() > .5) {
            dayReportAdd("Buying new asset:");
            return true;
        }
        return false;
    }
    
    /**
     * Make random animal sick.
     * @return true if animal got sick
     */
    public boolean makeAnimalSick() {
        double result = rand.nextDouble();
        if (result <= ANIMAL_SICKNESS_CHANCE && numberOfAnimals() > 0) {
            int whichAnimal = rand.nextInt(numberOfAnimals());
            Animal a = getHealthyAnimals().get(whichAnimal); 
            a.setDiseased();
            dayReportAdd("A " + a.getTypeName() + " has become ill.");
            return true;
        }
        return false;
    }
    
    /**
     * Make random crop sick.
     * @return true if crop got sick
     */
    public boolean makeCropSick() {
        double result = rand.nextDouble();
        if (result <= CROP_SICKNESS_CHANCE && numberOfCrops() > 0) {
            int whichCrop = rand.nextInt(numberOfCrops());
            Crop c = this.getHealthyCrops().get(whichCrop); 
            c.setDiseased();
            dayReportAdd("A " + c.getTypeName() + " field has become ill.");
            return true;
        }
        return false;
    }

    /**
     * Attempt to heal animal.
     * @return true if success
     * @throws FarmIsBankruptException farm runs out of money
     */
    public boolean healAnimal() throws FarmIsBankruptException { 
        Animal a = getSickAnimal();
        double odds = ASSET_HEAL_CHANCE + veterinaryHealBonus();
        if (a != null) {
            if (rand.nextDouble() <= odds) {
                a.setAlive();
                a.setHarvestDays(DEFAULT_HARVEST_DAYS);
                dayReportAdd("Sick " + a.getTypeName() 
                    +  " has been healed.");
                return true;
            }
            a.setDead();
            dayReportAdd("Sick " + a.getTypeName() 
                + " has perished, and been reordered.");
            farm.removeAsset(a);
            reOrder(a);
            return false;
        }
        return false;
    }
    
    /**
     * Try to heal crop.
     * @return true of success
     * @throws FarmIsBankruptException farm runs out of money
     */
    public boolean healCrop() throws FarmIsBankruptException { 
        Crop c = getSickCrop();
        double odds = ASSET_HEAL_CHANCE + veterinaryHealBonus();
        if (c != null) {
            if (rand.nextDouble() <= odds) {
                c.setAlive();
                c.setHarvestDays(DEFAULT_HARVEST_DAYS);
                dayReportAdd("Sick " + c.getTypeName()
                        + " has been healed.");
                return true;
            }
            c.setDead();
            dayReportAdd("Sick " + c.getTypeName()
                    + " field has perished, and been reordered.");
            farm.removeAsset(c);
            reOrder(c);
            return false;
        }
        return false;
    }
    
    /**
     * Increase harvest price for animals.
     * @return double a % bonus for animal sale
     */
    public double animalHarvestBonus() {
        if (getAnimalsForHarvest().size() == 0) {
            return 0;
        }
        return (double)numberOfAnimalFarmers()
                / (double) getAnimalsForHarvest().size() / 25.0;
    }
    
    /**
     * Increase harvest price for crops.
     * @return double a % bonus for crop sale
     */
    private double cropHarvestBonus() {
        if (getCropsForHarvest().size() == 0) {
            return 0.0;
        }
        return (double)numberOfCropFarmers()
                / (double)getCropsForHarvest().size() / 25.0;
    }
       
    /**
     * Increase odds of animal survival when healing.
     * @return the bonus
     */
    public double veterinaryHealBonus() {
        if (numberOfAnimals() == 0) {
            return 0.0;
        }
        return (double)numberOfVeterinaryFarmers()
                / (double)numberOfAnimals() / 10.0;
    }
    
    /**
     * Increase money earned from selling.
     * @return a % bonus for merchant farmer bonus
     */
    private double merchantFarmerBonus() {
        return numberOfMerchantFarmers() * .04; 
    }
    
    /**
     * Returns number of living animals on the farm.
     * @return
     */
    private int numberOfAnimals() {
        int num = 0;
        ArrayList<Animal> list = getAliveAnimals();
        for (Asset a : list) {
            if (a.isAlive()) {
                num++;
            }
        }
        return num;
    }
    
    /**
     * Returns the number of living crops on the farm.
     * @return
     */
    private int numberOfCrops() {
        int num = 0;
        ArrayList<Crop> list = getAliveCrops();
        for (Asset a : list) {
            if (a.isAlive()) {
                num++;
            }
        }
        return num;
    }

    /**
     * Returns a list of animals that are alive.
     * @return ArrayList of Animal
     */
    private ArrayList<Animal> getAliveAnimals() {
        ArrayList<Asset> list = farm.getAssetList();
        ArrayList<Animal> outputList = new ArrayList<Animal>();
        for (Asset a : list) {
            if (a instanceof Animal) {
                if (a.isAlive()) {
                    outputList.add((Animal) a);
                }
            }
        }
        return outputList;
    }
    
    /**
     * Returns a list of Crops that are alive.
     * @return list of crops
     */
    private ArrayList<Crop> getAliveCrops() {
        ArrayList<Asset> list = farm.getAssetList();
        ArrayList<Crop> outputList = new ArrayList<Crop>();
        for (Asset a : list) {
            if (a instanceof Crop) {
                if (a.isAlive()) {
                    outputList.add((Crop) a);
                }
            }
        }
        return outputList;
    }
    
    /**
     * Returns the number of farmers that specialize in animals.
     * @return num of animal farmers
     */
    private int numberOfAnimalFarmers() {
        int num = 0;
        ArrayList<Farmer> list = farm.getFarmerList();
        for (Farmer f : list) {
            if (f.getFarmerKind().equals(FarmerKind.ANIMAL)) {
                num++;
            }
        }
        return num;
    }
    
    /**
     * Returns the number of farmers that specialize in crops.
     * @return
     */
    private int numberOfCropFarmers() {
        int num = 0;
        ArrayList<Farmer> list = farm.getFarmerList();
        for (Farmer f : list) {
            if (f.getFarmerKind().equals(FarmerKind.CROPS)) {
                num++;
            }
        }
        return num;
    }
    
    /**
     * Returns the number of veterinarians working on the farm.
     * @return farmers that are vets
     */
    public int numberOfVeterinaryFarmers() {
        int num = 0;
        ArrayList<Farmer> list = farm.getFarmerList();
        for (Farmer f : list) {
            if (f.getFarmerKind().equals(FarmerKind.VETERINARY)) {
                num++;
            }
        }
        return num;
    }
    
    /**
     * Returns the number of merhchant farmers working on the farm.
     * @return number of farmers that are merchants
     */
    private int numberOfMerchantFarmers() {
        int num = 0;
        ArrayList<Farmer> list = farm.getFarmerList();
        for (Farmer f : list) {
            if (f.getFarmerKind().equals(FarmerKind.MERCHANT)) {
                num++;
            }
        }
        return num;
    }
    
    /**
     * Sets reports up for a new cycle.
     */
    private void resetCycleReports() {
        String dayInit = REPORT_STAR_SEPERATOR + REPORT_STAR_SEPERATOR 
                + "\n" + "DayReport:\n\n";
        dayReport = new StringBuilder(dayInit);
    }
    
    /**
     * Add a String to the day report.
     * @param appendation String add to report
     */
    public void dayReportAdd(String appendation) {
        dayReport.append(appendation + "\n");
    }
    
    /**
     * Resets the day report for tomorrow.
     */
    private void resetDayReport() {
        dayReport = new StringBuilder();
    }
    
    /**
     * Returns day report as sting and resets it for another cycle.
     * @return String day report
     */
    public String reportDay() {
        String dr = dayReport.toString();
        resetDayReport();
        return dr;
    }
    
    /**
     * Provides list of crops ready for harvest.
     * @return ArrayList of Crops
     */
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
    
    /**
     * Provides list of animals that are ready to harvest.
     * @return ArrayList of Animals
     */
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
    
    /**
     * If there is a sick animal, return it.
     * @return Animal that is sick
     */
    private Animal getSickAnimal() {
        ArrayList<Asset> al = farm.getAssetList();
        for (Asset a : al) {
            if (a instanceof Animal && a.isDiseased()) {
                return (Animal) a;
            }
        }
        return null;
    }
    
    /**
     * If there is a sick crop, return it.
     * @return Animal that is sick
     */
    private Crop getSickCrop() {
        ArrayList<Asset> al = farm.getAssetList();
        for (Asset a : al) {
            if (a instanceof Crop && a.isDiseased()) {
                return (Crop) a;
            }
        }
        return null;
    }
    
    /**
     * Check each animals age and move to dead if over 14 days.
     * Reorder replacement
     */
    public void checkAnimalAge() {
        ArrayList<Animal> list = getAliveAnimals();
        for (Animal a : list) {
            if (a.getAge() >= a.AGE_TO_DIE) {
                a.setDead();
                dayReportAdd("A " + a.getTypeName() 
                        + " has died of old age. A replacement will be reordered");
            }
        }
    }
    
    /**
     * Return a list of crops that are not sick and are alive.
     * @return
     */
    public ArrayList<Crop> getHealthyCrops() {
        ArrayList<Asset> list = farm.getAssetList();
        ArrayList<Crop> outputList = new ArrayList<Crop>();
        for (Asset a : list) {
            if (a instanceof Crop && a.isHealthy()) {
                outputList.add((Crop) a);
            }
        }
        return outputList;
    }
    
    /**
     * Return a list of crops that are not sick and are alive.
     * @return
     */
    public ArrayList<Animal> getHealthyAnimals() {
        ArrayList<Asset> list = farm.getAssetList();
        ArrayList<Animal> outputList = new ArrayList<Animal>();
        for (Asset a : list) {
            if (a instanceof Animal && a.isHealthy()) {
                outputList.add((Animal) a);
            }
        }
        return outputList;
    }
    
    
    /**
     * Returns indicator is the simulation should continue.
     * @return indicator if sim should go on
     */
    public boolean isGameOn() {
        return gameOn;
    }
    
    /**
     * Increment the age of each living asset.
     */
    public void incrementDay() {
        ArrayList<Asset> list = farm.getAssetList();
        for (Asset a : list) {
            if (a.isAlive()) {
                a.incrementDay();
            }
        }
        cycles++;
    }
    
    /**
     * Animals over the threshold die of old age.
     * @throws FarmIsBankruptException if farm is out of money
     */
    private void clearOlderAnimals() throws FarmIsBankruptException {
        ArrayList<Animal> list = getAliveAnimals();
        for (Animal a : list) {
            if (a.getAge() >= Animal.AGE_TO_DIE) {
                this.dayReportAdd("A " + a.getTypeName() + "has died of old age. " +
                        "a replacement has been ordered.");
                reOrder(a);
            }
        }
        
    }
}
