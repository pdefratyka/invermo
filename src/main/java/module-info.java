module com.invermo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;

    opens com.invermo to javafx.fxml;
    opens com.invermo.gui.login to javafx.fxml;
    opens com.invermo.gui.dashboard to javafx.fxml;
    opens com.invermo.gui.portfolio.dto to javafx.base;

    opens com.invermo.gui.assets.views.creation to javafx.fxml;
    opens com.invermo.gui.assets.views.list to javafx.fxml;
    opens com.invermo.gui.assets.components to javafx.fxml;

    opens com.invermo.gui.portfolio.views to javafx.fxml;
    opens com.invermo.gui.portfolio.views.position to javafx.fxml;
    opens com.invermo.gui.portfolio.views.transaction to javafx.fxml;
    opens com.invermo.gui.portfolio.views.details to javafx.fxml;
    opens com.invermo.gui.components to javafx.fxml;

    opens com.invermo.gui.components.views to javafx.fxml;

    exports com.invermo;
    exports com.invermo.gui.login;
    exports com.invermo.gui.dashboard;
    exports com.invermo.gui.assets.views.creation;
    exports com.invermo.gui.assets.views.list;
    exports com.invermo.gui.assets.components;
    exports com.invermo.gui.components.views;
    exports com.invermo.gui.portfolio.views.position;
    exports com.invermo.gui.portfolio.views.transaction;
    exports com.invermo.persistance.entity;
    exports com.invermo.gui.portfolio.views;
}