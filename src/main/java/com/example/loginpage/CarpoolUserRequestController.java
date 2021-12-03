package com.example.loginpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CarpoolUserRequestController {

    @FXML
    String globalVisa;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setup(String visa) throws IOException {

        globalVisa = visa;
    }

    public void switchToPoolingDashboard(MouseEvent e) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("carpoolDashBoard.fxml"));
        root = loader.load();

        CarpoolDashboardController carpoolDashboardController = loader.getController();

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        carpoolDashboardController.setup(globalVisa);
        stage.show();

    }

    public void switchToPoolingPropose(MouseEvent e) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("carpoolPropose.fxml"));
        root = loader.load();
        PoolingProposeController poolingProposeController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        poolingProposeController.setup(globalVisa);
        stage.show();
    }

    public void switchToPoolingUserRequest(MouseEvent e) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("carpoolRequest.fxml"));
        root = loader.load();
        CarpoolUserRequestController carpoolUserRequestController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        carpoolUserRequestController.setup(globalVisa);
        stage.show();

    }

    public void switchToPoolingManage(MouseEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("carpoolManagement.fxml"));
        root = loader.load();
        CarpoolManagementController carpoolManagementController = loader.getController();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        carpoolManagementController.setup(globalVisa);
        stage.show();
    }
}
