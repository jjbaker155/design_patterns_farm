package main.java;

public class AnimalStateContext {
    
    private AnimalState animalState;
    
    public AnimalStateContext() {
        
    }
    
    /**
     * Sets the state of the animal that has this context
     * @param as new state of the animal
     */
    public void setState(AnimalState as) {
        animalState = as;
    }

}
