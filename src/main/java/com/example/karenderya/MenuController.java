package com.example.karenderya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    // Navigation buttons
    @FXML private Button InventoryBtn;
    @FXML private Button CustomerBtn;

    @FXML
    private void handleInventoryClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inventory.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) InventoryBtn.getScene().getWindow();
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

    // Order components
    @FXML private TableView<OrderItem> orderTable;
    @FXML private TableColumn<OrderItem, String> itemColumn;
    @FXML private TableColumn<OrderItem, Double> priceColumn;
    @FXML private Label totalLabel;
    @FXML private Button removeButton;
    @FXML private Button confirmButton;

    private final ObservableList<OrderItem> orderList = FXCollections.observableArrayList();

    @FXML private FlowPane menuFlow;

    @FXML
    public void initialize() {

        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderTable.setItems(orderList);

        removeButton.setOnAction(e -> handleRemoveOrder());
        confirmButton.setOnAction(e -> handleConfirmOrder());

        loadMenuItems();
    }

    // Add item to temporary order list
    public void addOrder(String item, double price) {
        orderList.add(new OrderItem(item, price));
        updateTotal();
    }

    // Update total price
    private void updateTotal() {
        double total = orderList.stream().mapToDouble(OrderItem::getPrice).sum();
        totalLabel.setText("Total: ₱" + total);
    }

    // Remove selected order
    private void handleRemoveOrder() {
        OrderItem selected = orderTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to remove.");
            return;
        }

        orderList.remove(selected);
        updateTotal();
    }

    // CONFIRM ORDER — SAVE TO SalesData
    private void handleConfirmOrder() {

        if (orderList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Order", "Cannot confirm an empty order.");
            return;
        }

        // Save all items
        for (OrderItem item : orderList) {
            SalesData.addSale(item.getItem(), item.getPrice());
        }

        showAlert(Alert.AlertType.INFORMATION, "Order Saved", "Order added to customer history!");

        orderList.clear();
        updateTotal();
    }

    // ALERT
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // LOAD MENU CARDS
    private void loadMenuItems() {

        addMenuItem("Dinuguan", 50, "/com/example/karenderya/images/dinuguan.jpg");
        addMenuItem("Lumpia", 10, "/com/example/karenderya/images/lumpia.jpg");
        addMenuItem("Fried Chicken", 25, "/com/example/karenderya/images/fried_chicken.jpg");
        addMenuItem("Pancit Bihon", 15, "/com/example/karenderya/images/pancit_bihon.jpg");
        addMenuItem("Coke", 25, "/com/example/karenderya/images/coke.jpg");
        addMenuItem("Sprite", 25, "/com/example/karenderya/images/sprite.jpg");

    }

    // Create a food card
    private void addMenuItem(String name, double price, String imagePath) {

        VBox box = new VBox();
        box.setSpacing(8);
        box.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-padding: 12;" +
                        "-fx-border-color: #d0d0d0;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-alignment: center;"
        );
        box.setPrefSize(150, 210);

        // Load image
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            System.out.println("Image missing: " + imagePath);
            image = new Image("https://via.placeholder.com/120");
        }

        ImageView img = new ImageView(image);
        img.setFitWidth(120);
        img.setFitHeight(100);
        img.setPreserveRatio(true);

        Label lblName = new Label(name);
        lblName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label lblPrice = new Label("₱" + price);
        lblPrice.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

        Button addBtn = new Button("+");
        addBtn.setStyle(
                "-fx-background-color: #4da3ff;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;"
        );
        addBtn.setPrefWidth(60);

        addBtn.setOnAction(e -> addOrder(name, price));

        box.getChildren().addAll(img, lblName, lblPrice, addBtn);

        menuFlow.getChildren().add(box);
    }

    // ORDER ITEM CLASS
    public static class OrderItem {
        private final String item;
        private final Double price;

        public OrderItem(String item, Double price) {
            this.item = item;
            this.price = price;
        }

        public String getItem() { return item; }
        public Double getPrice() { return price; }
    }
}
