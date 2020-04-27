package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.NoSuchFarmTypeException;
import main.java.AnimalFarm;
import main.java.CropFarm;
import main.java.Farm;
import main.java.FarmFactory;

/**
 * Test FarmFactory class
 * @author bbymi
 *
 */
public class FarmFactoryTest {
    
    FarmFactory ff1;
    FarmFactory ff2;
    
    @Before
    public void setUp() {
        ff1 = FarmFactory.create();
        ff2 = FarmFactory.create();
    }
    
    
    /**
     * Tests to see if the FarmFactory is a singleton class
     */
    @Test
    public void singletonTest() {
        assertEquals(ff1.hashCode(),ff2.hashCode());
    }
    
    /**
     * Tests the creation of an AnimalFarm
     * @throws NoSuchFarmTypeException
     */
    @Test
    public void createAnimalFarmTest() throws NoSuchFarmTypeException {
        Farm af1 = ff1.makeFarm("A");
        assertEquals(af1.getClass(), AnimalFarm.class);
    }
    
    /**
     * Tests the creation of a CropFarm
     * @throws NoSuchFarmTypeException
     */
    @Test
    public void createCropFarmTest() throws NoSuchFarmTypeException {
        Farm cf1 = ff1.makeFarm("c");
        assertEquals(cf1.getClass(), CropFarm.class);
    }
    
    @Test (expected = NoSuchFarmTypeException.class)
    public void noSuchFarmTypeTest() throws NoSuchFarmTypeException {
        Farm wf1 = ff1.makeFarm("b");
    }

}
