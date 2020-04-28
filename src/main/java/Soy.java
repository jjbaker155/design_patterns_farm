package main.java;

public class Soy extends Crop {
    private final static double LAND_NEEDED = 0.3;
    private final static int COST = 300;
    private final static int PROFIT = 550;
    private final static double DISEASE_DEATH_RATE = .25;
    
    public Soy() {
        super(COST, PROFIT, LAND_NEEDED, DISEASE_DEATH_RATE);
    }

}
