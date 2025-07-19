package com.example.demo4;

import java.io.*;
import java.util.Scanner;

public class Discount {
    public static void updateBill() {
        boolean found = false;
        System.out.print("Enter the bill ID: ");
        int targetID = sc.nextInt();

        // Step 1: Check if the ID exists
        try (BufferedReader br = new BufferedReader(new FileReader("billsdata.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith(targetID + " ")) {
                    found = true;
                    System.out.println("Bill ID " + targetID + " is found:");
                    break;
                }
            }
            if (!found) {
                System.out.println("Bill ID " + targetID + " not found.");
                return;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }


        System.out.print("Enter the discount in percentage: ");
        double discount = sc.nextDouble();
        if(discount <= 100 && discount >= 0) {
            updateDiscountAndRecalculate(targetID, discount);
            System.out.println("Bill updated to the file successfully");
        }
        }



    static Scanner sc = new Scanner(System.in);

    public static void updateDiscountAndRecalculate(int targetBillId, double newDiscount) {
        File inputFile = new File("billsdata.txt");
        File tempFile = new File("temp.txt");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s{2,}");

                if (parts.length >= 13) {
                    int billIdInLine = Integer.parseInt(parts[0]);

                    if (billIdInLine == targetBillId) {
                        // Parse data
                        String status = parts[1];
                        String name = parts[2];
                        String address = parts[3];
                        String cnic = parts[4];
                        int prevUnits = Integer.parseInt(parts[5]);
                        int currUnits = Integer.parseInt(parts[6]);
                        int unitsConsumed = Integer.parseInt(parts[7]);
                        int originalTotal = Integer.parseInt(parts[8]);
                        String category = parts[9];
                        // parts[10] = old discount
                        String dueDate = parts[11];
                        double fine = Double.parseDouble(parts[12]);

                        // Recalculate total bill with new discount
                        double discountAmount = originalTotal * (newDiscount / 100.0);
                        int newTotal = (int) (originalTotal - discountAmount + fine);

                        // Write updated line
                        String updatedLine = String.format(
                                "%-10d%-10s%-20s%-30s%-20s%-15d%-15d%-15d%-15d%-15s%-10.2f%-15s%-10.2f%n",
                                billIdInLine, status, name, address, cnic,
                                prevUnits, currUnits, unitsConsumed, newTotal,
                                category, newDiscount, dueDate, fine
                        );

                        writer.write(updatedLine);
                        continue;
                    }
                }

                // Copy line as-is if not matching or malformed
                writer.write(line + System.lineSeparator());
            }

        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
            return;
        }

        // Step 3: Replace file
        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Failed to rename temp file.");
            }
        } else {
            System.out.println("Failed to delete original file.");
        }
    }
}
