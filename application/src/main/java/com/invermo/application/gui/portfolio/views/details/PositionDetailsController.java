package com.invermo.application.gui.portfolio.views.details;

import com.invermo.application.gui.components.views.charts.line.InvermoBarChart;
import com.invermo.application.gui.components.views.charts.model.ChartPoint;
import com.invermo.application.gui.portfolio.dto.SingleTransaction;
import com.invermo.application.gui.portfolio.service.PositionDetailsService;
import com.invermo.application.service.ServiceManager;
import com.invermo.business.domain.SinglePortfolioAsset;
import com.invermo.business.domain.SingleTransactionRecord;
import com.invermo.business.enumeration.TransactionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PositionDetailsController implements Initializable {

    @FXML
    private TableView<SingleTransaction> transactionsTable;
    @FXML
    private InvermoBarChart gainChart;
    @FXML
    private InvermoBarChart cumulativeGainChart;
    @FXML
    private InvermoBarChart percentageGainChart;
    private SinglePortfolioAsset singlePortfolioAsset;
    private PositionDetailsService positionDetailsService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void customInitialize() {
        this.positionDetailsService = ServiceManager.getPositionDetailsService();
        configureColumns();
        final List<SingleTransactionRecord> transactions = this.positionDetailsService.getSingleTransactionsForPosition(singlePortfolioAsset.getPositionId());

        final List<SingleTransaction> singleTransactions = mapToSingleTransactions(transactions);
        final ObservableList<SingleTransaction> singleTransactionObservableList = FXCollections.observableArrayList();
        singleTransactionObservableList.addAll(singleTransactions);
        transactionsTable.setItems(singleTransactionObservableList);
        final Map<String, List<ChartPoint>> gains = positionDetailsService.getPositionGainByMonths(singlePortfolioAsset.getPositionId());
        List<ChartPoint> chartPoints = gains.get(PositionDetailsService.GAIN);
        List<ChartPoint> cumulativeGainPoints = gains.get(PositionDetailsService.CUMULATIVE_GAIN);
        List<ChartPoint> percentageGainByMonth = gains.get(PositionDetailsService.PERCENTAGE_GAIN);

        gainChart.setChartData(chartPoints);
        gainChart.setTitleSide(Side.TOP);
        gainChart.setTitle("Gain");
        gainChart.getXAxis().setLabel("Date");

        cumulativeGainChart.setChartData(cumulativeGainPoints);
        cumulativeGainChart.setTitleSide(Side.TOP);
        cumulativeGainChart.setTitle("Cumulative Gain");
        cumulativeGainChart.getXAxis().setLabel("Date");

        percentageGainChart.setPercentageType("PERCENTAGE_TYPE");
        percentageGainChart.setChartData(percentageGainByMonth);
        percentageGainChart.setTitleSide(Side.TOP);
        percentageGainChart.setTitle("Percentage Gain");
        percentageGainChart.getXAxis().setLabel("Date");


//        mainPane.getChildren().add(new CustomLineChart());
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
