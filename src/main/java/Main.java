package main.java;

import java.nio.charset.Charset;
import java.util.Scanner;

public class Main {
    
    /**
     * Main method just starts the sim.
     * @param args nothing in this String
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in, Charset.forName("UTF-8"));
        FarmControl fc = FarmControl.createFarmControl();
        boolean gameOn = true;
        
        do {
            try {
                fc.runDay();
                System.out.println(fc.reportDay());
                System.out.println("\nPress enter key to move on 1 day:");
                try {
                    System.in.read();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } catch (EndSimulationException e1) {
                gameOn = false;
                System.out.println(fc.reportDay());
                System.out.println(e1.getMessage());
            }
        } while (gameOn);
        input.close();
    }
}