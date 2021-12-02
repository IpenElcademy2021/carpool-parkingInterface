package com.example.loginpage;

import com.example.loginpage.models.FreeParking;
import com.example.loginpage.models.FreeParkingUserCarOwners;
import com.example.loginpage.models.RequestUserCarOwners;
import com.example.loginpage.oop.MethodClass;
import com.example.loginpage.oop.RestAPI.OkHttpGet;
import com.example.loginpage.oop.RestAPI.OkHttpPost;
import com.example.loginpage.oop.RestAPI.OkHttpPut;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class parkingManageParkingController {
    //OOP
    OkHttpGet okHttpGet = new OkHttpGet();
    OkHttpPut okHttpPut = new OkHttpPut();
    OkHttpPost okHttpPost = new OkHttpPost();
    MethodClass methodClass = new MethodClass();

    @FXML
    private DatePicker dpApplyFreeParking;
    @FXML
    private Label labelCurrentStatus, labelGlobalvisa;
    @FXML
    private CheckBox checkboxProposeFreeParking, checkboxConfirmUpdateRequest;
    @FXML
    private TableColumn tablecolumnManageDate, tablecolumnManageVisa, tablecolumnRequestId;
    @FXML
    private TableView tableviewDemand;
    @FXML
    private Label labelVisaSelected, labelDateSelected;
    @FXML
    private RadioButton radioApprove, radioDecline;

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
        ObservableList<RequestUserCarOwners> data = methodClass.getMyParkingDemands(globalVisa);
        tablecolumnRequestId.setCellValueFactory(new PropertyValueFactory<FreeParking,String>("requestId"));
        tablecolumnManageVisa.setCellValueFactory(new PropertyValueFactory<FreeParking,String>("visa"));
        tablecolumnManageDate.setCellValueFactory(new PropertyValueFactory<FreeParking,String>("date"));

        tableviewDemand.setItems(data);
    }

    Integer requestId;
    public void getSelectedRecord(MouseEvent mouseEvent) throws IOException {
        RequestUserCarOwners requestUserCarOwners = (RequestUserCarOwners) tableviewDemand.getSelectionModel().getSelectedItem();
        labelVisaSelected.setText(requestUserCarOwners.getVisa());
        labelDateSelected.setText(requestUserCarOwners.getDate());
        requestId = Integer.parseInt(requestUserCarOwners.getRequestId());
    }

    public void setLeaveStatus() throws IOException {
        if(checkboxConfirmUpdateRequest.isSelected()) {
            if(radioApprove.isSelected()) {
                String url = "http://localhost:8080/cppk/update/" + requestId;
                String updateRequestJson = "    {\n        \"status\": \"" + "Approved" + "\"\n" +
                        "    }";
                System.out.println(updateRequestJson);
                okHttpPut.post(url, updateRequestJson);
                checkboxConfirmUpdateRequest.setSelected(false);
                radioDecline.setSelected(false);
                radioApprove.setSelected(false);
                labelCurrentStatus.setText("Request approved!");

            }
            else if (radioDecline.isSelected()) {
                String url = "http://localhost:8080/cppk/update/" + requestId;
                String updateRequestJson = "    {\n        \"status\": \"" + "Declined" + "\"\n" +
                        "    }";
                checkboxConfirmUpdateRequest.setSelected(false);
                radioDecline.setSelected(false);
                radioApprove.setSelected(false);
                okHttpPut.post(url, updateRequestJson);
                labelCurrentStatus.setText("Request delined!");
            }
            else {
                methodClass.messageBox("Please select a Radio Button (Accept/Decline)", "User Error");
            }
        }

        else {
                 methodClass.messageBox("Please check the Checkbox before Requesting a parking slot", "User error");
             }


    }

    public void ApplyFreeParking() throws IOException {
        if(checkboxProposeFreeParking.isSelected())
        {
            String newFreeParkingjson = "    {\n        \"date\": \"" + dpApplyFreeParking.getValue() + "\",\n" +
                    "        \"user\": {\"visa\":\"" + globalVisa + "\"}\n" +
                    "    }";

            String response = okHttpPost.post("http://localhost:8080/cppk/addFreeParking/", newFreeParkingjson);
            labelCurrentStatus.setText(response);
        }
        else
        {
         methodClass.messageBox("Please check the Checkbox before proposing a free parking slot", "User error");
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
