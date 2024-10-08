package com.invermo.application.gui.portfolio.views;

import com.invermo.application.gui.Views;
import com.invermo.application.gui.components.views.ToastMessageService;
import com.invermo.application.gui.components.views.ToastMessageType;
import com.invermo.application.gui.portfolio.views.details.PositionDetailsController;
import com.invermo.application.gui.portfolio.views.position.NewPositionController;
import com.invermo.application.service.ServiceManager;
import com.invermo.application.service.impl.AssetPriceService;
import com.invermo.application.service.impl.PortfolioService;
import com.invermo.application.service.impl.PositionService;
import com.invermo.business.domain.Position;
import com.invermo.business.domain.SinglePortfolioAsset;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
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
        assetsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {
                try {
                    onPositionSelection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
                                .positionId(singlePortfolioAsset.getPositionId())
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
    private void onRefreshPricesAction() throws IOException, GeneralSecurityException {
        try {
            final Map<String, Long> result = assetPriceService.updateAllAssetsPricesFromOneFile();
            final String resultMessage = constructUpdatePricesResultText(result);
            displayToastMessage(ToastMessageType.SUCCESS, "You successfully updated prices: " + resultMessage);
        } catch (Exception ex) {
            displayToastMessage(ToastMessageType.FAILURE, "There was a problem on updating prices");
            throw ex;
        }
    }

    private String constructUpdatePricesResultText(final Map<String, Long> result) {
        String message = "\n";
        for (Map.Entry<String, Long> entry : result.entrySet()) {
            message = message + entry.getKey() + ": " + entry.getValue() + "\n";
        }
        return message;
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

    @FXML
    private void onPositionSelection() throws IOException {
        final SinglePortfolioAsset singlePortfolioAsset = assetsTable.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.POSITION_DETAILS_VIEW_RESOURCE));
        Parent root = loader.load();
        PositionDetailsController positionDetailsController = loader.getController();
        positionDetailsController.setSinglePortfolioAsset(singlePortfolioAsset);
        positionDetailsController.customInitialize();

        Scene scene = new Scene(root, 1144, 800);
        Stage stage = new Stage();
        stage.setTitle(singlePortfolioAsset.getName() + " Details");
        stage.setScene(scene);
        Stage primaryStage = (Stage) assetsTable.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void displayToastMessage(final ToastMessageType toastMessageType, final String message) throws IOException {
        Stage primaryStage = (Stage) assetsTable.getScene().getWindow();
        ToastMessageService.displayToastMessage(primaryStage, toastMessageType, message);
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
                        setStyle("-fx-background-color: salmon;");
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
