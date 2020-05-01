package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.java.Asset;
import main.java.AssetFactory;
import main.java.Cattle;
import main.java.Corn;
import main.java.Farm;
import main.java.FarmControl;
import main.java.FarmHasWonException;
import main.java.FarmIsBankruptException;
import main.java.Farmer;
import main.java.FarmerControl;
import main.java.FarmerControl.FarmerKind;
import main.java.Hog;
import main.java.Sheep;
import main.java.Soy;
import main.java.StateAlive;
import main.java.StateDead;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmControlTest {

    private FarmControl farmControl;
    private Farm farm;
    private AssetFactory af = AssetFactory.makeAssetFactory();
    private FarmerControl farmerControl;
    
    @Before
    public void setup() {
        farmerControl = FarmerControl.createTestFarmerControl();
        farmControl = FarmControl.createTestFarmControl();
        farm = farmControl.getFarm();
        
    }

    /**
     * After init of FarmControl, there should be 3 farmers
     */
    @Test
    public void test1InitialFarmersCount() {
        assertEquals(0, farm.getFarmerCount());
    }
    
    /**
     * Starts with 3 farmers, should have 4 after this test
     */
    @Test
    public void test2HireRandomFarmer() {
        farmControl.hireRandomFarmer();
        assertEquals(1, farm.getFarmerCount());
    }
    
    /**
     * Test to see if initial assets are generated properly
     * Should have 4 assets after this test
     * @throws AssetAlreadyDeadException 
     */
    @Test
    public void test3GenerateInitialAssets() {
        farmControl.generateInitialAssets();
        ArrayList<Asset> assetList = farmControl.getFarm().getAssetList();
        assertTrue(assetList.get(FarmControl.INITIAL_ASSETS - 1) instanceof Asset);
    }
    
    /**
     * Test to see if purchase a random asset works
     * Should have 5 assets after this test
     * @throws FarmIsBankruptException 
     * @throws AssetAlreadyDeadException 
     */
    @Test
    public void test4purchaseRandomAsset() throws FarmIsBankruptException {
        farmControl.purchaseRandomAsset();
        ArrayList<Asset> assetList = farmControl.getFarm().getAssetList();
        assertTrue(assetList.get(0) instanceof Asset);
    }
    
    /**
     * Test to see if buyAcre() works.
     * Farm size should be 2.0 after this test
     * @throws FarmIsBankruptException 
     */
    @Test
    public void test5buyAcre() throws FarmIsBankruptException {
        farmControl.buyAcre();
        assertTrue(farm.getAcreage() == 2.0);
    }
    
    /**
     * Test harvesting one crop asset
     * @throws FarmHasWonException 
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test8HarvestCrops() throws FarmHasWonException {
        Corn corn = (Corn) af.createAsset(4);
        corn.setAge(3);
        corn.setHarvestDays(0);
        farm.addAsset(corn);
        int earnings = farmControl.harvestCrops();
        assertTrue(earnings > 0);
    }
    
    /**
     * Test harvest one animal
     * @throws FarmHasWonException 
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test9HarvestAnimals() throws FarmHasWonException {
        Cattle cattle = (Cattle) af.createAsset(0);
        cattle.setAge(3);
        cattle.setHarvestDays(0);
        farm.addAsset(cattle);
        int earnings = farmControl.harvestAnimals();
        assertTrue(earnings > 0);
    }
    
    /**
     * Test harvest wool
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test10HarvestWool() {
        Sheep sheep = (Sheep) af.createAsset(2);
        sheep.setAge(3);
        sheep.setHarvestDays(0);
        farm.addAsset(sheep);
        assertTrue(sheep.getStateContext().getState() instanceof StateAlive);
    }
    
    /**
     * Tests the harvest of multiple crops but only if they qualify
     * @throws FarmHasWonException 
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test12HarvestMultipleCrops() throws FarmHasWonException {
        Cattle cattle1 = (Cattle) af.createAsset("cattle"); 
        Hog hog1 = (Hog) af.createAsset("hog");
        
        Corn corn1 = (Corn) af.createAsset("corn");
        Corn corn2 = (Corn) af.createAsset("corn");
        
        Soy soy1 = (Soy) af.createAsset("soy");
        
        //adds 0 to harvest
        farm.addAsset(cattle1);
        
        farm.addAsset(hog1);
        hog1.setHarvestDays(0); 
        
        
        farm.addAsset(corn1);
        corn1.setHarvestDays(0);
        
        //adds 0 to harvest
        farm.addAsset(corn2);
        corn2.setHarvestDays(1);
        
        
        farm.addAsset(soy1);
        soy1.setHarvestDays(0);
        
        //Corn.PROFIT + Soy.PROFIT
        assertEquals(Corn.PROFIT + Soy.PROFIT, farmControl.harvestCrops());
    }
    
    /**
     * Tests the harvest of multiple Animals but only of they qualify
     * @throws FarmHasWonException 
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test13HarvestMultipleAnimals() throws FarmHasWonException {
        Cattle cattle1 = (Cattle) af.createAsset("cattle"); 
        Hog hog1 = (Hog) af.createAsset("hog");
        Sheep sheep1 = (Sheep) af.createAsset("sheep");
        Sheep sheep2 = (Sheep) af.createAsset("sheep");
        
        Corn corn1 = (Corn) af.createAsset("corn");
        Corn corn2 = (Corn) af.createAsset("corn");
        
        Soy soy1 = (Soy) af.createAsset("soy");
        
        
        farm.addAsset(cattle1);
        cattle1.setHarvestDays(0);
        
        
        farm.addAsset(hog1);
        hog1.setHarvestDays(0); 
        
        
        farm.addAsset(sheep1);
        sheep1.setHarvestDays(0);
        
        //adds 0 to harvest
        farm.addAsset(sheep2);
        sheep2.setHarvestDays(1);
        
        //adds 0 to harvest
        farm.addAsset(corn1);
        corn1.setHarvestDays(0);
        
        //adds 0 to harvest
        farm.addAsset(corn2);
        corn2.setHarvestDays(1);
        
        //adds 0 to harvest
        farm.addAsset(soy1);
        soy1.setHarvestDays(0);
        
        //Sheep.PROFIT + Hog.PROFIT + Cattle.PROFIT
        assertEquals(Sheep.PROFIT + Hog.PROFIT + Cattle.PROFIT, farmControl.harvestAnimals());
    }
    
    
    /**
     * Test the merchant farmer bonus for the sale of animals
     * @throws FarmHasWonException 
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test14MerchantBonus() throws FarmHasWonException {
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        Cattle cattle1 = (Cattle) af.createAsset("cattle");
        
        
        //adds 725 to harvest
        farm.addAsset(cattle1);
        cattle1.setHarvestDays(0);
        
        //3 merchants -> expected merchant bonus is .12
        //expected harvest: Cattle.PROFIT * 1.12 = 812
        
        assertTrue(Cattle.PROFIT * 1.12 == farmControl.harvestAnimals());
    }
    
    /**
     * Test the merchant farmer bonus for the sale of crops
     * @throws FarmHasWonException 
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test15MerchantBonus() throws FarmHasWonException {
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        Corn corn1 = (Corn) af.createAsset("corn");
        
        
        farm.addAsset(corn1);
        corn1.setHarvestDays(0);
        
        //3 merchants -> expected merchant bonus is .08
        //expected harvest: Corn.PROFIT * 1.08
        
        assertTrue(Corn.PROFIT * 1.08 == farmControl.harvestCrops());
    }
    
    /**
     * Test the crop farmer bonus for the sale of crops
     * @throws FarmHasWonException 
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test16CropBonus() throws FarmHasWonException {
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.CROPS));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.CROPS));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.CROPS));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.ANIMAL));
        Corn corn1 = (Corn) af.createAsset("corn");
        
        
        farm.addAsset(corn1);
        corn1.setHarvestDays(0);
        
        //3 crop farmers, 1 crop -> expected crops bonus is .12
        //expected harvest: Corn.PROFIT * 1.12 = 728
        
        
        assertTrue(Corn.PROFIT * 1.12 == farmControl.harvestCrops());
    }
    
    /**
     * Test the crop farmer bonus for the sale of crops
     * @throws FarmHasWonException 
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test17AnimalBonus() throws FarmHasWonException {
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.ANIMAL));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.ANIMAL));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.CROPS));
        Cattle cattle1 = (Cattle) af.createAsset("cattle");
        
        farm.addAsset(cattle1);
        cattle1.setHarvestDays(0);
        
        //2 animal farmers, 2 animals -> expected animal bonus is 0.04
        //expected harvest: Cattle.PROFIT * 1.04 
        //(animalHarvestBonus() + 1) * Cattle.PROFIT 
        
        assertTrue((farmControl.animalHarvestBonus() + 1.0) * Cattle.PROFIT == 
                farmControl.harvestAnimals());
    }
    
    @Test
    public void test18runDay() throws FarmHasWonException, FarmIsBankruptException {
        farmControl.runDay();
    }

    @Test
    public void test20IncrementAnimalAge() {
        Cattle cattle1 = (Cattle) af.createAsset("cattle");
        Cattle cattle2 = (Cattle) af.createAsset("cattle");
        Corn corn1 = (Corn) af.createAsset("corn");
        
        farm.addAsset(cattle1);
        farmControl.incrementDay();
        
        farm.addAsset(cattle2);
        farmControl.incrementDay();
        
        farm.addAsset(corn1);
        
        assertTrue(cattle1.getAge() ==  2 && cattle2.getAge() == 1 && corn1.getAge() == 0);
        
    } 
    
    
}
