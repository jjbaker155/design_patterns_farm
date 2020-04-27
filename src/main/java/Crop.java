package main.java;

public abstract class Crop extends Asset{
    
    private final CropStateContext stateContexct = new CropStateContext();
    
    
    public Crop (int cost, int profit, double land, double diseaseDeathRate) {
         super(cost, profit, land, diseaseDeathRate);
     }

}
