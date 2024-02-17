package com.invermo.gui.portfolio.views.transaction;

import com.invermo.persistance.entity.Position;
import com.invermo.persistance.entity.PositionWithAsset;
import com.invermo.persistance.entity.Transaction;
import com.invermo.persistance.enumeration.TransactionType;
import com.invermo.service.PositionService;
import com.invermo.service.ServiceManager;
import com.invermo.service.TransactionService;
import com.invermo.utils.DateParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NewTransactionController implements Initializable {

    @FXML
    private ChoiceBox<String> positionPicker;
    @FXML
    private ChoiceBox<String> transactionTypePicker;
    @FXML
    private TextField date;
    @FXML
    private TextField numberOfAssets;
    @FXML
    private TextField pricePerOne;
    @FXML
    private TextField currencyExchange;
    private List<PositionWithAsset> positions;
    private TransactionService transactionService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final PositionService positionService = ServiceManager.getPositionService();
        this.transactionService = ServiceManager.getTransactionService();
        this.positions = positionService.getPositionsWithAssetsForUser();
        initPositionPicker();
        initTransactionTypePicker();
    }

    @FXML
    private void saveTransaction() {
        final Transaction transaction = Transaction.builder()
                .positionId(findPositionIdByAssetNameAndType(positionPicker.getValue()))
                .currencyExchangeRate(BigDecimal.valueOf(Double.parseDouble(currencyExchange.getText())))
                .numberOfAsset(BigDecimal.valueOf(Double.parseDouble(numberOfAssets.getText())))
                .price(BigDecimal.valueOf(Double.parseDouble(pricePerOne.getText())))
                .dateTime(DateParser.parseStringToLocalDateTime(date.getText()))
                .transactionType(TransactionType.valueOf(transactionTypePicker.getValue()))
                .build();
        transactionService.saveTransaction(transaction);
    }

    private void initPositionPicker() {
        final ObservableList<String> positionsObservableList = FXCollections.observableArrayList();
        positionsObservableList.addAll(positions.stream().map(p->p.getAssetName()+","+p.getPositionType()).toList());
        positionPicker.setItems(positionsObservableList);
    }

    private void initTransactionTypePicker() {
        final ObservableList<String> transactionTypeObservableList = FXCollections.observableArrayList();
        transactionTypeObservableList.addAll(Arrays.stream(TransactionType.values()).map(TransactionType::name).toList());
        transactionTypePicker.setItems(transactionTypeObservableList);
    }

    private Long findPositionIdByAssetNameAndType(String assetNameAndType){
        final String assetName=assetNameAndType.split(",")[0];
        final String positionType=assetNameAndType.split(",")[1];

        return positions.stream()
                .filter(p->p.getAssetName().equals(assetName))
                .filter(p->p.getPositionType().name().equals(positionType))
                .map(PositionWithAsset::getPositionId)
                .findFirst()
                .orElseThrow(()->new RuntimeException("Position not found"));
    }
}
