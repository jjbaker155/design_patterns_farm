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
}

