package com.invermo.application.gui.portfolio.views.details;

import com.invermo.application.gui.facade.PositionDetailsFacade;
import com.invermo.application.gui.portfolio.dto.SinglePortfolioAsset;
import com.invermo.application.gui.portfolio.dto.SingleTransaction;
import com.invermo.application.gui.portfolio.dto.SingleTransactionRecord;
import com.invermo.application.service.ServiceManager;
import com.invermo.business.enumeration.TransactionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PositionDetailsController implements Initializable {

    @FXML
    private TableView<SingleTransaction> transactionsTable;

    private SinglePortfolioAsset singlePortfolioAsset;
    private PositionDetailsFacade positionDetailsFacade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void customInitialize() {
        this.positionDetailsFacade = ServiceManager.getPositionDetailsFacade();
        configureColumns();
        final List<SingleTransactionRecord> transactions = this.positionDetailsFacade.getSingleTransactionsForPosition(singlePortfolioAsset.getPositionId());
        final List<SingleTransaction> singleTransactions = mapToSingleTransactions(transactions);
        final ObservableList<SingleTransaction> singleTransactionObservableList = FXCollections.observableArrayList();
        singleTransactionObservableList.addAll(singleTransactions);
        transactionsTable.setItems(singleTransactionObservableList);

    }

    public void setSinglePortfolioAsset(SinglePortfolioAsset singlePortfolioAsset) {
        this.singlePortfolioAsset = singlePortfolioAsset;
    }

    private List<SingleTransaction> mapToSingleTransactions(final List<SingleTransactionRecord> singleTransactionRecords) {
        final List<SingleTransaction> singleTransactions = new ArrayList<>();
        for (SingleTransactionRecord transaction : singleTransactionRecords) {
            SingleTransaction singleTransaction;
            if (transaction.getTransaction().getTransactionType().equals(TransactionType.BUY)) {
                singleTransaction = SingleTransaction.builder()
                        .transactionId(transaction.getTransaction().getTransactionId())
                        .dateTime(transaction.getTransaction().getDateTime())
                        .transactionType(transaction.getTransaction().getTransactionType().name())
                        .number(formatNumberOfAsset(transaction.getTransaction().getNumberOfAsset()))
                        .pricePerOne(String.format("%,.2f", transaction.getPricePerOne()))
                        .price(String.format("%,.2f", transaction.getPrice()))
                        .numberOfSold(formatNumberOfAsset(transaction.getNumberOfSold()))
                        .numberOfActive(formatNumberOfAsset(transaction.getNumberOfActive()))
                        .realizedGain(String.format("%,.2f", transaction.getRealizedGain()))
                        .activeGain(String.format("%,.2f", transaction.getActiveGain()))
                        .allGain(String.format("%,.2f", transaction.getAllGain()))
                        .percentageGain(String.format("%,.2f", transaction.getPercentageGain()))
                        .part(String.format("%,.2f", transaction.getPart()))
                        .isActive(transaction.isActive())
                        .build();
            } else {
                singleTransaction = SingleTransaction.builder()
                        .transactionId(transaction.getTransaction().getTransactionId())
                        .dateTime(transaction.getTransaction().getDateTime())
                        .transactionType(transaction.getTransaction().getTransactionType().name())
                        .number(formatNumberOfAsset(transaction.getTransaction().getNumberOfAsset()))
                        .pricePerOne(String.format("%,.2f", transaction.getPricePerOne()))
                        .price(String.format("%,.2f", transaction.getPrice())).build();
            }
            singleTransactions.add(singleTransaction);
        }
        return singleTransactions;
    }

    private String formatNumberOfAsset(BigDecimal numberOfAsset) {
        if (numberOfAsset.scale() == 1 || numberOfAsset.compareTo(BigDecimal.ZERO) <= 0) {
            return String.format("%,.1f", numberOfAsset);
        }
        return String.format("%,.8f", numberOfAsset);
    }

    private void configureColumns() {
        TableColumn<SingleTransaction, String> realizedGain = (TableColumn<SingleTransaction, String>) transactionsTable.getColumns().get(7);
        TableColumn<SingleTransaction, String> activeGain = (TableColumn<SingleTransaction, String>) transactionsTable.getColumns().get(8);
        TableColumn<SingleTransaction, String> allGain = (TableColumn<SingleTransaction, String>) transactionsTable.getColumns().get(9);
        TableColumn<SingleTransaction, String> percentageGain = (TableColumn<SingleTransaction, String>) transactionsTable.getColumns().get(10);

        realizedGain.setCellFactory(cell -> {
            return new TextFieldTableCell<SingleTransaction, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !item.equals("") && !item.equals("0,00")) {
                        if (item.startsWith("-")) {
                            setStyle("-fx-background-color: salmon;");
                        } else {
                            setStyle("-fx-background-color: lightgreen;");
                        }
                    } else {
                        setStyle("-fx-background-color: default;");
                    }
                }
            };
        });

        activeGain.setCellFactory(cell -> {
            return new TextFieldTableCell<SingleTransaction, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !item.equals("") && !item.equals("0,00")) {
                        if (item.startsWith("-")) {
                            setStyle("-fx-background-color: salmon;");
                        } else {
                            setStyle("-fx-background-color: lightgreen;");
                        }
                    } else {
                        setStyle("-fx-background-color: default;");
                    }
                }
            };
        });

        allGain.setCellFactory(cell -> {
            return new TextFieldTableCell<SingleTransaction, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !item.equals("") && !item.equals("0,00")) {
                        if (item.startsWith("-")) {
                            setStyle("-fx-background-color: salmon;");
                        } else {
                            setStyle("-fx-background-color: lightgreen;");
                        }
                    } else {
                        setStyle("-fx-background-color: default;");
                    }
                }
            };
        });

        percentageGain.setCellFactory(cell -> {
            return new TextFieldTableCell<SingleTransaction, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !item.equals("") && !item.equals("0,00")) {
                        if (item.startsWith("-")) {
                            setStyle("-fx-background-color: salmon;");
                        } else {
                            setStyle("-fx-background-color: lightgreen;");
                        }
                    } else {
                        setStyle("-fx-background-color: default;");
                    }
                }
            };
        });
    }
}
