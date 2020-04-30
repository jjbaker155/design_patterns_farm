package test;

import org.junit.Before;

import main.java.AssetFactory;
import main.java.Farm;
import main.java.FarmControl;

public class AssetTest {
    
    private FarmControl farmControl = FarmControl.createFarmControl();
    private Farm farm = farmControl.getFarm();
    private AssetFactory af = AssetFactory.makeAssetFactory();
    
    
    @Before
    public void setup() {
        farmControl = FarmControl.createTestFarmControl();
        farm = farmControl.getFarm();
    }

}
