### Design Pattern Summary
You’ll find a number of factories that are also singleton classes. A state pattern is used to handle the behaviors of the various assets in the farm depending on their status. A strategy pattern is used to handle the different kinds of harvesting that you would do with one Asset vs another, since class type isn’t a reliable indicator of this. All of these techniques are intertwined. For example, an Asset would be generated by an AssetFactory which is both a factory and a singleton. The Asset would be designated with an appropriate HarvestStrategy and AssetState.

#### Other Notes
Please see FarmDesignPatternAssignment.pdf for the original assignment instructions.  
The constants will need to be tweaked before this will be an interesting simulation. The asset costs are too high right now.
