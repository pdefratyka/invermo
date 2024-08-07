package com.invermo.application.gui.portfolio.views.position;

import com.invermo.application.gui.portfolio.views.PortfolioViewController;
import com.invermo.application.service.ServiceManager;
import com.invermo.application.service.impl.AssetService;
import com.invermo.business.domain.Asset;
import com.invermo.business.domain.Position;
import com.invermo.business.enumeration.PositionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NewPositionController implements Initializable {

    @FXML
    private ChoiceBox<String> assetPicker;
    @FXML
    private ChoiceBox<String> positionTypePicker;

    private List<Asset> assets;
    private PortfolioViewController portfolioViewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final AssetService assetsService = ServiceManager.getAssetsService();
        this.assets = assetsService.getAllAssets();
        initializeAssetPicker();
        initializePositionTypePicker();
    }

    private void initializeAssetPicker() {
        final ObservableList<String> assetsObservableList = FXCollections.observableArrayList();
        assetsObservableList.addAll(assets.stream().map(Asset::name).toList());
        assetPicker.setItems(assetsObservableList);
    }

    private void initializePositionTypePicker() {
        final ObservableList<String> positionTypeObservableList = FXCollections.observableArrayList();
        positionTypeObservableList.addAll(Arrays.stream(PositionType.values()).map(PositionType::toString).toList());
        positionTypePicker.setItems(positionTypeObservableList);
    }

    @FXML
    private void savePosition() {
        final Long assetId = getAssetIdByAssetName(assetPicker.getValue());
        final Position position = Position.builder()
                .assetId(assetId)
                .positionType(PositionType.valueOf(positionTypePicker.getValue()))
                .build();
        portfolioViewController.savePosition(position);
    }

    private Long getAssetIdByAssetName(final String assetName) {
        return assets.stream()
                .filter(asset -> asset.name().equals(assetName))
                .map(Asset::assetId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Asset with given name not found"));
    }

    public void setPortfolioViewController(final PortfolioViewController portfolioViewController) {
        this.portfolioViewController = portfolioViewController;
    }
}
