package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import main.java.FarmControl;
import main.java.Farmer;
import main.java.FarmerControl;
import main.java.FarmerControl.FarmerKind;

public class FarmerControlTest {
    
    FarmControl farmControl = FarmControl.createFarmControl();
    FarmerControl farmerControl = FarmerControl.createFarmerControl(farmControl);
    
    @Before
    public void setUp(){
        
    }
   
    @Test
    public void createCropFarmerTest() {
        Farmer f = farmerControl.createFarmer(FarmerKind.CROPS);
        assertEquals(f.getFarmerKind(), FarmerKind.CROPS);
    }
    
    @Test
    public void createAnimalFarmerTest() {
        Farmer f = farmerControl.createFarmer(FarmerKind.ANIMAL);
        assertEquals(f.getFarmerKind(), FarmerKind.ANIMAL);
    }
    
    @Test
    public void createDairyFarmerTest() {
        Farmer f = farmerControl.createFarmer(FarmerKind.DAIRY);
        assertEquals(f.getFarmerKind(), FarmerKind.DAIRY);
    }
    
    @Test
    public void createMercahantFarmerTest() {
        Farmer f = farmerControl.createFarmer(FarmerKind.MERCHANT);
        assertEquals(f.getFarmerKind(), FarmerKind.MERCHANT);
    }
    
    @Test
    public void createVeterinaryFarmerTest() {
        Farmer f = farmerControl.createFarmer(FarmerKind.VETERINARY);
        assertEquals(f.getFarmerKind(), FarmerKind.VETERINARY);
    }
    
    @Test
    public void randomFarmerTest() {
        Farmer f = farmerControl.randomFarmer();
        assertTrue(f.getClass().equals(Farmer.class));
    }

}
