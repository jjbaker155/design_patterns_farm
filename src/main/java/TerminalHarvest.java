package main.java;

/**
 * Terminal harvest is a strategy that should employed 
 * on animals who have to be killed in order to sell.
 * @author jjbaker4
 * @version 1.0
 *
 */

public class TerminalHarvest implements HarvestStrategy {

    //this strategy is also a singleton
    private static TerminalHarvest thSingleInstance;
    
    private TerminalHarvest() {
        
    }
    
    /**
     * Returns the single instance of HarvestStrategy. Creates a new one
     * if required
     * @return HarvestStrategy
     */
    public static TerminalHarvest getTerminalHarvest() {
        if (thSingleInstance == null) {
            thSingleInstance = new TerminalHarvest();
        }
        return thSingleInstance;
    }
    
    /**
     * Harvest the asset.
     * @param a the asset to harvest
     * @return int the amount of money earned
     * @throws AssetAlreadyDeadException is the asset is already dead
     */
    @Override
    public int harvest(Asset a) {
        a.setDead();
        return a.getProfit();
    }
    
    /**
     * Returns true.
     * @return true
     */
    @Override
    public boolean isHarvestTerminal() {
        return true;
    }

}
