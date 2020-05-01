package main.java;
/**
 * An asset state for when asset is alive.
 * @author jjbaker4
 * @version 1.0
 */

public class StateAlive implements AssetState {

    public StateAlive() {
        // TODO Auto-generated constructor stub
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
        // TODO Auto-generated method stub
        return false;
    }

}
