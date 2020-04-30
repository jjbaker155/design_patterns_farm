package main.java;

import java.util.Scanner;

import exceptions.AssetAlreadyDeadException;

public class Main {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        FarmControl fc = FarmControl.createFarmControl();
        do {
            //todo: place try catch block in methods where they will be useful
            //instead of here
            try {
                System.out.println(fc.runDay());
            } catch (AssetAlreadyDeadException e1) {
                e1.printStackTrace();
            }
            System.out.println(fc.runNight());
            System.out.println("\nPress enter key to move on 1 day:");
            try {
                System.in.read();
            }
            catch(Exception e) {
            
            }
        } while(fc.isGameOn());
    }
}