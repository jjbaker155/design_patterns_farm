package main.java;

import main.java.FarmerControl.FarmerKind;

public class Farmer {
    
    private FarmerKind kind;
    
    /**
     * Farmer constructor
     * @param type of farmer
     */
    public Farmer(FarmerKind k) {
        kind = k;
    }
    
    /**
     * Return the kind of farmer
     * @return FarmerKind enum
     */
    public FarmerKind getFarmerKind() {
        return kind;
    }
    
    /**
     * A string representation of the farmer type
     * @return
     */
    public String getTypeName() {
        if (this.getFarmerKind() == FarmerKind.ANIMAL) {
            return "animal";
        }
        if (this.getFarmerKind() == FarmerKind.CROPS) {
            return "crop";
        }
        if (this.getFarmerKind() == FarmerKind.VETERINARY) {
            return "veterinary";
        }
        if (this.getFarmerKind() == FarmerKind.MERCHANT) {
            return "merchant";
        }
        return null;
    }
}

