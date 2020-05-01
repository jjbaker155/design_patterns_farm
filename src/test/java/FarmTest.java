package test.java;

import main.java.Asset;
import main.java.AssetFactory;
import main.java.Farm;
import main.java.FarmControl;
import main.java.FarmHasWonException;
import main.java.FarmIsBankruptException;
import main.java.Farmer;
import main.java.FarmerControl.FarmerKind;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Test class for Farm
 * @author jjbaker4
 * @version 1.0
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmTest {
    
    private Farm farm;
    private Farmer farmer1;
    private Farmer farmer2;
    private AssetFactory af = AssetFactory.makeAssetFactory();
    
    @Before
    public void setup() {
        //create farm with 1 acre
        farm = Farm.makeTestFarm();
        farmer1 = new Farmer(FarmerKind.CROPS);
        farmer2 = new Farmer(FarmerKind.ANIMAL);
        
    }
    
    @Test
    public void test1AddFarmer() {
        farm.addFarmer(farmer2);
        Farmer retrievedFarmer = farm.getFarmerList().get(0);
        assertTrue(farmer2.equals(retrievedFarmer));
    }
    
    @Test
    public void test2InitialFarmerList() {
        assertEquals(0, farm.getFarmerCount());
    }
    
    @Test
    public void test3RemoveFarmer() {
        farm.addFarmer(farmer1);
        farm.addFarmer(farmer2);
        farm.removeFarmer(farmer1);
        assertEquals(1, farm.getFarmerCount());
    }
    
    @Test
    public void test4AddAcre() {
        farm.addAcre();
        assertTrue(farm.getSize() == 2.0);
    }
    
    @Test
    public void test5AcreProductivity() {
        farm.addFarmer(farmer1);
        farm.addFarmer(farmer2);
        assertTrue(0.6 == farm.acreProductivity());
    }
    
    @Test
    public void test6SetAcreage() {
        farm.setAcreage(5);
        assertTrue(5.0 == farm.getAcreage());
    }
    
    @Test
    public void test7RemoveMoney() throws FarmIsBankruptException {
        farm.setMoney(1000);
        farm.deductMoney(1);
        assertEquals(999, farm.getMoney());
    }
    
    @Test (expected = FarmIsBankruptException.class)
    public void test8Bankrupt() throws FarmIsBankruptException {
        farm.setMoney(1000);
        farm.deductMoney(1001);
    }
    
    @Test
    public void test9AddMoney() throws FarmHasWonException {
        farm.setMoney(1000);
        farm.addMoney(1);
        assertEquals(1001, farm.getMoney());
    }
    
    @Test (expected = FarmHasWonException.class)
    public void test10FarmMoneyWin() throws FarmHasWonException {
        farm.setMoney(FarmControl.MAX_MONEY-1);
        farm.addMoney(1);
    }
    
    @Test (expected = FarmHasWonException.class)
    public void test11MoneyWinOver() throws FarmHasWonException {
        farm.setMoney(FarmControl.MAX_MONEY-1);
        farm.addMoney(2);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void test12TooMuchMoney() throws IllegalArgumentException {
        farm.setMoney(FarmControl.MAX_MONEY);
    }
    
    /**
     * Tests addAsset(). Also tests removeAssetByIndex()
     * @throws AssetAlreadyDeadException 
     */
    @Test
    public void test13AddAsset() {
        Asset myAsset = af.createRandomAsset();
        farm.addAsset(myAsset);
        assertEquals(farm.removeAssetByIndex(0), myAsset);
    }
    
    /**
     * Tests removeAsset()
     * @throws AssetAlreadyDeadException 
     */
    @Test
    public void test14RemoveAsset() {
        Asset myAsset = af.createRandomAsset();
        farm.addAsset(myAsset);
        assertTrue(farm.removeAsset(myAsset));
    }
    
    @Test
    public void test15GetAssetList() {
        Asset myAsset = af.createAsset(1);
        farm.addAsset(myAsset);
        ArrayList<Asset> list = farm.getAssetList();
        assertEquals(list.get(0), myAsset);
    }
    
    /**
     * @throws AssetAlreadyDeadException 
     * 
     */
    @Test
    public void test16GetAssetByIndex() {
        Asset myAsset = af.createAsset(0);
        farm.addAsset(myAsset);
        assertEquals(farm.getAssetByIndex(0), myAsset);
    }
    
    @Test
    public void test17MakeFarm() {
        Farm myFarm = Farm.makeFarm();
        assertTrue(myFarm != null);
    }
    
    
}
