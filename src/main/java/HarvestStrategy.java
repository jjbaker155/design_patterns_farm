package main.java;

import exceptions.AssetAlreadyDeadException;

public interface HarvestStrategy {
    public int harvest(Asset a) throws AssetAlreadyDeadException;
}
