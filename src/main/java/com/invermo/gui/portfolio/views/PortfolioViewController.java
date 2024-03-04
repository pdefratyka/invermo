package com.invermo.gui.portfolio.views;

import com.invermo.gui.Views;
import com.invermo.gui.portfolio.dto.SinglePortfolioAsset;
import com.invermo.gui.portfolio.views.position.NewPositionController;
import com.invermo.persistance.entity.Position;
import com.invermo.service.PortfolioService;
import com.invermo.service.PositionService;
import com.invermo.service.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PortfolioViewController implements Initializable {

    @FXML
    private TableView<SinglePortfolioAsset> assetsTable;
    private PortfolioService portfolioService;
    private PositionService positionService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.portfolioService = ServiceManager.getPortfolioService();
        this.positionService = ServiceManager.getPositionService();
        configureColumns();
        initializeData();
    }

    public void initializeData() {
        final List<SinglePortfolioAsset> assets = portfolioService.getPortfolioAssets();
        final ObservableList<SinglePortfolioAsset> singlePortfolioAssets = FXCollections.observableArrayList();
        singlePortfolioAssets.addAll(assets);
        assetsTable.setItems(singlePortfolioAssets);
    }

    public void savePosition(final Position position) {
        positionService.addNewPosition(position);
        initializeData();
    }

    @FXML
    private void onAddPositionAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.NEW_POSITION_VIEW_RESOURCE));
        Parent root = loader.load();
        NewPositionController newPositionController = loader.getController();
        newPositionController.setPortfolioViewController(this);
        Scene scene = new Scene(root, 296, 350);
        Stage stage = new Stage();
        stage.setTitle("Position Creation");
        stage.setScene(scene);
        Stage primaryStage = (Stage) assetsTable.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    private void onAddTransactionAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.NEW_TRANSACTION_VIEW_RESOURCE));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1144, 700);
        Stage stage = new Stage();
        stage.setTitle("Transaction Creation");
        stage.setScene(scene);
        Stage primaryStage = (Stage) assetsTable.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void configureColumns() {
        TableColumn<SinglePortfolioAsset, BigDecimal> tableColumn = (TableColumn<SinglePortfolioAsset, BigDecimal>) assetsTable.getColumns().get(6);

        tableColumn.setCellFactory(cell -> {
            return new TextFieldTableCell<SinglePortfolioAsset, BigDecimal>() {
                @Override
                public void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.compareTo(BigDecimal.ZERO) > 0) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else if (item != null) {
                        setStyle("-fx-background-color: red;");
                    }
                }
            };
        });
    }
}
