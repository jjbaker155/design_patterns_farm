package main.java;

import java.util.Random;

/**
 * A singleton Asset factory. You can use the methods below to generate farm Assets of any type.
 * @author jjbaker4
 * @version 1.0
 *
 */
public class AssetFactory {
    
    public enum AssetKind { CATTLE, HOG, SHEEP, DAIRYCOW, CORN, SOY }
    
    //Singleton
    private static AssetFactory theOnlyAssetFactory;
    
    private static Random rand = new Random();
    
    private AssetFactory() {
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
     * Pass an integer to this method to create a Farm Asset.
     * 0:Cattle 1:Hog 2:Sheep 3:DairyCow 4:Corn 5:Soy
     * @param num int representing Asset to be created
     * @return an Asset
     */
    public Asset createAsset(int num) {
        Asset a = null;
        if (num < 0 || num > AssetKind.values().length - 1) {
            return null;
        }
        if (num == AssetKind.CATTLE.ordinal()) {
            a = new Cattle();
        }
        if (num == AssetKind.HOG.ordinal()) {
            a = new Hog();
        }
        if (num == AssetKind.SHEEP.ordinal()) {
            a = new Sheep();
        }
        if (num == AssetKind.DAIRYCOW.ordinal()) {
            a = new DairyCow();
        }
        if (num == AssetKind.CORN.ordinal()) {
            a = new Corn();
        }
        if (num == AssetKind.SOY.ordinal()) {
            a = new Soy();
        }
        return a;
    }
    
    /**
     * Pass a String to this method to create a Farm Asset.
     * "Cattle" "Hog" "Sheep" "DairyCow" "Corn" "Soy"
     * @param s String represnts asset that you want
     * @return an Asset
     */
    public Asset createAsset(String s) {
        Asset a = null;
        if (s.equalsIgnoreCase("cattle")) {
            a = new Cattle();
        }
        if (s.equalsIgnoreCase("hog")) {
            a = new Hog();
        }
        if (s.equalsIgnoreCase("sheep")) {
            a = new Sheep();
        }
        if (s.equalsIgnoreCase("dairy cow")) {
            a = new DairyCow();
        }
        if (s.equalsIgnoreCase("corn")) {
            a = new Corn();
        }
        if (s.equalsIgnoreCase("soy")) {
            a = new Soy();
        }
        //a.setAliveNewAsset();
        return a;
    }
    
    /**
     * Create and return a random asset.
     * @return Asset
     */
    public Asset createRandomAsset() {
        int assetNum = rand.nextInt(AssetKind.values().length);
        return createAsset(assetNum);
    }
    
    public Asset createAssetOfType(Asset a) {
        return createAsset(a.getTypeName());
    }

    
}
