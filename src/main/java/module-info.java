module com.example.albombpechatleniu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.albombpechatleniu to javafx.fxml;
    exports com.example.albombpechatleniu;
}