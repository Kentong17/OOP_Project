package com.example.karenderya;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryController {


    @FXML
    private Button MenuBtn;

    @FXML
    private Button CustomerBtn;


    @FXML
    private void handleMenuClick() throws IOException {
        // Load login-view.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) MenuBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();




    }

    @FXML
    private void handleCustomerClick() throws IOException {
        // Load login-view.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) CustomerBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();



    }
}
