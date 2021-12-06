package com.example.loginpage;

import com.example.loginpage.models.CarpoolManagement;
import com.example.loginpage.oop.CarpoolManagementMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class CarpoolManagementController {

    @FXML
    String globalVisa;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView <CarpoolManagement> tableView_management;

    @FXML
    private TableColumn column_visa;

    @FXML
    private TableColumn column_date;

    @FXML
    private TableColumn column_status;

    @FXML
    private TableColumn column_seat;

    @FXML
    private TableColumn column_comment;

    @FXML
    private TextField textField_visa;
    @FXML
    private TextField textField_date;
    @FXML
    private ComboBox comboBox_status;
    @FXML
    private TextArea textArea_comment;

    String user_request_id;


    CarpoolManagementMethod carpoolManagementMethod = new CarpoolManagementMethod();

    public void setup(String visa) throws IOException {

        globalVisa = visa;

        ObservableList<CarpoolManagement> data = carpoolManagementMethod.getCarpoolRequestByVisa(globalVisa);

        column_visa.setCellValueFactory(new PropertyValueFactory<CarpoolManagement,String>("visa"));
        column_date.setCellValueFactory(new PropertyValueFactory<CarpoolManagement, Date>("date"));
        column_status.setCellValueFactory(new PropertyValueFactory<CarpoolManagement, String>("reservationStatus"));
        column_seat.setCellValueFactory(new PropertyValueFactory<CarpoolManagement, String>("seat"));
        column_comment.setCellValueFactory(new PropertyValueFactory<CarpoolManagement, String>("comment"));

        tableView_management.setItems(data);
    }

    @FXML
    public void initialize() throws IOException {

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

    public void getCellValue(MouseEvent e) throws IOException {

        CarpoolManagement carpoolManagement = tableView_management.getSelectionModel().getSelectedItem();
        textField_visa.setText(carpoolManagement.getVisa());
        textField_date.setText(carpoolManagement.getDate());


    }

    public void cancelBtn (MouseEvent e) throws IOException {
        textField_visa.setText("");
        textField_date.setText("");
        comboBox_status.setAccessibleText("Pending");
        textArea_comment.setText("");

    }

    public void confirmBtn (MouseEvent e) throws IOException {


    }
}
