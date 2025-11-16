package com.example.karenderya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryController {

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> colName, colType, colStatus, colDate;
    @FXML private TableColumn<Product, Number> colPrice, colStock;

    @FXML private TextField tfName, tfType, tfPrice, tfStock, tfStatus, tfDate;

    @FXML private Button btnAdd, btnUpdate, btnClear, btnDelete, btnImport;
    @FXML private Button MenuBtn, CustomerBtn;

    // Observable list for table data
    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        // Connect table columns to Product model
        colName.setCellValueFactory(data -> data.getValue().nameProperty());
        colType.setCellValueFactory(data -> data.getValue().typeProperty());
        colPrice.setCellValueFactory(data -> data.getValue().priceProperty());
        colStock.setCellValueFactory(data -> data.getValue().stockProperty());
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());
        colDate.setCellValueFactory(data -> data.getValue().dateProperty());

        productTable.setItems(productList);

        // When selecting a row, show data in textfields
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tfName.setText(newVal.getName());
                tfType.setText(newVal.getType());
                tfPrice.setText(String.valueOf(newVal.getPrice()));
                tfStock.setText(String.valueOf(newVal.getStock()));
                tfStatus.setText(newVal.getStatus());
                tfDate.setText(newVal.getDate());
            }
        });
    }

    // ADD PRODUCT
    @FXML
    private void addProduct() {
        try {
            Product p = new Product(
                    tfName.getText(),
                    tfType.getText(),
                    Double.parseDouble(tfPrice.getText()),
                    Integer.parseInt(tfStock.getText()),
                    tfStatus.getText(),
                    tfDate.getText()
            );

            productList.add(p);
            clearFields();

        } catch (Exception e) {
            showAlert("Invalid Input", "Please enter valid price/stock values.");
        }
    }

    // UPDATE PRODUCT
    @FXML
    private void updateProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Select a product to update.");
            return;
        }

        try {
            selected.setName(tfName.getText());
            selected.setType(tfType.getText());
            selected.setPrice(Double.parseDouble(tfPrice.getText()));
            selected.setStock(Integer.parseInt(tfStock.getText()));
            selected.setStatus(tfStatus.getText());
            selected.setDate(tfDate.getText());

            productTable.refresh();

        } catch (Exception e) {
            showAlert("Invalid Input", "Price and Stock must be numeric.");
        }
    }

    // DELETE PRODUCT
    @FXML
    private void deleteProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Select a product to delete.");
            return;
        }

        productList.remove(selected);
        clearFields();
    }

    // CLEAR FIELDS
    @FXML
    private void clearFields() {
        tfName.clear();
        tfType.clear();
        tfPrice.clear();
        tfStock.clear();
        tfStatus.clear();
        tfDate.clear();
        productTable.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.show();
    }

    // Navigation Buttons
    @FXML
    private void handleMenuClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) MenuBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleCustomerClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) CustomerBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
