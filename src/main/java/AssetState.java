package main.java;

public interface AssetState {
    final static FarmControl fc = FarmControl.createFarmControl();
    
    boolean isAlive();
    boolean isDiseased();
    
}
