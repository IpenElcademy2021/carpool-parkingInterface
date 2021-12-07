package com.example.loginpage;


import com.example.loginpage.models.PoolingPropose;
import com.example.loginpage.models.User;
import com.example.loginpage.oop.PoolingMethodClass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import okhttp3.*;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;


public class PoolingProposeController {

    @FXML
    private DatePicker datePicker_date;

    @FXML
    private TextField textField_region, textField_pickup_point, textField_pickup_time, textField_departure_time;

    @FXML
    private ComboBox comboxBox_seat;

    @FXML
    private TableView tableView_propose;

    @FXML
    private TableColumn column_visa, column_date, column_region, column_pickup_point, column_pickup_time, column_departure_time, column_seat;

    @FXML
    private ImageView imageDashboard,imagePropose,imageRequest,imageManage;

    @FXML
    String globalVisa;

    private Stage stage;
    private Scene scene;
    private Parent root;

    Boolean hasCarBoolean;


    PoolingMethodClass poolingMethodClass = new PoolingMethodClass();

    @FXML
    public void setup(String visa, Boolean hasCar) throws IOException {
        ObservableList<Integer> seats = FXCollections.observableArrayList(1, 2, 3);
        comboxBox_seat.setItems(seats);
        comboxBox_seat.getSelectionModel().selectFirst();

        datePicker_date.setValue(LocalDate.now());

        hasCarBoolean = hasCar;
        if(hasCar == true)
        {
            imageRequest.setDisable(true);
            System.out.println("You are a driver");
        }
        else
        {
            imagePropose.setDisable(true);
            imageManage.setDisable(true);
            System.out.println("You are not a driver");;
        }


        globalVisa = visa;

        ObservableList<PoolingPropose> data = poolingMethodClass.getAllProposePooling();
        column_visa.setCellValueFactory(new PropertyValueFactory<User, String>("visa"));
        column_date.setCellValueFactory(new PropertyValueFactory<PoolingPropose, Date>("date"));
        column_region.setCellValueFactory(new PropertyValueFactory<PoolingPropose, String>("region"));
        column_pickup_point.setCellValueFactory(new PropertyValueFactory<PoolingPropose, String>("pickUpPoint"));
        column_pickup_time.setCellValueFactory(new PropertyValueFactory<PoolingPropose, String>("pickUpTime"));
        column_departure_time.setCellValueFactory(new PropertyValueFactory<PoolingPropose, String>("departureTime"));
        column_seat.setCellValueFactory(new PropertyValueFactory<PoolingPropose, String>("seat"));

        tableView_propose.setItems(data);


        //labelCurrentStatus.setText("Add required Information");
    }

    OkHttpClient okHttpClient = new OkHttpClient();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void CreatePooling(ActionEvent e) throws IOException {

        if (!textField_region.getText().isEmpty()) {
            if (!textField_pickup_point.getText().isEmpty() && !textField_pickup_time.getText().isEmpty()) {
                addPooling();
            } else if (!textField_departure_time.getText().isEmpty()){
                addPooling();
            }else {
                MessageBox("Field pick up point and field pick up time are empty or enter departure time only", "Error");
            }
        }else {
            MessageBox("Field region is empty", "Error");
        }

    }

    public void addPooling() throws IOException {
        String json = "    {\n        \"date\": \"" + datePicker_date.getValue() + "\",\n" +
                "        \"region\": \"" + textField_region.getText() + "\",\n" +
                "        \"pickUpPoint\": \"" + textField_pickup_point.getText() + "\",\n" +
                "        \"pickUpTime\": \"" + textField_pickup_time.getText() + "\",\n" +
                "        \"departureTime\": \"" + textField_departure_time.getText() + "\",\n" +
                "        \"seat\": \"" + comboxBox_seat.getSelectionModel().getSelectedItem() + "\",\n" +
                "        \"user\": {\"visa\":\"" + globalVisa + "\"}\n" +
                "    }";

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url("http://localhost:8080/cppk/createPooling").post(body).build();

        System.out.println(json);

        try (Response response = okHttpClient.newCall(request).execute()) {
            System.out.println(response.body().string());
        }

        setup(globalVisa, hasCarBoolean);

        MessageBox("New Pooling added", "Propose Pooling");
    }



    private void MessageBox(String message, String title) {
        JOptionPane.showMessageDialog(null,message,"" +title,JOptionPane.INFORMATION_MESSAGE);
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
        poolingProposeController.setup(globalVisa, hasCarBoolean);
        stage.show();
    }

    public void switchToPoolingUserRequest(MouseEvent e) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("carpoolRequest.fxml"));
        root = loader.load();
        CarpoolUserRequestController carpoolUserRequestController = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        carpoolUserRequestController.setup(globalVisa, hasCarBoolean);
        stage.show();

    }

    public void switchToPoolingManage(MouseEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("carpoolManagement.fxml"));
        root = loader.load();
        CarpoolManagementController carpoolManagementController = loader.getController();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        carpoolManagementController.setup(globalVisa, hasCarBoolean);
        stage.show();
    }

}
