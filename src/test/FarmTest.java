package test;

import main.java.Farm;
import main.java.FarmControl;
import main.java.Farmer;
import main.java.FarmerControl.FarmerKind;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;


public class FarmTest {
    
    //create farm with 1 acre
    private Farm farm;
    private Farmer farmer1;
    private Farmer farmer2;
    
    @Before
    public void setup() {
        farm = Farm.makeTestFarm(1.0);
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
    
}
