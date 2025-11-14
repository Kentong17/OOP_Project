package com.example.karenderya;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField accessCodeField;

    @FXML
    private Button signInButton;

    @FXML
    private void handleSignIn() throws IOException {
        // Load login-view.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
