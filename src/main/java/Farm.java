package main.java;

import java.util.ArrayList;

/**
 * A single farm
 * @author jjbaker4
 *
 */
public class Farm {
    
    //list of Farmers
    private ArrayList<Farmer> farmers;
    //size of farm in square acres
    private double acres;
    

    /**
     * Constructor sets up new ArrayList of Farmer. Sets default acres.
     */
    public Farm(double a) {
        farmers = new ArrayList <Farmer> ();
        acres = a;
    }
    
    /**
     * 
     * @return size of farm in acres
     */
    public double getSize() {
        return acres;
    }
    
    /**
     * Returns the class name of this object as a String (might not need this
     * or might change to Class as return type) 
     * @return String representation of type
     */
    public String getFarmType() {
        return this.getClass().toString();
    }
    
    /**
     * Add a Farmer
     * @param f Farmer to add
     */
    public void addFarmer(Farmer f) {
        farmers.add(f);
    }
    
    /**
     * Returns list of farmers 
     * @return
     */
    public ArrayList<Farmer> farmersList(){
        return farmers;
    }
    
    /**
     * Returns number of farmers on this farm
     * @return
     */
    public int getFarmerCount() {
        return farmers.size();
    }
    
    public Object getName() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
