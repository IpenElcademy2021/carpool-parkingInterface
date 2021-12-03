package com.example.loginpage;

import com.example.loginpage.models.FreeParking;
import com.example.loginpage.models.RequestUserCarOwners;
import com.example.loginpage.oop.MethodClass;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class parkingDashboardController {
    MethodClass methodClass = new MethodClass();
    @FXML
    private TableColumn tablecolumnDate, tablecolumnParkingSlot, tablecolumnStatus, tablecolumnCourtesyof;
    @FXML
    private TableView tableviewDashboard;
    @FXML
    private Label labelGlobalvisa;

    private Stage stage;
    private Scene scene;
    private Parent root;

    String globalVisa;

    @FXML
    protected void initialize() throws IOException {

    }

    public void setup(String globalvisa) throws IOException {
        labelGlobalvisa.setText(globalvisa);
        globalVisa = labelGlobalvisa.getText();

        //populating tableview
        ObservableList<RequestUserCarOwners> data = methodClass.getMyRequestByUser(labelGlobalvisa.getText());
        tablecolumnDate.setCellValueFactory(new PropertyValueFactory<RequestUserCarOwners,String>("date"));
//        tablecolumnParkingSlot.setCellValueFactory(new PropertyValueFactory<RequestUserCarOwners,String>("parkingSlot"));
        tablecolumnStatus.setCellValueFactory(new PropertyValueFactory<RequestUserCarOwners,String>("status"));
        tablecolumnCourtesyof.setCellValueFactory(new PropertyValueFactory<RequestUserCarOwners,String>("driverVisa"));

        tableviewDashboard.setItems(data);
    }


    public void switchToParkingDashboard(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("parkingDashboard.fxml"));
        root = loader.load();
        parkingDashboardController parkingDashboardController = loader.getController();

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        parkingDashboardController.setup(globalVisa);
        stage.show();
    }

    public void switchToParkingApply(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("parkingApplyForParking.fxml"));
        root = loader.load();
        parkingApplyForParkingController parkingApplyForParkingController = loader.getController();

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        parkingApplyForParkingController.setup(globalVisa);
        stage.show();
    }

    public void switchToManageParking(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("parkingManageParking.fxml"));
        root = loader.load();
        parkingManageParkingController parkingManageParkingController = loader.getController();

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        parkingManageParkingController.setup(globalVisa);
        stage.show();
    }

}
