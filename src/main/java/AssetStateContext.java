/**
 * State context for state pattern that treats assets differently depsning on their state. 
 */

package main.java;

public class AssetStateContext {
    
    private AssetState myState;
    
    public AssetStateContext() {
        
    }
    
    /**
     * Sets the state of the asset that holds this context object.
     * @param newState myState the new state
     */
    public void setState(AssetState newState) {
        myState = newState;
    }
    
    /**
     * Returns the current AssetState.
     * @return current state of the asset
     */
    public AssetState getState() {
        return myState;
    }
    
    public boolean isAlive() {
        return myState.isAlive();
    }
    
    public boolean isDiseased() {
        return myState.isSick();
    }
    
    public boolean isDead() {
        return myState.isDead();
    }
}
