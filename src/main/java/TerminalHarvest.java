package main.java;

import exceptions.AssetAlreadyDeadException;

public class TerminalHarvest implements HarvestStrategy {

  //this strategy is also a singleton
    private static TerminalHarvest thSingleInstance;
    
    private TerminalHarvest() {
        // TODO Auto-generated constructor stub
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
     * Harvest the asset
     * @param Asset to harvest
     * @return int the amount of money earned
     * @throws AssetAlreadyDeadException is the asset is already dead
     */
    @Override
    public int harvest(Asset a) throws AssetAlreadyDeadException {
        // change a to dead
        if (a.isDead()) {
            throw new AssetAlreadyDeadException();
        }
        a.setDead();
        //return sale amount
        return a.getProfit();
    }

    @Override
    public boolean isHarvestTerminal() {
        return true;
    }

}
