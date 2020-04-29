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
    public boolean isDead() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isSick() {
        // TODO Auto-generated method stub
        return false;
    }

}
