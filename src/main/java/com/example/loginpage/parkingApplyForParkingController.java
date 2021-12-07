package com.example.loginpage;

import com.example.loginpage.models.FreeParking;
import com.example.loginpage.models.FreeParkingUserCarOwners;
import com.example.loginpage.oop.Login;
import com.example.loginpage.oop.MethodClass;
import com.example.loginpage.oop.RestAPI.OkHttpGet;
import com.example.loginpage.oop.RestAPI.OkHttpPost;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class parkingApplyForParkingController {
    //OOP
    OkHttpGet okHttpGet = new OkHttpGet();
    MethodClass methodClass = new MethodClass();
    OkHttpPost okHttpPost = new OkHttpPost();
    @FXML
    private TableColumn tablecolumnDateFree, tablecolumnDriverVisa, tablecolumnParkingSlot;
    @FXML
    private TableView tableviewFreeSlots;
    @FXML
    private Label labelSelectedDay, labelSelectedParkingslot, labelCurrentStatus, labelGlobalvisa;
    @FXML
    private CheckBox checkboxConfirmApplyParking;

    private Stage stage;
    private Scene scene;
    private Parent root;

    String globalVisa;

    public void setup(String globalvisa) throws IOException {
        labelGlobalvisa.setText(globalvisa);
        globalVisa = labelGlobalvisa.getText();
    }

    @FXML
    protected void initialize() throws IOException {
        //populating tableview
        ObservableList<FreeParkingUserCarOwners> data = methodClass.getAllFreeParking();

        tablecolumnDateFree.setCellValueFactory(new PropertyValueFactory<FreeParking,String>("date"));
        tablecolumnDriverVisa.setCellValueFactory(new PropertyValueFactory<FreeParking,String>("visa"));
        tablecolumnParkingSlot.setCellValueFactory(new PropertyValueFactory<FreeParking,String>("parkingSlot"));

        tableviewFreeSlots.setItems(data);

    }

    String drivervisa = "";
    public void getSelectedRecord(MouseEvent mouseEvent) throws IOException {
        FreeParkingUserCarOwners freeParkingUserCarOwners = (FreeParkingUserCarOwners) tableviewFreeSlots.getSelectionModel().getSelectedItem();
        labelSelectedDay.setText(freeParkingUserCarOwners.getDate());
        labelSelectedParkingslot.setText(freeParkingUserCarOwners.getParkingSlot());
        drivervisa = freeParkingUserCarOwners.getVisa();
    }

    public void RequestFreeParking() throws IOException {
        if(checkboxConfirmApplyParking.isSelected())
        {
            String newFreeParkingjson = "    {\n        \"date\": \"" + labelSelectedDay.getText() + "\",\n" +
                    "        \"status\": \"" + "Applied" + "\",\n" +
                    "        \"driverVisa\": \"" + drivervisa + "\",\n" +
                    "        \"user\": {\"visa\":\"" + globalVisa + "\"}\n" +
                    "    }";

            System.out.println(newFreeParkingjson);
            String response = okHttpPost.post("http://localhost:8080/cppk/addARequest/", newFreeParkingjson);
            labelCurrentStatus.setText(response);
        }
        else
        {
            methodClass.messageBox("Please check the Checkbox before Requesting a parking slot", "User error");
        }
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
