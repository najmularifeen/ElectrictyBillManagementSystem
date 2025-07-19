package com.example.demo4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;

public class PayBillController {
    @FXML private TextField billIdField;
    @FXML private Label messageLabel;

    @FXML
    private void handlePayBill() {
        String billIdInput = billIdField.getText().trim();
        boolean found = false;

        try {
            File inputFile = new File("billsdata.txt");
            File tempFile = new File("billsdata_temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    writer.write(line);
                    writer.newLine();
                    continue;
                }

                if (line.startsWith(billIdInput + " ")) {
                    // Replace "unpaid" with "paid", only the first occurrence to avoid modifying values in names, etc.
                    String updatedLine = line.replaceFirst("\\bunpaid\\b", "paid    ");
                    writer.write(updatedLine);
                    found = true;
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            reader.close();
            writer.close();

            if (inputFile.delete() && tempFile.renameTo(inputFile)) {
                messageLabel.setText(found ? "Bill paid successfully!" : "Bill ID not found.");
            } else {
                messageLabel.setText("Error updating bill.");
            }

        } catch (IOException e) {
            messageLabel.setText("An error occurred: " + e.getMessage());
        }
    }
}
