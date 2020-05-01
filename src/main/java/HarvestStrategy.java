package main.java;

public interface HarvestStrategy {
    
    public int harvest(Asset a);
    
    public boolean isHarvestTerminal();
}
