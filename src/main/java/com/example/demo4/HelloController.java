package com.example.demo4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    private void handleGenerate(ActionEvent event) {
        updateStatus("Opening Generate Bill window...");
        openWindow("/com/example/demo4/GenerateBill.fxml", "Generate Bill");
    }

    @FXML
    private void handleDiscount() {
        updateStatus("Opening Apply Discount window...");
        openWindow("/com/example/demo4/apply-discount.fxml", "Apply Discount");
    }

    @FXML
    private void handleLatePayment() {
        updateStatus("Opening Apply Late Payment Fine window...");
        openWindow("/com/example/demo4/apply-Late-fine.fxml", "Apply Late Payment Fine");
    }

    @FXML
    private void handlePay() {
        updateStatus("Opening Pay Bill window...");
        openWindow("/com/example/demo4/pay-bill.fxml", "Pay Bill");
    }

    @FXML
    private void handleRevenue() {
        updateStatus("Opening Revenue Statistics window...");
        openWindow("/com/example/demo4/revenue-statistics.fxml", "Revenue Statistics");
    }

    @FXML
    private void handleTaxes() {
        updateStatus("Opening Taxes window...");
        openWindow("/com/example/demo4/taxes.fxml", "Taxes");
    }

    @FXML
    private void handleExit() {
        updateStatus("Exiting application...");
        System.exit(0);
    }

    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            updateStatus("Error: Could not load " + title + " window.");
        }
    }

    private void updateStatus(String message) {
        if (welcomeText != null) {
            welcomeText.setText(message);
        }
    }
}
