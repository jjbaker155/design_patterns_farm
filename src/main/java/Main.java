package main.java;

import java.nio.charset.Charset;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in, Charset.forName("UTF-8"));
        FarmControl fc = FarmControl.createFarmControl();
        do {
            fc.runDay();
            System.out.println(fc.reportDay());
            fc.runNight();
            System.out.println(fc.reportNight());
            System.out.println("\nPress enter key to move on 1 day:");
            try {
                System.in.read();
            }
            catch(Exception e) {
            
            }
        } while(fc.isGameOn());
        input.close();
    }
}