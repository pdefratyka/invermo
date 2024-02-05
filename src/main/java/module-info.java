module com.invermo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;

    opens com.invermo to javafx.fxml;
    opens com.invermo.gui.login to javafx.fxml;
    opens com.invermo.gui.dashboard to javafx.fxml;
    opens com.invermo.gui.assets.views.creation to javafx.fxml;
    opens com.invermo.gui.assets.views.list to javafx.fxml;
    opens com.invermo.gui.assets.components to javafx.fxml;

    exports com.invermo;
    exports com.invermo.gui.login;
    exports com.invermo.gui.dashboard;
    exports com.invermo.gui.assets.views.creation;
    exports com.invermo.gui.assets.views.list;
    exports com.invermo.gui.assets.components;
    exports com.invermo.persistance.entity;
}