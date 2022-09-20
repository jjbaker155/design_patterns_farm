### Design Pattern Summary
You’ll find that I have a number of factories that are also singleton classes. I used a state pattern to handle the behaviors of the various assets in the farm depending on their status. I used a strategy pattern to handle the different kind of harvesting that you would do with one asset vs another, since class type isn’t a reliable indicator of this. All of these techniques are intertwined. For example, an Asset would be generated by an AssetFactory which is a factory and a singleton. The Asset would be generated with a HarvestStrategy and with an AssetState.

#### Other Notes
Please see FarmDesignPatternAssignment.pdf for the original assignment instructions.  
The constants will need to be tweaked before this will be an interesting simulation. The asset costs are too high right now.
