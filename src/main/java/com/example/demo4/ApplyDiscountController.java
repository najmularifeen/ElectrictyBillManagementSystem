package com.example.demo4;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;

public class ApplyDiscountController {

    @FXML private TextField billIdField;
    @FXML private TextField discountField;
    @FXML private Label messageLabel;

    @FXML
    private void onApplyDiscount() {
        String billIdText = billIdField.getText().trim();
        String discountText = discountField.getText().trim();

        if (billIdText.isEmpty() || discountText.isEmpty()) {
            messageLabel.setText("Bill ID or Discount is empty.");
            return;
        }

        int billId;
        double discount;

        try {
            billId = Integer.parseInt(billIdText);
            discount = Double.parseDouble(discountText);
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid Bill ID or Discount.");
            return;
        }

        boolean billFound = false;
        boolean isPaid = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("billsdata.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(billIdText + " ")) {
                    billFound = true;

                    // Check status column (assumed to be at index 1)
                    String[] parts = line.split("\\s{2,}");  // 2+ spaces
                    if (parts.length > 1 && parts[1].equalsIgnoreCase("paid")) {
                        isPaid = true;
                    }
                    break;
                }
            }
        } catch (IOException e) {
            messageLabel.setText("Error reading bill data: " + e.getMessage());
            return;
        }

        if (!billFound) {
            messageLabel.setText("Bill ID not found.");
            return;
        }

        if (isPaid) {
            messageLabel.setText("Cannot apply discount. Bill is already paid.");
            return;
        }

        try {
            Discount.updateDiscountAndRecalculate(billId, discount);
            messageLabel.setText("Discount applied successfully and total bill updated.");
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error applying discount: " + e.getMessage());
        }
    }
}
