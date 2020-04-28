package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import main.java.Farm;
import main.java.FarmControl;
import main.java.FarmerControl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmControlTest {

    //private FarmControl farmControl;
    //private FarmerControl farmerControl;
    private FarmControl farmControl = FarmControl.createFarmControl();
    private FarmerControl farmerControl = FarmerControl.createFarmerControl(farmControl);
    private Farm farm = farmControl.getFarm();
    
    @Before
    public void setup() {
        
    }   

    /**
     * After init of FarmControl, there should be 3 farmers
     */
    @Test
    public void testAInitialFarmersCount() {
        assertEquals(3, farm.getFarmerCount());
    }
    
    /**
     * Starts with 3 farmers, should have 4 after this test
     */
    @Test
    public void testBHireRandomFarmer() {
        farmControl.hireRandomFarmer();
        assertEquals(4, farm.getFarmerCount());
    }
    
    

}
