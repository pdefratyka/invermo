package com.invermo.application.gui.assets.components;

import com.invermo.application.gui.Views;
import com.invermo.application.gui.assets.views.creation.CreateAssetViewController;
import com.invermo.application.gui.assets.views.list.AssetsViewController;
import com.invermo.business.domain.Asset;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SingleAssetComponent extends Pane {

    private static final double PREF_HEIGHT = 50;

    private static final String BACKGROUND_COLOR_1 = "darkGray";
    private static final String BACKGROUND_COLOR_2 = "lightGray";
    private static final Background EVEN_RECORD_BACKGROUND = getBackground(BACKGROUND_COLOR_1);
    private static final Background ODD_RECORD_BACKGROUND = getBackground(BACKGROUND_COLOR_2);

    private static final double DEFAULT_FONT_SIZE = 15;
    private static final Font DEFAULT_FONT = new Font(DEFAULT_FONT_SIZE);

    private final VBox parentComponent;
    private final AssetsViewController parentController;
    private final Asset asset;
    private final int counter;
    private ContextMenu assetsOperationsMenu;


    public SingleAssetComponent(Asset asset, int counter, VBox parentComponent, AssetsViewController parentController) {
        this.counter = counter;
        this.asset = asset;
        this.parentComponent = parentComponent;
        this.parentController = parentController;
        setView();
    }

    private void setView() {
        setLayout();
        setComponentBackground();
        addAssetsTexts();
        addButtons();
        createAssetOperationsContextMenu();
    }

    private void setLayout() {
        setPrefHeight(PREF_HEIGHT);
    }

    private void setComponentBackground() {
        if (counter % 2 == 0) {
            super.setBackground(EVEN_RECORD_BACKGROUND);
        } else {
            super.setBackground(ODD_RECORD_BACKGROUND);
        }
    }

    private void addAssetsTexts() {
        final double layoutY = 5 + PREF_HEIGHT / 2;
        final double initialLayoutX = 50;
        final double columnWidth = 200;
        final double margin = 5;

        final Text assetName = new Text(asset.name());
        assetName.setLayoutY(layoutY);
        assetName.setLayoutX(initialLayoutX);
        assetName.setFont(DEFAULT_FONT);

        final Text assetSymbol = new Text(asset.symbol());
        assetSymbol.setLayoutY(layoutY);
        assetSymbol.setLayoutX(assetName.getLayoutX() + columnWidth + margin);
        assetSymbol.setFont(DEFAULT_FONT);

        final Text assetType = new Text(asset.type().getName());
        assetType.setLayoutY(layoutY);
        assetType.setLayoutX(assetSymbol.getLayoutX() + columnWidth + margin);
        assetType.setFont(DEFAULT_FONT);


        getChildren().add(assetName);
        getChildren().add(assetSymbol);
        getChildren().add(assetType);
    }

    private void addButtons() {
        final double marginRight = 50;
        final double buttonWidth = 50;
        final double contextMenuButtonWidth = 30;
        final double buttonHeight = 20;
        final double layoutY = (PREF_HEIGHT - buttonHeight) / 2 - 2;

        final Button contextMenuButton = new Button("...");
        contextMenuButton.setPrefWidth(contextMenuButtonWidth);
        contextMenuButton.setLayoutX(parentComponent.getPrefWidth() - contextMenuButton.getPrefWidth() - marginRight);
        contextMenuButton.setLayoutY(layoutY);
        contextMenuButton.setOnMouseClicked(event -> showContextMenu(contextMenuButton, event));

        final BackgroundFill sellBackgroundFill = new BackgroundFill(Paint.valueOf("red"), null, null);
        final Background sellBackground = new Background(sellBackgroundFill);
        final Button sellButton = new Button("Sell");
        sellButton.setBackground(sellBackground);
        sellButton.setPrefWidth(buttonWidth);
        sellButton.setLayoutX(contextMenuButton.getLayoutX() - sellButton.getPrefWidth() - marginRight);
        sellButton.setLayoutY(layoutY);
        sellButton.setOnAction(event -> onSell());

        final BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("green"), null, null);
        final Background background = new Background(backgroundFill);
        final Button buyButton = new Button("Buy");
        buyButton.setBackground(background);
        buyButton.setPrefWidth(buttonWidth);
        buyButton.setLayoutX(sellButton.getLayoutX() - buyButton.getPrefWidth() - marginRight);
        buyButton.setLayoutY(layoutY);
        buyButton.setOnAction(event -> onBuy());

        getChildren().add(buyButton);
        getChildren().add(sellButton);
        getChildren().add(contextMenuButton);
    }

    private void showContextMenu(final Button button, final MouseEvent mouseEvent) {
        assetsOperationsMenu.show(button, mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }

    private void createAssetOperationsContextMenu() {
        assetsOperationsMenu = new ContextMenu();
        final MenuItem viewItem = new MenuItem("View");
        viewItem.setOnAction(event -> onView());
        final MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> onEdit());
        final MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> onDelete());
        assetsOperationsMenu.getItems().addAll(viewItem, editItem, deleteItem);
    }

    private void onBuy() {
        System.out.println("On buy: " + counter);
    }

    private void onSell() {
        System.out.println("On sell: " + counter);
    }

    private void onView() {
        showAssetCreationModal(false);
    }

    private void onEdit() {
        showAssetCreationModal(true);
    }

    private void onDelete() {
        parentController.deleteAssetById(asset.assetId());
    }

    private void showAssetCreationModal(boolean isEditable) {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.CRATE_ASSET_VIEW_RESOURCE));
        final Parent root = loadResource(loader);

        Scene scene = new Scene(root, 640, 400);
        Stage stage = new Stage();
        stage.setTitle("Asset Creation");
        stage.setScene(scene);

        CreateAssetViewController controller = loader.getController();
        controller.setAsset(asset, isEditable);
        controller.setAssetsViewController(parentController);

        Stage primaryStage = (Stage) parentComponent.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private Parent loadResource(final FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException ex) {
            throw new RuntimeException("FXML not found", ex);
        }
    }

    private static Background getBackground(final String backgroundColor) {
        final BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf(backgroundColor), null, null);
        return new Background(backgroundFill);
    }
}
