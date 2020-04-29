package main.java;

import java.util.Random;

import exceptions.AssetAlreadyDeadException;

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
     * @throws AssetAlreadyDeadException 
     */
    public Asset createAsset(int num) throws AssetAlreadyDeadException {
        Asset a = null;
        if(num < 0 || num > AssetKind.values().length-1) {
            return null;
        }
        if(num == AssetKind.CATTLE.ordinal()) {
            a = new Cattle();
        }
        if(num == AssetKind.HOG.ordinal()) {
            a = new Hog();
        }
        if(num == AssetKind.SHEEP.ordinal()) {
            a = new Sheep();
        }
        if(num == AssetKind.DAIRYCOW.ordinal()) {
            a = new DairyCow();
        }
        if(num == AssetKind.CORN.ordinal()) {
            a = new Corn();
        }
        if(num == AssetKind.SOY.ordinal()) {
            a = new Soy();
        }
        if(a != null) {
            a.setAliveReorder();
        }
        return a;
    }
    
    /**
     * Create and return a random asset
     * @return
     * @throws AssetAlreadyDeadException 
     */
    public Asset createRandomAsset() throws AssetAlreadyDeadException {
        int assetNum = rand.nextInt(AssetKind.values().length);
        return createAsset(assetNum);
    }
    
}
