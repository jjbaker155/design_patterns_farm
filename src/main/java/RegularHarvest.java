package main.java;

/**
 * This is a harvest strategy. To be employed by crops and animals
 * that don't have to be killed to be sold. It is a singleton and supports a strategy pattern
 * @author jjbaker4
 * @version 1.0
 */
public class RegularHarvest implements HarvestStrategy {
    
    //this strategy is also a singleton
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
    
    /**
     * Harvest the asset.
     * @param a Asset to harvest
     * @return int the amount of money earned
     * @throws AssetAlreadyDeadException  is the asset is dead
     */
    @Override
    public int harvest(Asset a) {
        a.setHarvestDays(FarmControl.DEFAULT_HARVEST_DAYS);
        //return sale amount
        return a.getProfit();
    }

    @Override
    public boolean isHarvestTerminal() {
        return false;
    }
    
}
