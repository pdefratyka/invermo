package com.invermo.gui.assets.views.creation;

import com.invermo.gui.assets.views.list.AssetsViewController;
import com.invermo.persistance.entity.Asset;
import com.invermo.persistance.enumeration.AssetType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


public class CreateAssetViewController implements Initializable {

    @FXML
    private ChoiceBox<String> assetTypePicker;
    @FXML
    private TextField assetSymbolValue;
    @FXML
    private TextField assetNameValue;
    @FXML
    private Button saveAssetButton;

    private Asset asset;
    private AssetsViewController assetsViewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAssetTypePicker();
    }

    public void setAsset(final Asset asset, boolean isEditable) {
        this.asset = asset;
        this.assetNameValue.setText(asset.name());
        this.assetNameValue.setDisable(!isEditable);

        this.assetSymbolValue.setText(asset.symbol());
        this.assetSymbolValue.setDisable(!isEditable);

        this.assetTypePicker.setValue(asset.type().name());
        this.assetTypePicker.setDisable(!isEditable);

        this.saveAssetButton.setVisible(isEditable);
    }

    public void saveAsset() {
        if (this.asset == null) {
            saveNewAsset();
        } else {
            updateAsset();
        }
    }

    private void saveNewAsset() {
        final Asset assetToSave = new Asset(null, assetNameValue.getText(), assetSymbolValue.getText(), AssetType.fromName(assetTypePicker.getValue()));
        assetsViewController.saveAsset(assetToSave);
        refreshView();
    }

    private void updateAsset() {
        final Asset assetToSave = new Asset(asset.assetId(), assetNameValue.getText(), assetSymbolValue.getText(), AssetType.fromName(assetTypePicker.getValue()));
        assetsViewController.saveAsset(assetToSave);
    }

    private void refreshView() {
        assetSymbolValue.setText("");
        assetNameValue.setText("");
        initializeAssetTypePicker();
    }

    private void initializeAssetTypePicker() {
        final ObservableList<String> assetTypeObservableList = FXCollections.observableArrayList();
        assetTypeObservableList.addAll(Arrays.stream(AssetType.values()).map(AssetType::getName).toList());
        assetTypePicker.setItems(assetTypeObservableList);
    }

    public void setAssetsViewController(final AssetsViewController assetsViewController) {
        this.assetsViewController = assetsViewController;
    }
}
