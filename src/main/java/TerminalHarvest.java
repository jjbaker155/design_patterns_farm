package main.java;

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
    public void harvest(Asset a) {
       // change a to perished
       // other action (return money, or maybe that is handled by control)
        
    }

}
