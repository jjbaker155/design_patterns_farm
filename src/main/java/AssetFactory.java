package main.java;

public class AssetFactory {

    private static AssetFactory theOnlyAssetFactory;
    
    private AssetFactory() {
        
    }
    
    public static AssetFactory makeAssetFactory() {
        if (theOnlyAssetFactory == null) {
            theOnlyAssetFactory = new AssetFactory();
        }
        return theOnlyAssetFactory;
    }
    
    public Asset makeAsset(String a) {
        if (a.equalsIgnoreCase("cattle"))
            return new Cattle();
        if (a.equalsIgnoreCase("chicken"))
            return new Chicken();
        if (a.equalsIgnoreCase("cow"))
            return new DairyCow();
        if (a.equalsIgnoreCase("sheep"))
            return new Sheep();
        else return null;
    }
}
