package main.java;

import java.util.Random;

public class AssetFactory {
    
    public enum AssetKind {CATTLE, HOG, SHEEP, DAIRYCOW, CORN, SOY}
    
    //Singleton
    private static AssetFactory theOnlyAssetFactory;
    
    private static Random rand = new Random();
    
    private AssetFactory() {
        rand = new Random();
    }
    
    /**
     * Returns an asset factory. Creates one if needed. 
     * @return
     */
    public static AssetFactory makeAssetFactory() {
        if (theOnlyAssetFactory == null) {
            theOnlyAssetFactory = new AssetFactory();
        }
        return theOnlyAssetFactory;
    }
        
    
    /**
     * Pass an integer to this method to create a Farm Asset ->
     * 0:Cattle 1:Hog 2:Sheep 3:DairyCow 4:Corn 5:Soy
     * @param num
     * @return
     */
    public Asset createAsset(int num) {
        if(num < 0 || num > AssetKind.values().length-1) {
            return null;
        }
        if(num == AssetKind.CATTLE.ordinal()) {
            return new Cattle();
        }
        if(num == AssetKind.HOG.ordinal()) {
            return new Hog();
        }
        if(num == AssetKind.SHEEP.ordinal()) {
            return new Sheep();
        }
        if(num == AssetKind.DAIRYCOW.ordinal()) {
            return new DairyCow();
        }
        if(num == AssetKind.CORN.ordinal()) {
            return new Corn();
        }
        if(num == AssetKind.SOY.ordinal()) {
            return new Soy();
        }
        return null;
    }
    
    /**
     * Create and return a random asset
     * @return
     */
    public Asset createRandomAsset() {
        int assetNum = rand.nextInt(AssetKind.values().length);
        return createAsset(assetNum);
    }
    
}
