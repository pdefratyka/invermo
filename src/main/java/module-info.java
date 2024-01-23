module com.invermo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.invermo to javafx.fxml;
    exports com.invermo;
}