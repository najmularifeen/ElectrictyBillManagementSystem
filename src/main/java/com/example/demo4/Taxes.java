package com.example.demo4;

import java.util.Scanner;

public class Taxes {
    static Scanner sc = new Scanner(System.in);
    private static int ptv = 35;
    private static double fpa = 8.00;
    private static double salesTax = 5.00;
    private static int[][] unitPrices = {
            {12, 18, 25, 30},    // Residential
            {15, 25, 40, 50},    // Commercial
            {20, 30, 45, 60}     // Industry
    };

    // Getters
    public static int getPtv() {
        return ptv;
    }

    public static double getFpa() {
        return fpa;
    }

    public static double getSalesTax() {
        return salesTax;
    }

    public static int[][] getUnitPrices() {
        return unitPrices;
    }
    public static void setPtv(int newPtv) {
        ptv = newPtv;
    }

    public static void setFpa(double newFpa) {
        fpa = newFpa;
    }

    public static void setSalesTax(double newSalesTax) {
        salesTax = newSalesTax;
    }


    // Display all taxes and unit prices
    public static void displayCurrentTaxes() {
        System.out.println("\n--- Current Tax Details ---");
        System.out.println("\nPTV Fee: Rs. " + ptv);
        System.out.println("FPA (%): " + fpa);
        System.out.println("Sales Tax (%): " + salesTax);

        String[] categories = {"Residential", "Commercial", "Industry"};
        System.out.println("\n--- Unit Prices per Category ---\n");
        for (int i = 0; i < unitPrices.length; i++) {
            System.out.print(categories[i] + ": ");
            for (int price : unitPrices[i]) {
                System.out.print(price + " ");
            }
            System.out.println();
        }
        System.out.print("Do you want to change the taxes: (yes/no)");
        if (sc.nextLine().equalsIgnoreCase("yes")) {

            updateTaxes();
        }
        else {
            System.out.println("Exiting from the Taxes class.");
        }
    }

    // Update taxes and unit prices
    public static void updateTaxes() {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter new PTV fee (current: " + ptv + "): ");
        ptv = sc.nextInt();

        System.out.print("Enter new FPA % (current: " + fpa + "): ");
        fpa = sc.nextDouble();

        System.out.print("Enter new Sales Tax % (current: " + salesTax + "): ");
        salesTax = sc.nextDouble();

        String[] categories = {"Residential", "Commercial", "Industry"};
        for (int i = 0; i < 3; i++) {
            System.out.println("Update unit prices for " + categories[i] + ":");
            for (int j = 0; j < 4; j++) {
                System.out.print("  Enter unit price for slab " + (j + 1) + ": ");
                unitPrices[i][j] = sc.nextInt();
            }
        }

        System.out.println("\nTaxes and unit prices updated successfully.");
    }
}
