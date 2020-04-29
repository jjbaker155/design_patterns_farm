package main.java;

public class RegularHarvest implements HarvestStrategy {

    //this staregy is also a singleton
    private static RegularHarvest rhSingleInstance;
    
    private RegularHarvest() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Returns the single instance of HarvestStrategy. Creates a new one
     * if required
     * @return HarvestStrategy
     */
    public static RegularHarvest getRegularHarvest() {
        if (rhSingleInstance == null) {
            rhSingleInstance = new RegularHarvest();
        }
        return rhSingleInstance;
    }

    @Override
    public int harvest(Asset a) {
        //change state of a 
        //other action (return money, or maybe that is handled by control)
        return 0;
    }

}
