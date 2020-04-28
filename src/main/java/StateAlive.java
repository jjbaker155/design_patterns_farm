package main.java;

public class StateAlive implements AssetState{

    public StateAlive() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public boolean isDiseased() {
        return false;
    }

}
