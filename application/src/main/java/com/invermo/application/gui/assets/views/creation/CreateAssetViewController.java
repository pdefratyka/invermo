package com.invermo.application.gui.assets.views.creation;

import com.invermo.application.gui.assets.views.list.AssetsViewController;
import com.invermo.business.enumeration.AssetType;
import com.invermo.business.domain.Asset;
import com.invermo.business.enumeration.Currency;
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
    private ChoiceBox<String> currencyPicker;
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
        initializeCurrencyPicker();
    }

    public void setAsset(final Asset asset, boolean isEditable) {
        this.asset = asset;
        this.assetNameValue.setText(asset.name());
        this.assetNameValue.setDisable(!isEditable);

        this.assetSymbolValue.setText(asset.symbol());
        this.assetSymbolValue.setDisable(!isEditable);

        this.assetTypePicker.setValue(asset.type().name());
        this.assetTypePicker.setDisable(!isEditable);

        this.currencyPicker.setValue(asset.currency().name());
        this.currencyPicker.setDisable(!isEditable);

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
        final Asset assetToSave = new Asset(null, assetNameValue.getText(), assetSymbolValue.getText(), AssetType.fromName(assetTypePicker.getValue()),
                Currency.valueOf(currencyPicker.getValue()));
        assetsViewController.saveAsset(assetToSave);
        refreshView();
    }

    private void updateAsset() {
        final Asset assetToSave = new Asset(asset.assetId(), assetNameValue.getText(), assetSymbolValue.getText(), AssetType.fromName(assetTypePicker.getValue()), Currency.valueOf(currencyPicker.getValue()));
        assetsViewController.saveAsset(assetToSave);
    }

    private void refreshView() {
        assetSymbolValue.setText("");
        assetNameValue.setText("");
        initializeAssetTypePicker();
        initializeCurrencyPicker();
    }

    private void initializeAssetTypePicker() {
        final ObservableList<String> assetTypeObservableList = FXCollections.observableArrayList();
        assetTypeObservableList.addAll(Arrays.stream(AssetType.values()).map(AssetType::getName).toList());
        assetTypePicker.setItems(assetTypeObservableList);
    }

    private void initializeCurrencyPicker() {
        final ObservableList<String> currencyObservableList = FXCollections.observableArrayList();
        currencyObservableList.addAll(Arrays.stream(Currency.values()).map(Currency::toString).toList());
        currencyPicker.setItems(currencyObservableList);
    }

    public void setAssetsViewController(final AssetsViewController assetsViewController) {
        this.assetsViewController = assetsViewController;
    }
}
