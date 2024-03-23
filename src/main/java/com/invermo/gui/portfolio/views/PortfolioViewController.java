package com.invermo.gui.portfolio.views;

import com.invermo.gui.Views;
import com.invermo.gui.portfolio.dto.SinglePortfolioAsset;
import com.invermo.gui.portfolio.views.position.NewPositionController;
import com.invermo.persistance.entity.Position;
import com.invermo.service.AssetPriceService;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PortfolioViewController implements Initializable {

    @FXML
    private TableView<SinglePortfolioAsset> assetsTable;
    @FXML
    private Label priceSummary;
    @FXML
    private Label valueSummary;
    @FXML
    private Label gainSummary;
    @FXML
    private Label percentageGainSummary;
    private PortfolioService portfolioService;
    private PositionService positionService;
    private AssetPriceService assetPriceService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.portfolioService = ServiceManager.getPortfolioService();
        this.positionService = ServiceManager.getPositionService();
        this.assetPriceService = ServiceManager.getAssetPriceService();
        configureColumns();
        initializeData();
        initializeSummary();
    }

    public void initializeData() {
        final List<SinglePortfolioAsset> assets = portfolioService.getPortfolioAssets();
        final List<SinglePortfolioAsset> styledAssets = styleSinglePortfolioAssets(assets);
        final ObservableList<SinglePortfolioAsset> singlePortfolioAssets = FXCollections.observableArrayList();
        singlePortfolioAssets.addAll(styledAssets);
        assetsTable.setItems(singlePortfolioAssets);
    }

    public void savePosition(final Position position) {
        positionService.addNewPosition(position);
        initializeData();
    }

    private void initializeSummary() {
        final List<SinglePortfolioAsset> singlePortfolioAssets = assetsTable.getItems();
        // initialize priceSummary

        // Add this logic to seperate service
        BigDecimal value = BigDecimal.ZERO;
        BigDecimal price = BigDecimal.ZERO;
        for (SinglePortfolioAsset singlePortfolioAsset : singlePortfolioAssets) {
            value = value.add(singlePortfolioAsset.getValue());
            price = price.add(singlePortfolioAsset.getPrice());
        }
        final BigDecimal gain = value.subtract(price);
        if (price.compareTo(BigDecimal.ZERO) > 0) {
            final BigDecimal percentageGain = gain.divide(price, 6, RoundingMode.FLOOR).multiply(BigDecimal.valueOf(100));
            percentageGainSummary.setText(String.format("%,.2f", percentageGain));
        }
        valueSummary.setText(String.format("%,.2f", value));
        priceSummary.setText(String.format("%,.2f", price));
        gainSummary.setText(String.format("%,.2f", gain));
    }

    private List<SinglePortfolioAsset> styleSinglePortfolioAssets(final List<SinglePortfolioAsset> assets) {
        return assets.stream()
                .map(singlePortfolioAsset ->
                        SinglePortfolioAsset.builder()
                                .name(singlePortfolioAsset.getName())
                                .assetType(singlePortfolioAsset.getAssetType())
                                .positionType(singlePortfolioAsset.getPositionType())
                                .number(singlePortfolioAsset.getNumber())
                                .price(roundBigDecimal(singlePortfolioAsset.getPrice()))
                                .value(roundBigDecimal(singlePortfolioAsset.getValue()))
                                .gain(roundBigDecimal(singlePortfolioAsset.getGain()))
                                .percentageGain(roundBigDecimal(singlePortfolioAsset.getPercentageGain()))
                                .percentagePortfolioPart(roundBigDecimal(singlePortfolioAsset.getPercentagePortfolioPart()))
                                .build()
                ).toList();
    }

    private BigDecimal roundBigDecimal(final BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value.setScale(4, RoundingMode.FLOOR);
    }

    @FXML
    private void onRefreshPricesAction() {
        System.out.println("On refresh prices");
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            assetPriceService.updateAllAssetsPricesFromOneFile(file.getAbsolutePath());
        }
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
        TableColumn<SinglePortfolioAsset, BigDecimal> priceColumn = (TableColumn<SinglePortfolioAsset, BigDecimal>) assetsTable.getColumns().get(4);
        TableColumn<SinglePortfolioAsset, BigDecimal> valueColumn = (TableColumn<SinglePortfolioAsset, BigDecimal>) assetsTable.getColumns().get(5);

        tableColumn.setCellFactory(cell -> {
            return new TextFieldTableCell<SinglePortfolioAsset, BigDecimal>() {
                @Override
                public void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.compareTo(BigDecimal.ZERO) > 0) {
                        setStyle("-fx-background-color: lightgreen;");
                        setText(String.format("%,.2f", item));
                    } else if (item != null) {
                        setStyle("-fx-background-color: red;");
                        setText(String.format("%,.2f", item));
                    }
                }
            };
        });

        priceColumn.setCellFactory(cell -> {
            return new TextFieldTableCell<SinglePortfolioAsset, BigDecimal>() {
                @Override
                public void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.format("%,.2f", item));
                    }
                }
            };
        });

        valueColumn.setCellFactory(cell -> {
            return new TextFieldTableCell<SinglePortfolioAsset, BigDecimal>() {
                @Override
                public void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.format("%,.2f", item));
                    }
                }
            };
        });
    }
}
