package main.java;

public abstract class Crop extends Asset{
    
    
    public Crop (int cost, int profit, double land, double diseaseDeathRate, HarvestStrategy hs) {
         super(cost, profit, land, diseaseDeathRate, hs);
     }

}
