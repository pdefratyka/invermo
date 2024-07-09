module com.invermo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires com.invermo.business;

    opens com.invermo.application to javafx.fxml;
    opens com.invermo.application.gui.login to javafx.fxml;
    opens com.invermo.application.gui.dashboard to javafx.fxml;
    opens com.invermo.application.gui.portfolio.dto to javafx.base;

    opens com.invermo.application.gui.assets.views.creation to javafx.fxml;
    opens com.invermo.application.gui.assets.views.list to javafx.fxml;
    opens com.invermo.application.gui.assets.components to javafx.fxml;

    opens com.invermo.application.gui.portfolio.views to javafx.fxml;
    opens com.invermo.application.gui.portfolio.views.position to javafx.fxml;
    opens com.invermo.application.gui.portfolio.views.transaction to javafx.fxml;
    opens com.invermo.application.gui.portfolio.views.details to javafx.fxml;
    opens com.invermo.application.gui.components to javafx.fxml;

    opens com.invermo.application.gui.components.views to javafx.fxml;

    exports com.invermo.application;
    exports com.invermo.application.gui.login;
    exports com.invermo.application.gui.dashboard;
    exports com.invermo.application.gui.assets.views.creation;
    exports com.invermo.application.gui.assets.views.list;
    exports com.invermo.application.gui.assets.components;
    exports com.invermo.application.gui.components.views;
    exports com.invermo.application.gui.portfolio.views.position;
    exports com.invermo.application.gui.portfolio.views.transaction;
    exports com.invermo.application.gui.portfolio.views;
}