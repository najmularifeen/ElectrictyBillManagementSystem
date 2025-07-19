package com.example.demo4;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fine {
    public static void applyFineAfterDueDate() {
        File inputFile = new File("billsdata.txt");
        File tempFile = new File("temp.txt");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate today = LocalDate.now();

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s{2,}");

                if (parts.length >= 13) {
                    // Extract data
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
                    String dueDateStr = parts[11];
                    double fine = Double.parseDouble(parts[12]);

                    // Parse due date
                    LocalDate dueDate = LocalDate.parse(dueDateStr, formatter);

                    // Apply fine if overdue and unpaid
                    if (status.equalsIgnoreCase("unpaid") && today.isAfter(dueDate) && fine == 0.0) {
                        double calculatedFine;
                        if (totalBill < 3000)
                            calculatedFine = 300;
                        else if (totalBill < 7000)
                            calculatedFine = 600;
                        else if (totalBill < 12000)
                            calculatedFine = 900;
                        else
                            calculatedFine = 1200;

                        fine = calculatedFine;
                        totalBill += calculatedFine;
                    }

                    // Write formatted updated line
                    String updatedLine = String.format(
                            "%-10d%-10s%-20s%-30s%-20s%-15d%-15d%-15d%-15d%-15s%-10.2f%-15s%-10.2f%n",
                            billId, status, name, address, cnic,
                            prevUnits, currUnits, unitsConsumed,
                            totalBill, category, discount, dueDateStr, fine
                    );

                    writer.write(updatedLine);
                } else {
                    // Malformed line — write as-is and skip processing
                    System.out.println("Skipping malformed line: " + line);
                    writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
            return;
        }

        // Replace the original file
        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Failed to rename temp file.");
            }
        } else {
            System.out.println("Failed to delete original file.");
        }
        System.out.println("Applied fine after due date successfully.");
    }
}
