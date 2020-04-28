package main.java;

import exceptions.AssetAlreadyDeadException;

public class AssetStateContext {
    
    private AssetState myState;
    
    public AssetStateContext() {
        
    }
    /**
     * Sets the state of the asset that holds this context object
     * @param newState the new state
     * @throws AssetAlreadyDeadException prevents state changes because asset is dead
     */
    public void setState(AssetState newState) throws AssetAlreadyDeadException {
        if (myState.getClass().equals(StateDead.class)) {
            throw new AssetAlreadyDeadException(); 
        }
        myState = newState;
    }
    
    public boolean isAlive() {
        return myState.isAlive();
    }
    
    public boolean isDiseased() {
        return myState.isDiseased();
    }
    
}
