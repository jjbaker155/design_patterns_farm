package main.java;

public abstract class Crop extends Asset{
    
    
    public Crop (int cost, int profit, double land, HarvestStrategy hs) {
         super(cost, profit, land, hs);
     }

}
