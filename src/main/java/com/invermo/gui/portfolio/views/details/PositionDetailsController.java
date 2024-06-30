package com.invermo.gui.portfolio.views.details;

import com.invermo.gui.portfolio.dto.SinglePortfolioAsset;
import com.invermo.gui.portfolio.dto.SingleTransaction;
import com.invermo.persistance.entity.Transaction;
import com.invermo.service.ServiceManager;
import com.invermo.service.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PositionDetailsController implements Initializable {

    @FXML
    private TableView<SingleTransaction> transactionsTable;

    private SinglePortfolioAsset singlePortfolioAsset;
    private TransactionService transactionService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void customInitialize() {
        this.transactionService = ServiceManager.getTransactionService();
        final List<Transaction> transactions = this.transactionService.getAllTransactionForPosition(singlePortfolioAsset.getPositionId());
        final List<SingleTransaction> singleTransactions = mapToSingleTransactions(transactions);
        final ObservableList<SingleTransaction> singleTransactionObservableList = FXCollections.observableArrayList();
        singleTransactionObservableList.addAll(singleTransactions);
        transactionsTable.setItems(singleTransactionObservableList);
        System.out.println(transactions);
    }

    public void setSinglePortfolioAsset(SinglePortfolioAsset singlePortfolioAsset) {
        this.singlePortfolioAsset = singlePortfolioAsset;
    }

    private List<SingleTransaction> mapToSingleTransactions(final List<Transaction> transactions) {
        final List<SingleTransaction> singleTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            SingleTransaction singleTransaction = SingleTransaction.builder()
                    .transactionId(transaction.getTransactionId())
                    .dateTime(transaction.getDateTime())
                    .transactionType(transaction.getTransactionType().name())
                    .number(transaction.getNumberOfAsset())
                    .price(String.format("%,.2f", transaction.getPrice().multiply(transaction.getNumberOfAsset()).multiply(transaction.getCurrencyExchangeRate())))
                    .pricePerOne(String.format("%,.2f",transaction.getPrice().multiply(transaction.getCurrencyExchangeRate())))
                    .build();
            singleTransactions.add(singleTransaction);
        }
        return singleTransactions;
    }
}
