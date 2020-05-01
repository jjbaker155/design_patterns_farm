package main.java;

/**
 * State for when asset is dead.
 * @author jjbaker4
 * @version 1.0
 *
 */
public class StateDead implements AssetState {

    public StateDead() {
        
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public boolean isDead() {
        return true;
    }

    @Override
    public boolean isSick() {
        return false;
    }

}
