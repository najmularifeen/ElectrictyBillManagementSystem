package com.example.demo4;

import  javafx.event.ActionEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class GenerateBill extends CallGenerateBill{

    Scanner sc = new Scanner(System.in);
    //int billId = new Random().nextInt(9000) + 1000; // Random 4-digit billId

    private String name, cnic, address, status = "unpaid";
    private int billId, previousUnits, currentUnits;
    private double discount = 0;

    private String dueDate;

    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getPreviousUnits() {
        return previousUnits;
    }

    public void setPreviousUnits(int previousUnits) {
        this.previousUnits = previousUnits;
    }

    public int getCurrentUnits() {
        return currentUnits;
    }

    public void setCurrentUnits(int currentUnits) {
        this.currentUnits = currentUnits;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private double fine = 0;
    private Category category;

    public GenerateBill(Category category) {
        this.category = category;
        this.billId = new Random().nextInt(1000000);

        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.dueDate = due.format(formatter);
    }

    public void inputDetails() {
        System.out.println("Bill ID: " + billId);

        System.out.print("Enter CNIC (13 digits, without dashes): ");
        String rawCnic = sc.next().trim();
        while (!rawCnic.matches("\\d{13}")) {
            System.out.print("Invalid CNIC. Enter again (exactly 13 digits, no dashes): ");
            rawCnic = sc.next().trim();
        }
        sc.nextLine(); // consume leftover newline

        // Auto-format CNIC with dashes: #####-#######-#
        cnic = rawCnic.substring(0, 5) + "-" + rawCnic.substring(5, 12) + "-" + rawCnic.substring(12);

        if (loadExistingCustomerData(cnic, category)) {
            System.out.println("\nPrevious record found. Auto-filled:");
            System.out.println("Name: " + name);
            System.out.println("Address: " + address);
            System.out.println("Previous Units: " + previousUnits);
        } else {
            System.out.print("Enter Name: ");
            name = sc.nextLine().trim();
            while (name.isEmpty()) {
                System.out.print("Name cannot be empty. Enter again: ");
                name = sc.nextLine().trim();
            }

            System.out.print("Enter Address: ");
            address = sc.nextLine().trim();
            while (address.isEmpty()) {
                System.out.print("Address cannot be empty. Enter again: ");
                address = sc.nextLine().trim();
            }

            previousUnits = 0;
        }
    }


    public int ConsumedUnits() {
//        do {
//            System.out.print("\nPrevious Reading: ");    //previous reading from data base
//            previousUnits = sc.nextInt();
//        } while (previousUnits < 0);

        do {
            System.out.print("Current Reading: ");
            currentUnits = sc.nextInt();
        } while (currentUnits < previousUnits);

        return currentUnits - previousUnits;
    }


    public int BillWithoutTaxes(int unitsConsumed) {
        int uPrice = 0;
        // int catIndex = category.ordinal();

        if (category == Category.RESIDENTIAL) {
            if (unitsConsumed <= 200)
                uPrice = Taxes.getUnitPrices()[0][0];
            else if (unitsConsumed <= 500)
                uPrice = Taxes.getUnitPrices()[0][1];
            else if (unitsConsumed <= 1000)
                uPrice = Taxes.getUnitPrices()[0][2];
            else
                uPrice = Taxes.getUnitPrices()[0][3];

        } else if (category == Category.COMMERCIAL) {
            if (unitsConsumed <= 300)
                uPrice = Taxes.getUnitPrices()[1][0];
            else if (unitsConsumed <= 700)
                uPrice = Taxes.getUnitPrices()[1][1];
            else if (unitsConsumed <= 1200)
                uPrice = Taxes.getUnitPrices()[1][2];
            else
                uPrice = Taxes.getUnitPrices()[1][3];
        } else if (category == Category.INDUSTRY) {
            if (unitsConsumed <= 500)
                uPrice = Taxes.getUnitPrices()[2][0];
            else if (unitsConsumed <= 1000)
                uPrice = Taxes.getUnitPrices()[2][1];
            else if (unitsConsumed <= 2000)
                uPrice = Taxes.getUnitPrices()[2][2];
            else
                uPrice = Taxes.getUnitPrices()[2][3];
        }

        return unitsConsumed * uPrice; // bill without taxes showing also


    }
    double discountss=0.0;

    public double eidpackage(double billwithtaxes) {
        System.out.println(billwithtaxes);
        if (category == Category.RESIDENTIAL) {
           discountss = billwithtaxes * 0.4;
            //billwithtaxes -= discountss;
           // return discountss;
        } else if (category == Category.COMMERCIAL) {
            discountss=billwithtaxes*0.03;
            //billwithtaxes -= discountss;
           // return discountss;

        }
        else if(category == Category.INDUSTRY){
             discountss=billwithtaxes*0.05;
            //billwithtaxes -= discountss;
            //return discountss;
        }
        return discountss;
    }



    public int BillWithTaxes(int totalBill)
    {
        double fpaTax = totalBill * (Taxes.getFpa() / 100);
        double salesTaxAmount = totalBill * (Taxes.getSalesTax() / 100);
        totalBill = (int) (totalBill + Taxes.getPtv() + fpaTax + salesTaxAmount); //new method for taxes
        return totalBill;

    }

    public void WriteBilltoFile(int unitsConsumed, int totalBill,double discountss) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("billsdata.txt", true))) {

            writer.write(String.format(
                    "%-10d%-10s%-20s%-30s%-20s%-15d%-15d%-15d%-15d%-15s%-10.2f%-15s%-10.2f%-10.2f%n",
                    billId, status, name, address, cnic, previousUnits, currentUnits,
                    unitsConsumed, totalBill, category, discount, dueDate, fine,discountss));

        } catch (Exception e) {
            System.out.println("Error in making the bills...");
        }
    }

    public boolean loadExistingCustomerData(String inputCnic, Category inputCategory) {
        try (Scanner fileScanner = new Scanner(new File("billsdata.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.trim().split("\\s{2,}");

                String fileCnic = data[4];
                String fileCategory = data[9];

                if (fileCnic.equals(inputCnic) && fileCategory.equalsIgnoreCase(inputCategory.toString())) {
                    this.name = data[2];
                    this.address = data[3];
                    this.previousUnits = Integer.parseInt(data[5]);
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return false;
    }


    public void handleGenerate(ActionEvent actionEvent) {
    }
}