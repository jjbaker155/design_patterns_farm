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
    //number of cycles before simulation ends
    public final static int MAX_CYCLES = 25;
    //acreage to win
    public final static double MAX_ACREAGE = 100.0;
    //money to win
    public final static int MAX_MONEY = 10000;
    //money to start with
    public final static int INITIAL_MONEY = 2000;
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
    //chance animal will get sick
    private final double ANIMAL_SICKNESS_CHANCE = 0.70;
    //chance crop will get sick
    private final double CROP_SICKNESS_CHANCE = 0.60;
    //chance of healing asset
    private final double ASSET_HEAL_CHANCE = 0.15;
    //for report building
    private final String REPORT_ITEM_SEPERATOR = "-------------------";
    private final String REPORT_STAR_SEPERATOR = "*****************" + 
    "*************";
    
    private int cycles;
    
    private static Random rand = new Random();
    private Farm farm;
    private FarmerControl farmerControl;
    private int day;
    
    private StringBuilder dayReport;
    private StringBuilder nightReport;
    
    private AssetFactory af;
    
    //the only farm control - singleton
    private static FarmControl farmControlSoleInstance;
    
    private boolean gameOn;
    
    /**
     * Private constructor
     */
    private FarmControl() {
        farm = Farm.makeFarm();
        af = AssetFactory.makeAssetFactory();
        day = 0;
        resetCycleReports();
        gameOn = true;
        cycles = 0;
    }
    
    /**
     * Private constructor for making test FarmControl
     * @param boolean to indicate using this constructor
     */
    private FarmControl(boolean test) {
        farm = Farm.makeTestFarm();
        af = AssetFactory.makeAssetFactory();
        day = 0;
        resetCycleReports();
        gameOn = true;
        cycles = 0;
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
            try {
                farmControlSoleInstance.generateInitialAssets();
            } catch (AssetAlreadyDeadException e) {
                e.printStackTrace();
            }
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
     * "Pull yourself up by your bootstraps!"
     */
    private void attachFarmerControl() {
        if (farmerControl == null)
            farmerControl = FarmerControl.createFarmerControl();
    }
    
    /**
     * Generate 3 random farmer types for your farm
     * @param farm
     */
    private void generateInitialFarmers() {
        dayReportAdd("Initial Farmer Hires:");
        dayReportAdd(REPORT_ITEM_SEPERATOR);
        for (int i = 0; i <= INITIAL_FARMERS - 1; i++) {
            hireRandomFarmer();
        }
        dayReportAdd("\n");
    }
    
    /**
     * Generate initial assets for your farm
     * @throws AssetAlreadyDeadException 
     */
    public void generateInitialAssets() throws AssetAlreadyDeadException {
        dayReportAdd("Initial Asset Purchase:");
        dayReportAdd(REPORT_ITEM_SEPERATOR);
        for (int i = 0; i < INITIAL_ASSETS; i++) {
            purchaseRandomAsset();
        }
        dayReportAdd("\n");
    }
    
    /**
     * Adds a farmer (random type)
     * @param Farm to add a Farmer to
     */
    public void hireRandomFarmer() {
        Farmer f = farmerControl.randomFarmer();
        farm.addFarmer(f);
        dayReportAdd("Hired new " + f.getTypeName() + " farmer.");
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
        this.dayReportAdd(a.getTypeName() + " purchased. \nDeduct " + a.getCost());
        farm.deductMoney(a.getCost());
    }
    
    
    /**
     * Run through Day Sequence
     * @return report
     * @throws AssetAlreadyDeadException
     */
    public String runDay() throws AssetAlreadyDeadException {
        //todo: add some summary to day report
        //todo: place try/catch in methods where it will be useful
        harvestCrops();
        harvestAnimals();
        reOrderAllPerished();
        healAnimal();
        healCrop();
        payFarmers();
        if(shouldHireFarmer()) {
            hireRandomFarmer();
        }
        if(shouldBuyAsset()) {
            //todo: place try/catch in methods where it will be more useful
            try {
                purchaseRandomAsset();
            } catch (AssetAlreadyDeadException e) {
                System.out.println("Asset is already dead. **back to unit testing**");
            }
        }
        if(shouldBuyAcre()) {
            buyAcre();
        }
        cycles++;
        if (farm.getMoney() > MAX_MONEY) {
            dayReportAdd("\nReached money goal. Farm has succeded.");
            gameOn = false;
        }
        if (farm.getMoney() < 0) {
            dayReportAdd("\nBankrupt. Farm has failed.");
            gameOn = false;
        }
        if (farm.getAcreage() >= MAX_ACREAGE) {
            dayReportAdd("\nReached land goal. Farm has succeded.");
            gameOn = false;
        }
        if (cycles >= MAX_CYCLES) {
            dayReportAdd("\nReached cycle max. Simulation ended inconclusively.");
            gameOn = false;
        }
        return dayReport.toString();
    }
    
    /**
     * Run through Night Sequence
     * @return String report
     * @throws AssetAlreadyDeadException
     */
    public String runNight() {
        //todo: place try/catch in methods where it will be more useful
        try {
            makeAnimalSick();
            makeCropSick();
        } catch (AssetAlreadyDeadException e) {
            System.out.println("Asset is already dead. **back to unit testing**");
        }
        return nightReport.toString();
    }
    
    /**
     * Pay the farmer for their work on the farm
     */
    public void payFarmers() {
        int payRoll = farm.getFarmerCount() * FARMER_PER_DIEM;
        dayReportAdd("\n" + farm.getFarmerCount() + " farmers paid.\nTotal: " + payRoll);
        farm.deductMoney(payRoll);
    }
    
    /**
     * Returns the Farm object
     * @return Farm
     */
    public Farm getFarm() {
        return farm;
    }
    
    /**
     * Purchase an acre of land
     */
    public void buyAcre() {
        farm.deductMoney(ACRE_COST);
        farm.addAcre();
    }
  
    /**
     * Harvest all crops that qualify and return the proceeds
     * @return int the proceeds
     * @throws AssetAlreadyDeadException
     */
    public int harvestCrops() throws AssetAlreadyDeadException {
        dayReportAdd("Crop Harvest:\n" + REPORT_ITEM_SEPERATOR);
        double bonus = cropHarvestBonus();
        double merchantBonus = merchantFarmerBonus();
        int proceeds = 0;
        int count = 0;
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
            count++;
            dayReportAdd("Harvest " + c.getTypeName() + "\n" + "Harvest Type:"
            + harvestType + "Value: " + assetValue);
        }
        dayReportAdd("Crop farmer harvest bonus = " + bonus);
        dayReportAdd("Merchant farmer harvest bonus = " + merchantBonus);
        double multiplier = bonus + merchantBonus + 1.0;
        double p = proceeds * multiplier;
        dayReportAdd("Total crop earnings: " + p + "\n");
        return (int) p;
    }
    
    /**
     * Harvest all animals that qualify and return the proceeds
     * @return int the proceeds
     * @throws AssetAlreadyDeadException
     */
    public int harvestAnimals() throws AssetAlreadyDeadException {
        dayReportAdd("Animal Harvest:\n" + REPORT_ITEM_SEPERATOR);
        double bonus = animalHarvestBonus();
        double merchantBonus = merchantFarmerBonus();
        int proceeds = 0;
        int count = 0;
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
            count++;
            dayReportAdd("Harvest " + a.getTypeName() + "\n" + "Harvest Type:"
            + harvestType + "(animal slaughtered)\nValue: " + assetValue);
        }
        dayReportAdd("Animal farmer bonus = " + bonus);
        dayReportAdd("Merchant farmer bonus = " + merchantBonus);
        double multiplier = bonus + merchantBonus + 1;
        double p = proceeds * multiplier;
        dayReportAdd("Total animal earnings: " + p);
        return (int) p;
    }
       
    /**
     * Reorder the animal. More formally, it sets it to alive and deducts the cost
     * @param a
     */
    public void reOrder(Asset a) {
        a.setAliveNewAsset();
        this.dayReportAdd(a.getTypeName() + " reordered. \nDeduct " + a.getCost());
        farm.deductMoney(a.getCost());
    }
    
    /**
     * Reorder all perished assets
     */
    public void reOrderAllPerished() {
        ArrayList<Asset> assetList = farm.getAssetList();
        for(Asset a : assetList) {
            if(a instanceof Animal && a.isDead())
                reOrder(a);
        }
        for(Asset a : assetList) {
            if(a instanceof Crop && a.isDead())
                reOrder(a);
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
        if (farm.getFarmerCount()/farm.getAcreage() < 0.4) {
            dayReportAdd("Hiring new farmer:");
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
            dayReportAdd("Buying new asset:");
            return true;
        }
        return false;
    }
    
    /**
     * Make random animal sick
     * @return true if animal got sick
     * @throws AssetAlreadyDeadException if animal is dead
     */
    public boolean makeAnimalSick() throws AssetAlreadyDeadException {
        double result = rand.nextDouble();
        if (result <= ANIMAL_SICKNESS_CHANCE && numberOfAnimals() > 0) {
            int whichAnimal = rand.nextInt(numberOfAnimals());
            Animal a = getAliveAnimals().get(whichAnimal); 
            a.setDiseased();
            nightReportAdd("A " + a.getTypeName() + " has become ill.");
            return true;
        }
        return false;
    }
    
    /**
     * Make random crop sick
     * @return true if crop got sick
     * @throws AssetAlreadyDeadException if animal is dead
     */
    public boolean makeCropSick() throws AssetAlreadyDeadException {
        double result = rand.nextDouble();
        if (result <= CROP_SICKNESS_CHANCE && numberOfCrops() > 0) {
            int whichCrop = rand.nextInt(numberOfCrops());
            Crop c = this.getAliveCrops().get(whichCrop); 
            c.setDiseased();
            nightReportAdd("A " + c.getTypeName() + " field has become ill.");
            return true;
        }
        return false;
    }
    
    /**
     * Attempts to heal an animal
     * @return
     * @throws AssetAlreadyDeadException
     */
    public boolean healAnimal() throws AssetAlreadyDeadException { 
        Animal a = getSickAnimal();
        double odds = ASSET_HEAL_CHANCE + veterinaryHealBonus();
        if(a != null) {
            if(rand.nextDouble() <= ASSET_HEAL_CHANCE) {
                a.setAlive();
                dayReportAdd("Sick " + a.getTypeName() + 
                        " has been healed.");
                return true;
            }
            a.setDead();
            dayReportAdd("Sick " + a.getTypeName() + 
                    " has perished.");
            return false;
        }
        return false;
    }
    
    /**
     * Attempts to heal an crop
     * @return
     * @throws AssetAlreadyDeadException
     */
    public boolean healCrop() throws AssetAlreadyDeadException { 
        Crop c = getSickCrop();
        double odds = ASSET_HEAL_CHANCE + veterinaryHealBonus();
        if(c != null) {
            if(rand.nextDouble() <= ASSET_HEAL_CHANCE) {
                c.setAlive();
                dayReportAdd("Sick " + c.getTypeName() + 
                        " has been healed.");
                return true;
            }
            c.setDead();
            dayReportAdd("Sick " + c.getTypeName() + 
                    " has perished.");
            return false;
        }
        return false;
    }
    
    /**
     * Increase harvest price for animals 
     * @return double a % bonus for animal sale
     */
    private double animalHarvestBonus() {
        if(getAnimalsForHarvest().size() == 0) {
            return 0;
        }
        return numberOfAnimalFarmers() / getAnimalsForHarvest().size() / 25.0;
    }
    
    /**
     * Increase harvest price for crops
     * @return double a % bonus for crop sale
     */
    private double cropHarvestBonus() {
        if (getCropsForHarvest().size() == 0) {
            return 0.0;
        }
        return (numberOfCropFarmers() / getCropsForHarvest().size()) / 25.0;
    }
       
    /**
     * Increase odds of animal survival when healing
     * @return
     */
    private double veterinaryHealBonus() {
        if (numberOfAnimals() == 0) {
            return 0.0;
        }
        return numberOfVeterinaryFarmers() / numberOfAnimals() / 10.0;
    }
    
    /**
     * Increase odds of healing crops
     * @return
     */
    private double cropHealBonus() {
        if(numberOfCrops() == 0) {
            return 0.0;
        }
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
        ArrayList<Animal> list = getAliveAnimals();
        for(Asset a : list) {
            if(a.isAlive()) {
                num++;
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
        ArrayList<Crop> list = getAliveCrops();
        for(Asset a : list) {
            if(a.isAlive()) {
                num++;
            }
        }
        return num;
    }
    
    /**
     * Returns a list of animals that are alive
     * @return ArrayList<Animal>
     */
    private ArrayList<Animal> getAliveAnimals() {
        ArrayList<Asset> list = farm.getAssetList();
        ArrayList<Animal> outputList = new <Animal>ArrayList();
        for(Asset a : list) {
            if (a instanceof Animal) {
                if(a.isAlive()) {
                    outputList.add((Animal) a);
                }
            }
        }
        return outputList;
    }
    
    /**
     * Returns a list of crops that are alive
     * @return ArrayList<Crop>
     */
    private ArrayList<Crop> getAliveCrops() {
        ArrayList<Asset> list = farm.getAssetList();
        ArrayList<Crop> outputList = new <Crop> ArrayList();
        for(Asset a : list) {
            if(a instanceof Crop) {
                if(a.isAlive()) {
                    outputList.add((Crop) a);
                }
            }
        }
        return outputList;
    }
    
    /**
     * Returns the number of farmers that specialize in animals
     * @return
     */
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
    
    /**
     * Returns the number of farmers that specialize in crops
     * @return
     */
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
    
    /**
     * Returns the number of veterinarians working on the farm
     * @return farmers that are vets
     */
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
    
    /**
     * Returns the number of merhchant farmers working on the farm
     * @return number of farmers that are merchants
     */
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
     * Add a String to the day report
     * @param String to add to report
     */
    public void dayReportAdd(String appendation) {
        dayReport.append(appendation + "\n");
    }
    
    
    /**
     * Add a string to the night report
     * @param String to add to report
     */
    public void nightReportAdd(String appendation) {
        nightReport.append(appendation + "\n");
    }
    
    /**
     * Resets the day report for tomorrow
     */
    private void resetDayReport() {
        dayReport = new StringBuilder();
    }
    
    /**
     * Resets the night report for tomorrow
     */
    private void resetNighReport() {
        nightReport = new StringBuilder();
    }
    
    /**
     * Provides list of crops ready for harvest
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
     * Provides list of animals that are ready to harvest
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
     * If there is a sick animal, return it
     * @return Animal that is sick
     */
    private Animal getSickAnimal() {
        ArrayList<Asset> al = farm.getAssetList();
        for(Asset a : al) {
            if (a instanceof Animal && a.isDiseased()) {
                return (Animal) a;
            }
        }
        return null;
    }
    
    /**
     * If there is a sick crop, return it
     * @return Animal that is sick
     */
    private Crop getSickCrop() {
        ArrayList<Asset> al = farm.getAssetList();
        for(Asset a : al) {
            if (a instanceof Crop && a.isDiseased()) {
                return (Crop) a;
            }
        }
        return null;
    }
    
    /**
     * Check each animals age and move to dead if over 14 days
     * Reorder replacement
     */
    private void checkAnimalAge() {
        ArrayList<Animal> list = getAliveAnimals();
        for (Animal a : list) {
            if (a.getAge() >= a.AGE_TO_DIE) {
                try {
                    a.setDead();
                    dayReportAdd("A " + a.getTypeName() + 
                            " has died of old age. A replacement will be reordered");
                } catch (AssetAlreadyDeadException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Returns indicator is the simulation should continue.
     * @return indicator if sim should go on
     */
    public boolean isGameOn() {
        return gameOn;
    }
        
}
