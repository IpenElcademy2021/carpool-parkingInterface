package com.example.loginpage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class loginPageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}