/**
 * FarmerControl is actually a factory that I misnamed. It is a singleton.
 */

package main.java;

import java.util.Random;
import main.java.Farmer;

public class FarmerControl {
    
    public enum FarmerKind {
        CROPS, ANIMAL, MERCHANT, VETERINARY;
    }
    
    private Random rand = new Random();
    //private FarmControl farmControl;
    private static FarmerControl farmerControlSoleInstance;
    
    
    /**
     * Private constructor.
     */
    private FarmerControl() {
        //this.farmControl = farmControl;
    }
    
    /**
     * Private constructor for making test FarmerControl.
     */
    private FarmerControl(boolean test) {
        //this.farmControl = farmControl;
    }
    
    /**
     * This method will return a new FarmerControl if one does not already exist, and will return
     * the existing one if it does. This prevents the existence of more than one FC in the system
     * @return a FarmerControl object
     */
    public static FarmerControl createFarmerControl() {
        if (farmerControlSoleInstance == null) {
            farmerControlSoleInstance = new FarmerControl();
        }
        return farmerControlSoleInstance;
    }
    
    /**
     * This method will return a new FarmerControl for unit test ONLY.
     * This will DESTROY the existing FarmerControl
     * @return a FarmerControl object
     */
    public static FarmerControl createTestFarmerControl() {
        farmerControlSoleInstance = new FarmerControl(true);
        return farmerControlSoleInstance;
    }
    
    
    /**
     * Creates and returns farmer of a random kind.
     * @return farmer
     */
    public Farmer randomFarmer() {
        int num = FarmerKind.values().length;
        return createFarmer(FarmerKind.values()[rand.nextInt(num)]);
    }
    
    /**
     * Creates a farmer of the kind passed to this method.
     * @param k the kind of farmer
     * @return one farmer, ready to work
     */
    public Farmer createFarmer(FarmerKind k) {
        if (k.ordinal() >= FarmerKind.values().length) {
            throw new IllegalArgumentException("Not a valid farmer type");
        }
        return new Farmer(k);
    }
    
    
    
}
