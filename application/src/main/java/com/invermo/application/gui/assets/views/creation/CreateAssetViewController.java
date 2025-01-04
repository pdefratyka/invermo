package com.invermo.application.gui.assets.views.creation;

import com.invermo.application.gui.assets.views.list.AssetsViewController;
import com.invermo.business.domain.Asset;
import com.invermo.business.domain.Category;
import com.invermo.business.enumeration.AssetType;
import com.invermo.business.enumeration.Currency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
public class CreateAssetViewController implements Initializable {

    @FXML
    private ChoiceBox<String> assetTypePicker;
    @FXML
    private ChoiceBox<String> currencyPicker;
    @FXML
    private ChoiceBox<String> mainCategoriesPicker;
    @FXML
    private ChoiceBox<String> childCategoryPicker;
    @FXML
    private TextField assetSymbolValue;
    @FXML
    private TextField assetNameValue;
    @FXML
    private Button saveAssetButton;
    @FXML
    private Text assetCategories;

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

        initializeMainCategories();
        if (asset.assetId() != null) {
            initializeAssetCategories();
        }
    }

    public void saveAsset() {
        if (this.asset == null) {
            saveNewAsset();
        } else {
            updateAsset();
        }
    }

    public void saveCategory() {
        long categoryId = getSelectedCategoryId();
        log.info("Save category: {}", categoryId);
        saveAssetCategory(asset.assetId(), categoryId);
        initializeAssetCategories();
    }

    public void changeParentCategory() {
        log.info("Change parent category");
        initializeChildCategories();
    }

    private void saveNewAsset() {
        final Asset assetToSave = new Asset(null, assetNameValue.getText(), assetSymbolValue.getText(),
                AssetType.fromName(assetTypePicker.getValue()),
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

    public void initializeMainCategories() {
        final List<Category> mainCategories = assetsViewController.getAllMainCategories();
        final ObservableList<String> categoriesObservableList = FXCollections.observableArrayList();
        categoriesObservableList.addAll(mainCategories.stream().map(Category::getName).toList());
        mainCategoriesPicker.setItems(categoriesObservableList);
    }

    public void initializeChildCategories() {
        final List<Category> childCategories = assetsViewController.getChildCategoriesByParentId(getParentId());
        final ObservableList<String> categoriesObservableList = FXCollections.observableArrayList();
        categoriesObservableList.addAll(childCategories.stream().map(Category::getName).toList());
        childCategoryPicker.setItems(categoriesObservableList);
    }

    public void initializeAssetCategories() {
        final List<Category> categories = assetsViewController.getCategoriesByAssetId(asset.assetId());
        final String connectedCategories = String.join("; ", categories.stream().map(Category::getName).toList());
        assetCategories.setText(connectedCategories);
    }

    public void setAssetsViewController(final AssetsViewController assetsViewController) {
        this.assetsViewController = assetsViewController;
    }

    private long getParentId() {
        return assetsViewController.getAllMainCategories().stream()
                .filter(category -> category.getName().equals(mainCategoriesPicker.getValue()))
                .findFirst()
                .map(Category::getId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private long getSelectedCategoryId() {
        return assetsViewController.getChildCategoriesByParentId(getParentId()).stream()
                .filter(category -> category.getName().equals(childCategoryPicker.getValue()))
                .findFirst()
                .map(Category::getId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private void saveAssetCategory(long assetId, long categoryId) {
        assetsViewController.saveAssetCategory(assetId, categoryId);
    }
}
