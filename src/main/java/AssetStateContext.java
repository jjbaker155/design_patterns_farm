package main.java;

import exceptions.AssetAlreadyDeadException;

public class AssetStateContext {
    
    private AssetState myState;
    
    public AssetStateContext() {
        
    }
    /**
     * Sets the state of the asset that holds this context object
     * @param newState the new state
     */
    public void setState(AssetState newState) {
        myState = newState;
    }
    
    /**
     * Returns the current AssetState
     * @return current state of the asset
     */
    public AssetState getState() {
        return myState;
    }
    
    public boolean isAlive() {
        return myState.isAlive();
    }
    
    public boolean isDiseased() {
        return myState.isDiseased();
    }
}
