package com.example.karenderya;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerController {

    @FXML private Button MenuBtn;
    @FXML private Button InventoryBtn;
    @FXML private Button clearAllBtn;

    @FXML private TableView<SalesData.SaleRecord> customerTable;
    @FXML private TableColumn<SalesData.SaleRecord, String> itemColumn;
    @FXML private TableColumn<SalesData.SaleRecord, Double> priceColumn;

    @FXML private Label totalIncomeLabel;

    @FXML
    public void initialize() {

        // Setup table columns
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Load sales data
        customerTable.setItems(SalesData.salesList);

        // Update total income
        updateIncome();
    }

    // Update total income label
    private void updateIncome() {
        totalIncomeLabel.setText("Total Income: ₱" + SalesData.totalIncome);
    }

    // NAVIGATION
    @FXML
    private void handleMenuClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) MenuBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleInventoryClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inventory.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) InventoryBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // CLEAR ALL BUTTON
    @FXML
    private void handleClearAll() {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Clear");
        confirm.setHeaderText("Clear All Sales Records");
        confirm.setContentText("Are you sure you want to delete ALL records?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            // Clear all sales
            SalesData.salesList.clear();
            SalesData.totalIncome = 0;

            // Refresh UI
            customerTable.refresh();
            updateIncome();
        }
    }
}
