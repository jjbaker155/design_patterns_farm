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

    @Override
    public int harvest(Asset a) throws AssetAlreadyDeadException {
       // change a to perished
        a.setDead();
       // other action (return money, or maybe that is handled by control)
        return a.getProfit();
    }

}
