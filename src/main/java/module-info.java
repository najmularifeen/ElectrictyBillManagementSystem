module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.demo4 to javafx.fxml;
    exports com.example.demo4;
}