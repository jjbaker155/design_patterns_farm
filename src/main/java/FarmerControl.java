package main.java;
import java.util.Random;

import main.java.Farmer;

public class FarmerControl {
    
    public enum FarmerKind {
        CROPS, LIVESTOCK, DAIRY, MERCHANT;
    }
    
    private Random rand = new Random();
    private FarmControl farmControl;
    private static FarmerControl fcSoleInstance;
    
    
    /**
     * Private constructor
     */
    private FarmerControl() {
        farmControl = FarmControl.createFarmControl();
    }
    
    /**
     * This method will return a new FarmerControl if one does not already exist, and will return
     * the existing one if it does. This prevents the existence of more than one FC in the system
     * @return a FarmerControl object
     */
    public static FarmerControl createFarmerControl() {
        if (fcSoleInstance == null) {
            fcSoleInstance = new FarmerControl();
        }
        return fcSoleInstance;
    }
    
    /**
     * Creates and returns farmer of a random kind
     * @return farmer
     */
    public Farmer randomFarmer() {
        int num = FarmerKind.values().length;
        return createFarmer(FarmerKind.values()[rand.nextInt(num)]);
    }
    
    /**
     * Creates a farmer of the kind passed to this method
     * @param k the kind of farmer
     * @return one farmer, ready to work
     */
    private Farmer createFarmer(FarmerKind k) {
        return new Farmer(k);
    }
    
}
