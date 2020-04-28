package test;

import main.java.Farm;
import main.java.FarmControl;
import main.java.Farmer;
import main.java.FarmerControl.FarmerKind;

import org.junit.Before;
import org.junit.Test;

import exceptions.FarmHasWonException;
import exceptions.FarmIsBankruptException;

import static org.junit.Assert.*;

import java.util.ArrayList;


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
    public void addFarmerTest() {
        farm.addFarmer(farmer2);
        Farmer retrievedFarmer = farm.getFarmerList().get(0);
        assertTrue(farmer2.equals(retrievedFarmer));
    }
    
    @Test
    public void initialFarmerListTest() {
        assertEquals(0, farm.getFarmerCount());
    }
    
    @Test
    public void removeFarmerTest() {
        farm.addFarmer(farmer1);
        farm.addFarmer(farmer2);
        farm.removeFarmer(farmer1);
        assertEquals(1, farm.getFarmerCount());
    }
    
    @Test
    public void addAcreTest() {
        farm.addAcre();
        assertTrue(farm.getSize() == 2.0);
    }
    
    @Test
    public void acreProductivity() {
        farm.addFarmer(farmer1);
        farm.addFarmer(farmer2);
        assertTrue(0.6 == farm.acreProductivity());
    }
    
    @Test
    public void setAcreageTest() {
        farm.setAcreage(5);
        assertTrue(5.0 == farm.getAcreage());
    }
    
    @Test
    public void removeMoneyTest() throws FarmIsBankruptException {
        farm.setMoney(1000);
        farm.deductMoney(1);
        assertEquals(999, farm.getMoney());
    }
    
    @Test (expected = FarmIsBankruptException.class)
    public void bankruptTest() throws FarmIsBankruptException {
        farm.setMoney(1000);
        farm.deductMoney(1001);
    }
    
    @Test
    public void addMoneyTest() throws FarmHasWonException {
        farm.setMoney(1000);
        farm.addMoney(1);
        assertEquals(1001, farm.getMoney());
    }
    
    @Test (expected = FarmHasWonException.class)
    public void farmMoneyWinExact() throws FarmHasWonException {
        farm.setMoney(FarmControl.MAX_MONEY-1);
        farm.addMoney(1);
    }
    
    @Test (expected = FarmHasWonException.class)
    public void farmMoneyWinOver() throws FarmHasWonException {
        farm.setMoney(FarmControl.MAX_MONEY-1);
        farm.addMoney(2);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void tooMuchMoneySet() throws IllegalArgumentException {
        farm.setMoney(FarmControl.MAX_MONEY);
    }
    
    
    
}
