package com.example.demo4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.util.Scanner;

public class GenerateBillController {

    @FXML
    private TextField cnicField, nameField, addressField, currentUnitField, previousUnitField;
    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private Button generateButton;

    private Category category;

    @FXML
    public void initialize() {
        categoryBox.getItems().addAll("RESIDENTIAL", "COMMERCIAL", "INDUSTRY");
        previousUnitField.setEditable(false);
    }

    @FXML
    void onCheckCnic(ActionEvent event) {
        String rawCnic = cnicField.getText().trim();

        if (!rawCnic.matches("\\d{13}")) {
            showAlert("Invalid CNIC", "Please enter exactly 13 digits (no dashes).");
            return;
        }

        if (categoryBox.getValue() == null) {
            showAlert("Category Missing", "Please select a category.");
            return;
        }

        category = Category.valueOf(categoryBox.getValue());

        String formattedCnic = formatCnic(rawCnic);
        File file = new File("billsdata.txt");

        // If file doesn't exist, it's the first bill
        if (!file.exists()) {
            showAlert("First Bill", "No file found. This is the first bill. Please enter name, address.");
            previousUnitField.setText("0");
            previousUnitField.setEditable(false);
            nameField.clear();
            addressField.clear();
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            boolean found = false;
            String lastMatch = null;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.contains(formattedCnic) && line.contains(category.toString())) {
                    lastMatch = line;
                    found = true;
                }
            }

            if (found && lastMatch != null) {
                String[] data = lastMatch.trim().split("\\s{2,}");
                nameField.setText(data[2]);
                addressField.setText(data[3]);
                int lastCurrentUnit = Integer.parseInt(data[6]);
                previousUnitField.setText(String.valueOf(lastCurrentUnit));
                previousUnitField.setEditable(false);
            } else {
                showAlert("New Customer", "No previous record found. Please enter name, address. Previous Units set to 0.");
                previousUnitField.setText("0");
                previousUnitField.setEditable(false);
                nameField.clear();
                addressField.clear();
            }

        } catch (Exception e) {
            showAlert("Error", "Something went wrong while reading the file.");
            e.printStackTrace();
        }
    }


    @FXML
    void onGenerateBill(ActionEvent event)
    {
        try {
            category = Category.valueOf(categoryBox.getValue());
            GenerateBill bill = new GenerateBill(category);

            bill.setCnic(formatCnic(cnicField.getText()));
            bill.setName(nameField.getText());
            bill.setAddress(addressField.getText());
            bill.setPreviousUnits(Integer.parseInt(previousUnitField.getText()));
            bill.setCurrentUnits(Integer.parseInt(currentUnitField.getText()));

            if (bill.getCurrentUnits() < bill.getPreviousUnits()) {
                showAlert("Invalid Input", "Current units cannot be less than previous units.");
                return;
            }

            int unitsConsumed = bill.getCurrentUnits() - bill.getPreviousUnits();
            int base = bill.BillWithoutTaxes(unitsConsumed);
            int total = bill.BillWithTaxes(base);
            double discountss=bill.eidpackage(total);
            bill.WriteBilltoFile(unitsConsumed, total,discountss);

            showAlert("Success", "Bill Generated Successfully!");

            // Reset fields
            cnicField.clear();
            nameField.clear();
            addressField.clear();
            currentUnitField.clear();
            previousUnitField.clear();
            categoryBox.getSelectionModel();

        } catch (Exception e) {
            showAlert("Error", "Please make sure all fields are filled correctly.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String formatCnic(String rawCnic) {
        return rawCnic.substring(0, 5) + "-" +
                rawCnic.substring(5, 12) + "-" +
                rawCnic.substring(12);
    }
}
