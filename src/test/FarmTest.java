package test;

import main.java.Asset;
import main.java.Farm;
import main.java.FarmControl;
import main.java.Farmer;
import main.java.FarmerControl.FarmerKind;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import exceptions.FarmHasWonException;
import exceptions.FarmIsBankruptException;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Test class for Farm
 * @author jjbaker4
 * @version 1.0
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmTest {
    
    //create farm with 1 acre
    private Farm farm;
    private Farmer farmer1;
    private Farmer farmer2;
    
    @Before
    public void setup() {
        farm = Farm.makeTestFarm();
        farmer1 = new Farmer(FarmerKind.CROPS);
        farmer2 = new Farmer(FarmerKind.ANIMAL);
    }
    
    @Test
    public void aTestAddFarmer() {
        farm.addFarmer(farmer2);
        Farmer retrievedFarmer = farm.getFarmerList().get(0);
        assertTrue(farmer2.equals(retrievedFarmer));
    }
    
    @Test
    public void bTestInitialFarmerList() {
        assertEquals(0, farm.getFarmerCount());
    }
    
    @Test
    public void cTestRemoveFarmer() {
        farm.addFarmer(farmer1);
        farm.addFarmer(farmer2);
        farm.removeFarmer(farmer1);
        assertEquals(1, farm.getFarmerCount());
    }
    
    @Test
    public void dTestAddAcre() {
        farm.addAcre();
        assertTrue(farm.getSize() == 2.0);
    }
    
    @Test
    public void eTestAcreProductivity() {
        farm.addFarmer(farmer1);
        farm.addFarmer(farmer2);
        assertTrue(0.6 == farm.acreProductivity());
    }
    
    @Test
    public void fTestSetAcreage() {
        farm.setAcreage(5);
        assertTrue(5.0 == farm.getAcreage());
    }
    
    @Test
    public void gTestRemoveMoney() throws FarmIsBankruptException {
        farm.setMoney(1000);
        farm.deductMoney(1);
        assertEquals(999, farm.getMoney());
    }
    
    @Test (expected = FarmIsBankruptException.class)
    public void hTestBankrupt() throws FarmIsBankruptException {
        farm.setMoney(1000);
        farm.deductMoney(1001);
    }
    
    @Test
    public void iTestAddMoney() throws FarmHasWonException {
        farm.setMoney(1000);
        farm.addMoney(1);
        assertEquals(1001, farm.getMoney());
    }
    
    @Test (expected = FarmHasWonException.class)
    public void jTestFarmMoneyWin() throws FarmHasWonException {
        farm.setMoney(FarmControl.MAX_MONEY-1);
        farm.addMoney(1);
    }
    
    @Test (expected = FarmHasWonException.class)
    public void kTestMoneyWinOver() throws FarmHasWonException {
        farm.setMoney(FarmControl.MAX_MONEY-1);
        farm.addMoney(2);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void lTestTooMuchMoney() throws IllegalArgumentException {
        farm.setMoney(FarmControl.MAX_MONEY);
    }
    
    /**
     * Tests addAsset(). Also tests removeAssetByIndex()
     */
    @Test
    public void mTestAddAsset() {
        Asset myAsset = Asset.createRandomAsset();
        farm.addAsset(myAsset);
        assertEquals(farm.removeAssetByIndex(0), myAsset);
    }
    
    /**
     * Tests removeAsset()
     */
    @Test
    public void oTestRemoveAsset() {
        Asset myAsset = Asset.createRandomAsset();
        farm.addAsset(myAsset);
        assertTrue(farm.removeAsset(myAsset));
    }
    
    @Test
    public void xTestGetAssetList() {
        Asset myAsset = Asset.createAsset(1);
        farm.addAsset(myAsset);
        ArrayList<Asset> list = farm.getAssetList();
        assertEquals(list.get(0), myAsset);
    }
    
    /**
     * 
     */
    @Test
    public void yTestGetAssetByIndex() {
        Asset myAsset = Asset.createAsset(0);
        farm.addAsset(myAsset);
        assertEquals(farm.getAssetByIndex(0), myAsset);
    }
    
    public void zTestMakeFarm() {
        Farm myFarm = Farm.makeFarm();
        assertTrue(myFarm instanceof Farm);
    }
    
    
    
}
