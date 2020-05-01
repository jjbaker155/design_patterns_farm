package main.java;

/**
 * State for when asset is sick.
 * @author jjbaker4
 * @version 1.0
 *
 */
public class StateDiseased implements AssetState {

    public StateDiseased() {
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public boolean isSick() {
        return true;
    }
    
    
}
