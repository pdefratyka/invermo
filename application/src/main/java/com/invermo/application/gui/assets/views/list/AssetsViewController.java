package com.invermo.application.gui.assets.views.list;

import com.invermo.application.gui.Views;
import com.invermo.application.gui.assets.components.SingleAssetComponent;
import com.invermo.application.gui.assets.views.creation.CreateAssetViewController;
import com.invermo.application.service.ServiceManager;
import com.invermo.application.service.impl.AssetService;
import com.invermo.business.domain.Asset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AssetsViewController implements Initializable {

    @FXML
    private VBox assetsList;

    @FXML
    private TextField searchField;

    private AssetService assetsService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assetsService = ServiceManager.getAssetsService();
        refreshAssetsList();
    }

    public void deleteAssetById(final Long id) {
        assetsService.deleteAssetById(id);
        refreshAssetsList();
    }

    public void saveAsset(final Asset asset) {
        assetsService.saveAsset(asset);
        refreshAssetsList();
    }

    public void searchAssets() {
        final String searchParam = searchField.getText();
        final List<Asset> assets = assetsService.getAssetsBySearchParam(searchParam);
        addAssetsToList(assets);
    }

    public void onAddAssetAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.CRATE_ASSET_VIEW_RESOURCE));
        Parent root = loader.load();
        Scene scene = new Scene(root, 640, 400);
        Stage stage = new Stage();
        stage.setTitle("Asset Creation");
        stage.setScene(scene);
        CreateAssetViewController createAssetViewController = loader.getController();
        createAssetViewController.setAssetsViewController(this);
        Stage primaryStage = (Stage) assetsList.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void addAssetsToList(final List<Asset> assets) {
        int counter = 0;
        assetsList.getChildren().clear();
        for (Asset asset : assets) {
            SingleAssetComponent singleAssetComponent = new SingleAssetComponent(asset, counter, assetsList, this);
            assetsList.getChildren().add(singleAssetComponent);
            counter++;
        }
    }

    private void refreshAssetsList() {
        final List<Asset> assets = assetsService.getAllAssets();
        addAssetsToList(assets);
    }
}
