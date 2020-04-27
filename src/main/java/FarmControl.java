package main.java;

import java.util.Random;

import exceptions.NoSuchFarmTypeException;

public class FarmControl {
    
    private final int INITIAL_FARMS = 1;
    private final int INITIAL_FARMERS = 3;
    
    private FarmFactory ff = FarmFactory.create();
    
    private Random rand = new Random();
    
    
    /**
     * Private constructor
     */
    private FarmControl() {   
    }
    
    /**
     * Create singleton FarmControl
     * @return FamrControl
     */
    public static FarmControl createFarmControl() {
        return new FarmControl();
    }
    
    
    public Farm generateInitialFarm() throws NoSuchFarmTypeException {
        //equal chance for each farm type
        int whatFarm = rand.nextInt(1);
        if (whatFarm == 0)
            return ff.makeFarm("A"); //make animal farm
        if (whatFarm == 1)
            return ff.makeFarm("C"); //make crop farm
        else
            return null;
    }
    
    
    private void generateInitialFarmers(Farm farm) {
        
    }
    
    //place order (commodity type)
    
}
