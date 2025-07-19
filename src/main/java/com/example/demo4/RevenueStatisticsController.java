package com.example.demo4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;

public class RevenueStatisticsController {
    @FXML private Label revenueLabel;

    @FXML
    public void initialize() {
        int totalRevenue = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("billsdata.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s{2,}");
                if (parts.length >= 10 && "paid".equalsIgnoreCase(parts[1])) {
                    totalRevenue += Integer.parseInt(parts[8]);  // Bill amount
                }
            }
        } catch (Exception e) {
            revenueLabel.setText("Error reading revenue.");
            return;
        }

        revenueLabel.setText(totalRevenue + " PKR");
    }
}
