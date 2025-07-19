package com.example.demo4;

import java.io.*;
import java.util.Scanner;

public class Payment {
    static Scanner sc = new Scanner(System.in);
    public static double revenue = 0; // Static revenue field (stored in memory)

    public static void payBillById() {
        boolean found = false;
        System.out.print("Enter the bill ID to pay: ");
        int targetID = sc.nextInt();

        File inputFile = new File("billsdata.txt");
        File tempFile = new File("temp.txt");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s{2,}"); // Split by 2+ spaces

                if (parts.length >= 13) {
                    int billId = Integer.parseInt(parts[0]);
                    String status = parts[1];
                    String name = parts[2];
                    String address = parts[3];
                    String cnic = parts[4];
                    int prevUnits = Integer.parseInt(parts[5]);
                    int currUnits = Integer.parseInt(parts[6]);
                    int unitsConsumed = Integer.parseInt(parts[7]);
                    int totalBill = Integer.parseInt(parts[8]);
                    String category = parts[9];
                    double discount = Double.parseDouble(parts[10]);
                    String dueDate = parts[11];
                    double fine = Double.parseDouble(parts[12]);

                    if (billId == targetID) {
                        if (status.equalsIgnoreCase("paid")) {
                            System.out.println("Bill is already marked as PAID.");
                        } else {
                            status = "paid";
                            found = true;
                            revenue += totalBill; // Add to revenue on successful payment
                            System.out.println("Payment successful for Bill ID: " + targetID);
                        }
                    }

                    String updatedLine = String.format(
                            "%-10d%-10s%-20s%-30s%-20s%-15d%-15d%-15d%-15d%-15s%-10.2f%-15s%-10.2f%n",
                            billId, status, name, address, cnic,
                            prevUnits, currUnits, unitsConsumed,
                            totalBill, category, discount, dueDate, fine
                    );

                    writer.write(updatedLine);
                } else {
                    writer.write(line + System.lineSeparator()); // Write malformed line as-is
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
            return;
        }

        if (!found) {
            System.out.println("Bill ID " + targetID + " not found.");
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Failed to rename temp file after payment.");
            }
        } else {
            System.out.println("Failed to delete original file after payment.");
        }
    }

    // Method to display total revenue
    public static void getRevenue() {
        System.out.printf("\nTotal Revenue Collected: Rs. %.2f%n", revenue);
    }
}
