module com.example.pharmacymanagementsystemfullapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pharmacymanagementsystemfullapp to javafx.fxml;
    exports com.example.pharmacymanagementsystemfullapp;
}