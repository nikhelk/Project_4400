module com.example.brand_new_4400 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.brand_new_4400 to javafx.fxml;
    exports com.example.brand_new_4400;
}