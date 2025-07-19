package com.example.demo4;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class ApplyLateFineController {

    @FXML
    private Label messageLabel;

    @FXML
    private void onApplyFineAutomatically() {
        try {
            Fine.applyFineAfterDueDate();
            messageLabel.setText("Fine was applied successfully to all overdue and unpaid bills.");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fine Application");
            alert.setHeaderText("Automatic Fine Applied");
            alert.setContentText("Fine was applied successfully to all overdue and unpaid bills.");
            alert.showAndWait();

        } catch (Exception e) {
            messageLabel.setText("Error applying fine: " + e.getMessage());
        }
    }
}
