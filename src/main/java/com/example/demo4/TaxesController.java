package com.example.demo4;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TaxesController {

    @FXML private Label fpaLabel;
    @FXML private Label salesTaxLabel;
    @FXML private Label ptvLabel;

    @FXML private Button updateButton;

    @FXML private TextField fpaField;
    @FXML private TextField salesTaxField;
    @FXML private TextField ptvField;

    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        updateLabels();
    }

    @FXML
    private void onUpdateTaxes() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Tax Update");
        confirm.setHeaderText("Do you want to change the taxes?");
        confirm.setContentText("Choose your option.");

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");

        confirm.getButtonTypes().setAll(yes, no);

        confirm.showAndWait().ifPresent(response -> {
            if (response == yes) {
                fpaField.setDisable(false);
                salesTaxField.setDisable(false);
                ptvField.setDisable(false);
                updateButton.setText("Submit New Taxes");
                updateButton.setOnAction(e -> submitNewTaxes());
            } else {
                messageLabel.setText("No changes made.");
            }
        });
    }

    private void submitNewTaxes() {
        try {
            double newFpa = Double.parseDouble(fpaField.getText());
            double newSalesTax = Double.parseDouble(salesTaxField.getText());
            int newPtv = Integer.parseInt(ptvField.getText());

            Taxes.setFpa(newFpa);
            Taxes.setSalesTax(newSalesTax);
            Taxes.setPtv(newPtv);

            messageLabel.setText("Taxes updated successfully.");
            updateLabels();

            // Reset state
            fpaField.setDisable(true);
            salesTaxField.setDisable(true);
            ptvField.setDisable(true);
            updateButton.setText("Update Taxes");
            updateButton.setOnAction(e -> onUpdateTaxes());
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter valid numbers.");
        }
    }

    private void updateLabels() {
        fpaLabel.setText(Taxes.getFpa() + " %");
        salesTaxLabel.setText(Taxes.getSalesTax() + " %");
        ptvLabel.setText(Taxes.getPtv() + " PKR");

        fpaField.setText(String.valueOf(Taxes.getFpa()));
        salesTaxField.setText(String.valueOf(Taxes.getSalesTax()));
        ptvField.setText(String.valueOf(Taxes.getPtv()));

        fpaField.setDisable(true);
        salesTaxField.setDisable(true);
        ptvField.setDisable(true);
    }
}
