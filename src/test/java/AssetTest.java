package test.java;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import main.java.AssetFactory;
import main.java.Cattle;
import main.java.Corn;
import main.java.DairyCow;
import main.java.Farm;
import main.java.FarmControl;
import main.java.Hog;
import main.java.Sheep;
import main.java.Soy;
import main.java.StateDead;

public class AssetTest {
    
    private AssetFactory af = AssetFactory.makeAssetFactory();
    Cattle cattle1;
    Cattle cattle2;
    Sheep sheep1;
    Sheep sheep2;
    Soy soy1;
    Soy soy2;
    Corn corn1;
    Corn corn2;
    Hog hog1;
    Hog hog2;
    DairyCow cow1;
    DairyCow cow2;
    
    @Before
    public void setup() {
        cattle1 = (Cattle) af.createAsset("cattle");
        cattle2 = (Cattle) af.createAsset(0);
        sheep1 = (Sheep) af.createAsset("sheep");
        sheep2 = (Sheep) af.createAsset(2);
        soy1 = (Soy) af.createAsset("soy");
        soy2 = (Soy) af.createAsset(5);
        corn1 = (Corn) af.createAsset("corn");
        corn2 = (Corn) af.createAsset(4);
        hog1 = (Hog) af.createAsset("hog");
        hog2 = (Hog) af.createAsset(1);
        cow1 = (DairyCow) af.createAsset("DairyCow");
        cow2 = (DairyCow) af.createAsset(3);
    }
    
    @Test
    public void test1IsAlive() {
        assertTrue(cattle1.isAlive());
    }
    
    @Test
    public void test2IsDead() {
        cattle1.setDead();
        assertTrue(cattle1.isDead() && cattle1.getStateContext().getState() instanceof StateDead &&
                !cattle1.isDiseased());
    }
    
    @Test
    public void test3IsHealthy() {
        cattle1.setDead();
        cattle2.setDiseased();
        assertTrue(!cattle1.isHealthy() && !cattle2.isHealthy() && cow1.isHealthy());
    }
    
    /**
     * Test getAge() and incrementAge()
     */
    @Test
    public void test4Age() {
        cattle1.incrementDay();
        cattle1.incrementDay();
        assertTrue(cattle1.getAge() == 2 && cattle2.getAge() == 0);  
    }
    
    @Test
    public void test5GetLandNeeded() {
        assertTrue(cattle1.getLandNeeded() == Cattle.LAND_NEEDED &&
                sheep1.getLandNeeded() == Sheep.LAND_NEEDED);
    }
    
    @Test
    public void test7GetProfit() {
        assertTrue(soy1.getProfit() == Soy.PROFIT && corn1.getProfit() == Corn.PROFIT);
    }
    
    @Test
    public void test8AssetTypeToString() {
        assertTrue(sheep2.getTypeName().equalsIgnoreCase("Sheep") &&
                cow2.getTypeName().equalsIgnoreCase("Dairy Cow") &&
                hog2.getTypeName().equalsIgnoreCase("Hog") &&
                cattle2.getTypeName().equalsIgnoreCase("Cattle") &&
                corn2.getTypeName().equalsIgnoreCase("Corn") &&
                soy2.getTypeName().equalsIgnoreCase("Soy"));
    }
    
    @Test
    public void test9HarvestTypeTest() {
        assertTrue(!sheep1.isHarvestTerminal() && hog1.isHarvestTerminal());
    }

}
