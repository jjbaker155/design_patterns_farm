package main.java;

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
     * Harvest the asset
     * @param Asset to harvest
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
