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
import main.java.FarmerControl;
import main.java.StateAlive;
import main.java.StateDead;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmControlTest {

    //private FarmControl farmControl;
    //private FarmerControl farmerControl;
    private FarmControl farmControl = FarmControl.createFarmControl();
    private Farm farm = farmControl.getFarm();
    private AssetFactory af = AssetFactory.makeAssetFactory();
    
    @Before
    public void setup() {
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
    public void test3GenerateInitialAssets() throws AssetAlreadyDeadException {
        farmControl.generateInitialAssets(farm);
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
        farmControl.generateInitialAssets(farm);
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
        farmControl.generateInitialAssets(farm);
        Asset a = farm.getAssetByIndex(0);
        a.getStateContext().setState(new StateDead());
        a.setAge(2);
        farmControl.reOrder(a);
        assertEquals(a.getAge(), 0);
    }
    
    @Test
    public void test8HarvestCrops() throws AssetAlreadyDeadException {
        Corn corn = (Corn) af.createAsset(4);
        corn.setAge(3);
        corn.setHarvestDays(0);
        farm.addAsset(corn);
        int earnings = farmControl.harvestCrops();
        assertTrue(earnings > 0);
    }
    
    @Test
    public void test9HarvestAnimals() throws AssetAlreadyDeadException {
        Cattle cattle = (Cattle) af.createAsset(0);
        cattle.setAge(3);
        cattle.setHarvestDays(0);
        farm.addAsset(cattle);
        int earnings = farmControl.harvestAnimals();
        assertTrue(earnings > 0);
    }
    
    
}
