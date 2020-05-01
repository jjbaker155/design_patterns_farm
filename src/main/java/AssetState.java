/**
 * Object type for representing the state of an Asset.
 * @author jjbaker4
 * @version 1.0
 */

package main.java;

public interface AssetState {
    static final FarmControl fc = FarmControl.createFarmControl();
    
    boolean isAlive();
    
    boolean isDead();
    
    boolean isSick();
    
}
