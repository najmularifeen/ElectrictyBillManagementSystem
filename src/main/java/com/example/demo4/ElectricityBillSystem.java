package com.example.demo4;

import java .util.Scanner;
//import java.io.*;
//import java.util.*;
public class ElectricityBillSystem
{
    private Scanner sc = new Scanner(System.in);

    public void run()
    {

        System.out.println("\nWelcome to Electricity Bill Management System");

        while (true) {
            System.out.println("\n1. Generate Bills");
            System.out.println("2. Apply Discount");
            System.out.println("3. Apply Late Payment Fine");
            System.out.println("4. Pay Bill (Add to Company Revenue)");
            System.out.println("5. Show Revenue Statistics");
            System.out.println("6. Taxes");
            System.out.println("7. Exit");
            System.out.print("\nEnter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    CallGenerateBill.generateBill();
                    break;
                case 2:
                    Discount.updateBill();
                    break;

                case 3:
                    Fine.applyFineAfterDueDate();
                    break;
                case 4:
                    Payment.payBillById();
                    break;
                case 5:
                    Payment.getRevenue();
                    break;
                case 6:
                    Taxes.displayCurrentTaxes();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Choice. Try Again.");
            }
        }
    }

}