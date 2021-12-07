package com.example.loginpage;

import com.example.loginpage.models.FreeParking;
import com.example.loginpage.models.FreeParkingUserCarOwners;
import com.example.loginpage.models.RequestUserCarOwners;
import com.example.loginpage.oop.MethodClass;
import com.example.loginpage.oop.RestAPI.OkHttpDelete;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class parkingManageParkingController {
    //OOP
    OkHttpGet okHttpGet = new OkHttpGet();
    OkHttpPut okHttpPut = new OkHttpPut();
    OkHttpDelete okHttpDelete = new OkHttpDelete();
    OkHttpPost okHttpPost = new OkHttpPost();
    MethodClass methodClass = new MethodClass();

    @FXML
    private DatePicker dpApplyFreeParking;
    @FXML
    private Label labelCurrentStatus, labelGlobalvisa;
    @FXML
    private CheckBox checkboxProposeFreeParking, checkboxConfirmUpdateRequest;
    @FXML
    private TableColumn tablecolumnManageDate, tablecolumnManageVisa, tablecolumnRequestId, tablecolumnManageDateA, tablecolumnManageVisaA, tablecolumnRequestIdA, tablecolumnManageDateD, tablecolumnManageVisaD, tablecolumnRequestIdD;
    @FXML
    private TableView tableviewDemand, tableviewDemandA, tableviewDemandD;
    @FXML
    private Label labelVisaSelected, labelDateSelected;
    @FXML
    private RadioButton radioApprove, radioDecline;
    @FXML
    private ImageView imageviewUser, sidemenuApplyParking, sidemenuCredits, sidemenuManageParking, sidemenuParkingDashboard;

    private Stage stage;
    private Scene scene;
    private Parent root;

    String globalVisa;
    Boolean hasCarBoolean;
    List<String> dataArray = new ArrayList<String>();
    List<String> dataArrayA = new ArrayList<String>();
    List<String> dataArrayD = new ArrayList<String>();
    List<String> carUsersArray = new ArrayList<String>();


    @FXML
    protected void initialize() throws IOException {
//        setup(globalVisa, carUsersArray);
    }

    public void setup(String globalvisa, Boolean phasCarBoolean) throws IOException {

        if(phasCarBoolean == true)
        {
            sidemenuApplyParking.setDisable(true);
            labelCurrentStatus.setText("You are a car owner!");
        }
        else
        {
            sidemenuManageParking.setDisable(true);
            labelCurrentStatus.setText("You do not havea car!");
        }

        labelGlobalvisa.setText(globalvisa);
        hasCarBoolean = phasCarBoolean;
        globalVisa = labelGlobalvisa.getText();

        //populating tableview
        ObservableList<RequestUserCarOwners> data = methodClass.getMyParkingDemandsByStatus(globalVisa, "applied");
        ObservableList<RequestUserCarOwners> dataA = methodClass.getMyParkingDemandsByStatus(globalVisa, "approved");
        ObservableList<RequestUserCarOwners> dataD = methodClass.getMyParkingDemandsByStatus(globalVisa, "declined");


        tablecolumnRequestId.setCellValueFactory(new PropertyValueFactory<RequestUserCarOwners,String>("requestId"));
        tablecolumnManageVisa.setCellValueFactory(new PropertyValueFactory<RequestUserCarOwners,String>("visa"));
        tablecolumnManageDate.setCellValueFactory(new PropertyValueFactory<RequestUserCarOwners,String>("date"));

        tablecolumnRequestIdA.setCellValueFactory(new PropertyValueFactory<FreeParkingUserCarOwners,String>("requestId"));
        tablecolumnManageVisaA.setCellValueFactory(new PropertyValueFactory<FreeParkingUserCarOwners,String>("visa"));
        tablecolumnManageDateA.setCellValueFactory(new PropertyValueFactory<FreeParkingUserCarOwners,String>("date"));

        tablecolumnRequestIdD.setCellValueFactory(new PropertyValueFactory<FreeParking,String>("requestId"));
        tablecolumnManageVisaD.setCellValueFactory(new PropertyValueFactory<FreeParking,String>("visa"));
        tablecolumnManageDateD.setCellValueFactory(new PropertyValueFactory<FreeParking,String>("date"));

        tableviewDemand.setItems(data);
        tableviewDemandA.setItems(dataA);
        tableviewDemandD.setItems(dataD);




        dataArray.clear();
        Iterator<RequestUserCarOwners> iterator = data.iterator();
        while (iterator.hasNext()) {
            dataArray.add(iterator.next().getDate());
        }
        dataArrayA.clear();
        Iterator<RequestUserCarOwners> iteratorA = dataA.iterator();
        while (iteratorA.hasNext()) {
            dataArrayA.add(iteratorA.next().getDate());

        }
        dataArrayD.clear();
        Iterator<RequestUserCarOwners> iteratorD = dataD.iterator();
        while (iteratorD.hasNext()) {
            dataArrayD.add(iteratorD.next().getDate());
        }



    }

    Integer requestId;
    public void getSelectedRecord(MouseEvent mouseEvent) throws IOException {
        RequestUserCarOwners requestUserCarOwners = (RequestUserCarOwners) tableviewDemand.getSelectionModel().getSelectedItem();
        labelVisaSelected.setText(requestUserCarOwners.getVisa());
        labelDateSelected.setText(requestUserCarOwners.getDate());
        requestId = Integer.parseInt(requestUserCarOwners.getRequestId());
    }

    Integer deleterequestId;
    public void getSelectedRecordA(MouseEvent mouseEvent) throws IOException {
        RequestUserCarOwners requestUserCarOwners = (RequestUserCarOwners) tableviewDemandA.getSelectionModel().getSelectedItem();
        deleterequestId = Integer.parseInt(requestUserCarOwners.getRequestId());
    }
    public void getSelectedRecordD(MouseEvent mouseEvent) throws IOException {
        RequestUserCarOwners requestUserCarOwners = (RequestUserCarOwners) tableviewDemandD.getSelectionModel().getSelectedItem();
        deleterequestId = Integer.parseInt(requestUserCarOwners.getRequestId());
    }

    public void setLeaveStatus() throws IOException {
        if(checkboxConfirmUpdateRequest.isSelected()) {
            if(radioApprove.isSelected()) {
                if(dataArrayA.contains(labelDateSelected.getText()))
                {
                    methodClass.messageBox("You already have this date booked", "Date already booked!");
                }
                else
                {
                    String url = "http://localhost:8080/cppk/update/" + requestId;
                    String updateRequestJson = "    {\n        \"status\": \"" + "Approved" + "\"\n" +
                            "    }";
                    System.out.println(updateRequestJson);
                    okHttpPut.post(url, updateRequestJson);
                    checkboxConfirmUpdateRequest.setSelected(false);
                    radioDecline.setSelected(false);
                    radioApprove.setSelected(false);
                    labelCurrentStatus.setText("Request approved!");
                    setup(globalVisa, hasCarBoolean);
                }

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
                setup(globalVisa, hasCarBoolean);
            }
            else {
                methodClass.messageBox("Please select a Radio Button (Accept/Decline)", "User Error");
            }

        }

        else {
                 methodClass.messageBox("Please check the Checkbox before Requesting a parking slot", "User error");
             }
    }


    public void DeleteRequest() throws IOException {
        String url = "http://localhost:8080/cppk/deleteParkingRequest/" + deleterequestId;
        okHttpDelete.run(url);
        methodClass.messageBox("Request with ID: " + deleterequestId + " has been deleted", "Request deleted");
        setup(globalVisa, hasCarBoolean);
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
        parkingApplyForParkingController.setup(globalVisa, hasCarBoolean);
        stage.show();
    }

    public void switchToManageParking(MouseEvent e) throws IOException {
        labelCurrentStatus.setText("Already in Manage Parking");
    }

    public void switchToLoginPageLogOut(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        root = loader.load();
        loginPageController loginPageController = loader.getController();

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        loginPageController.setup("");
        stage.show();
    }

    public void switchToLoginPageReturn(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        root = loader.load();
        loginPageController loginPageController = loader.getController();

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        loginPageController.setup(globalVisa);
        stage.show();
    }
}
