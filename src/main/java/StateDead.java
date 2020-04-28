package main.java;

public class StateDead implements AssetState{

    public StateDead() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public boolean isDiseased() {
        return false;
    }

}
