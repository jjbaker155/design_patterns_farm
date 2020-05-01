/**
 * Harvest strategy to support a stratgedy pattern. Some Assets are killed when
 * harvested, and some are not.
 */

package main.java;

public interface HarvestStrategy {
    
    public int harvest(Asset a);
    
    public boolean isHarvestTerminal();
}
