package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import exceptions.AssetAlreadyDeadException;
import main.java.Asset;
import main.java.AssetFactory;
import main.java.Cattle;
import main.java.Corn;
import main.java.Farm;
import main.java.FarmControl;
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

    //private FarmControl farmControl;
    //private FarmerControl farmerControl;
    private FarmControl farmControl = FarmControl.createFarmControl();
    private Farm farm = farmControl.getFarm();
    private AssetFactory af = AssetFactory.makeAssetFactory();
    private FarmerControl farmerControl;
    
    @Before
    public void setup() {
        farmControl = FarmControl.createTestFarmControl();
        farm = farmControl.getFarm();
        farmerControl = FarmerControl.createTestFarmerControl();
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
    public void test3GenerateInitialAssets() throws AssetAlreadyDeadException {
        farmControl.generateInitialAssets();
        ArrayList<Asset> assetList = farmControl.getFarm().getAssetList();
        assertTrue(assetList.get(FarmControl.INITIAL_ASSETS - 1) instanceof Asset);
    }
    
    /**
     * Test to see if purchase a random asset works
     * Should have 5 assets after this test
     * @throws AssetAlreadyDeadException 
     */
    @Test
    public void test4purchaseRandomAsset() throws AssetAlreadyDeadException {
        farmControl.purchaseRandomAsset();
        ArrayList<Asset> assetList = farmControl.getFarm().getAssetList();
        assertTrue(assetList.get(0) instanceof Asset);
    }
    
    /**
     * Test to see if buyAcre() works.
     * Farm size should be 2.0 after this test
     */
    @Test
    public void test5buyAcre() {
        farmControl.buyAcre();
        assertTrue(farm.getAcreage() == 2.0);
    }
    
    /**
     * Test to see if the state of an asset changes to StateAlive
     * after the asset is re-ordered
     * @throws AssetAlreadyDeadException 
     */
    @Test
    public void test6reOrderState() throws AssetAlreadyDeadException {
        farmControl.generateInitialAssets();
        Asset a = farm.getAssetByIndex(0);
        a.getStateContext().setState(new StateDead());
        farmControl.reOrder(a);
        assertTrue(a.getStateContext().getState() instanceof StateAlive);
    }
    
    /**
     * Test to ensure re-ordering an asset will work
     * @throws AssetAlreadyDeadException 
     */
    @Test
    public void test7reOrderAge() throws AssetAlreadyDeadException {
        farmControl.generateInitialAssets();
        Asset a = farm.getAssetByIndex(0);
        a.getStateContext().setState(new StateDead());
        a.setAge(2);
        farmControl.reOrder(a);
        assertEquals(a.getAge(), 0);
    }
    
    /**
     * Test harvesting one crop asset
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test8HarvestCrops() throws AssetAlreadyDeadException {
        Corn corn = (Corn) af.createAsset(4);
        corn.setAge(3);
        corn.setHarvestDays(0);
        farm.addAsset(corn);
        int earnings = farmControl.harvestCrops();
        assertTrue(earnings > 0);
    }
    
    /**
     * Test harvest one animal
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test9HarvestAnimals() throws AssetAlreadyDeadException {
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
    public void test10HarvestWool() throws AssetAlreadyDeadException {
        Sheep sheep = (Sheep) af.createAsset(2);
        sheep.setAge(3);
        sheep.setHarvestDays(0);
        farm.addAsset(sheep);
        int earnings = farmControl.harvestAnimals();
        assertTrue(sheep.getStateContext().getState() instanceof StateAlive);
    }
    
    /**
     * Test asset already dead exception
     * @throws AssetAlreadyDeadException
     */
    @Test (expected = AssetAlreadyDeadException.class)
    public void test11HarvestDeadAsset() throws AssetAlreadyDeadException {
        Asset asset = af.createAsset(0);
        asset.setDead();
        asset.harvest();
    }
    
    /**
     * Tests the harvest of multiple crops but only if they qualify
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test12HarvestMultipleCrops() throws AssetAlreadyDeadException {
        Cattle cattle1 = (Cattle) af.createAsset("cattle"); 
        Hog hog1 = (Hog) af.createAsset("hog");
        
        Corn corn1 = (Corn) af.createAsset("corn");
        Corn corn2 = (Corn) af.createAsset("corn");
        
        Soy soy1 = (Soy) af.createAsset("soy");
        
        //adds 0 to harvest
        farm.addAsset(cattle1);
        //adds 0 to harvest
        farm.addAsset(hog1);
        hog1.setHarvestDays(0); 
        
        //adds 650 to harvest
        farm.addAsset(corn1);
        corn1.setHarvestDays(0);
        
        //adds 0 to harvest
        farm.addAsset(corn2);
        corn2.setHarvestDays(1);
        
        //adds 550 to harvest
        farm.addAsset(soy1);
        soy1.setHarvestDays(0);
        
        assertEquals(1200, farmControl.harvestCrops());
    }
    
    /**
     * Tests the harvest of multiple Animals but only of they qualify
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test13HarvestMultipleAnimals() throws AssetAlreadyDeadException {
        Cattle cattle1 = (Cattle) af.createAsset("cattle"); 
        Hog hog1 = (Hog) af.createAsset("hog");
        Sheep sheep1 = (Sheep) af.createAsset("sheep");
        Sheep sheep2 = (Sheep) af.createAsset("sheep");
        
        Corn corn1 = (Corn) af.createAsset("corn");
        Corn corn2 = (Corn) af.createAsset("corn");
        
        Soy soy1 = (Soy) af.createAsset("soy");
        
        //adds 725 to harvest
        farm.addAsset(cattle1);
        cattle1.setHarvestDays(0);
        
        //adds 225 to harvest
        farm.addAsset(hog1);
        hog1.setHarvestDays(0); 
        
        //adds 175 to harvest
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
        
        assertEquals(1125, farmControl.harvestAnimals());
    }
    
    
    /**
     * Test the merchant farmer bonus for the sale of animals
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test14MerchantBonus() throws AssetAlreadyDeadException {
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        Cattle cattle1 = (Cattle) af.createAsset("cattle");
        
        
        //adds 725 to harvest
        farm.addAsset(cattle1);
        cattle1.setHarvestDays(0);
        
        //3 merchants -> expected merchant bonus is .12
        //expected harvest: 725 * 1.12 = 812
        
        assertEquals(812, farmControl.harvestAnimals());
    }
    
    /**
     * Test the merchant farmer bonus for the sale of crops
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test15MerchantBonus() throws AssetAlreadyDeadException {
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.MERCHANT));
        Corn corn1 = (Corn) af.createAsset("corn");
        
        
        //adds 725 to harvest
        farm.addAsset(corn1);
        corn1.setHarvestDays(0);
        
        //3 merchants -> expected merchant bonus is .08
        //expected harvest: 650 * 1.08 = 702
        
        assertEquals(702, farmControl.harvestCrops());
    }
    
    /**
     * Test the crop farmer bonus for the sale of crops
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test16CropBonus() throws AssetAlreadyDeadException {
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.CROPS));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.CROPS));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.CROPS));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.ANIMAL));
        Corn corn1 = (Corn) af.createAsset("corn");
        
        
        //adds 650 to harvest
        farm.addAsset(corn1);
        corn1.setHarvestDays(0);
        
        //3 crop farmers, 1 crop -> expected crops bonus is .12
        //expected harvest: 650 * 1.12 = 728
        
        assertEquals(728, farmControl.harvestCrops());
    }
    
    /**
     * Test the crop farmer bonus for the sale of crops
     * @throws AssetAlreadyDeadException
     */
    @Test
    public void test17AnimalBonus() throws AssetAlreadyDeadException
    {
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.ANIMAL));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.ANIMAL));
        farm.addFarmer(farmerControl.createFarmer(FarmerKind.CROPS));
        Cattle cattle1 = (Cattle) af.createAsset("cattle");
        Cattle cattle2 = (Cattle) af.createAsset("cattle");
        
        //adds 1450 to harvest
        farm.addAsset(cattle1);
        cattle1.setHarvestDays(0);
        farm.addAsset(cattle2);
        cattle2.setHarvestDays(0);
        
        //2 animal farmers, 2 animals -> expected animal bonus is 0.04
        //expected harvest: 1450 * 1.04 = 1508
        
        assertEquals(1508, farmControl.harvestAnimals());
    }
    
    @Test
    public void test18runDay() throws AssetAlreadyDeadException {
        farmControl.runDay();
    }
    
    @Test
    public void test19runNight() throws AssetAlreadyDeadException {
        farmControl.runNight();
    }
    
    
    
}
