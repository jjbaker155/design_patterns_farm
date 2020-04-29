package main.java;

public class StateDiseased implements AssetState {

    public StateDiseased() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public boolean isDead() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSick() {
        // TODO Auto-generated method stub
        return true;
    }
    
    
}
