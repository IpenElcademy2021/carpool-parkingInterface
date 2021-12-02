package com.example.loginpage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;




public class CarpoolDashboardController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToCarpoolManagement(MouseEvent e) throws IOException {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("carpoolManagement.fxml"));
            root = loader.load();
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }
}
