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
    public void setState(AssetState newState){
        myState = newState;
    }
    
    public boolean isAlive() {
        return myState.isAlive();
    }
    
    public boolean isDiseased() {
        return myState.isDiseased();
    }
}
