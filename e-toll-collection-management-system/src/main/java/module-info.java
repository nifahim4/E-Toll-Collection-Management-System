module com.example.etollcollectionmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.etollcollectionmanagementsystem to javafx.fxml;
    exports com.example.etollcollectionmanagementsystem;
}