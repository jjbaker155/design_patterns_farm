package main.java;

import exceptions.NoSuchFarmTypeException;

/**
 * Factory creates new Farms based on instructions
 * @author jjbaker4
 *
 */
public class FarmFactory {
    private static FarmFactory ffSoleInstance;
    private Farm newFarm = null;
    private final double DEFAULT_ACRES = 1.0;
    
    /**
     * Private constructor
     */
    private FarmFactory() {
    }
    
    /**
     * Ensures that there is only one instance of FarmFactory and return it
     * @return
     */
    public static FarmFactory create() {
        if (ffSoleInstance == null) {
            ffSoleInstance = new FarmFactory();
        }
        return ffSoleInstance;
    }
    
    /**
     * Returns farm of the kind specified by the client
     * "A" for animal farm
     * "C" for crop farm
     * @param type String representation of the farm type
     * @return Farm object
     * @throws NoSuchFarmTypeException
     */
    public Farm makeFarm(String type) throws NoSuchFarmTypeException {
        if (type.equalsIgnoreCase("A")) {
            return new AnimalFarm(DEFAULT_ACRES);
        } else
        if (type.equalsIgnoreCase("C")) {
            return new CropFarm(DEFAULT_ACRES);
        } else
            throw new NoSuchFarmTypeException();
    }
    
}
