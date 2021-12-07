package com.example.loginpage;

import com.example.loginpage.models.PoolingPropose;
import com.example.loginpage.models.User;
import com.example.loginpage.oop.PoolingMethodClass;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import okhttp3.*;

import javax.swing.*;
import java.io.IOException;
import java.util.Date;

public class CarpoolUserRequestController {

    @FXML
    private TableView tableView_request;

    @FXML
    private TableColumn column_visa,column_date,column_region,column_pickup_point,column_pickup_time,column_departure_time,column_seat,column_poolId;

    @FXML
    private Label label_visa,label_date,label_region,label_pickup_point,label_pickup_time,label_departure_time;

    @FXML
    private ImageView imageDashboard,imagePropose,imageRequest,imageManage;



    @FXML
    String globalVisa;


    @FXML
    int poolingID =0;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    String reservationStatus = "Pending";

    String comment = "No comment";

    Boolean hasCarBoolean;


    PoolingMethodClass poolingMethodClass = new PoolingMethodClass();



    OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void setup(String visa, Boolean hasCar) throws IOException {
        hasCarBoolean = hasCar;
        if(hasCar == true)
        {
            //imageRequest.setDisable(true);
            System.out.println("You are a driver");
        }
        else
        {
            //imagePropose.setDisable(true);
            //imageManage.setDisable(true);
            System.out.println("You are not a driver");;
        }

        globalVisa = visa;

        ObservableList<PoolingPropose> data = poolingMethodClass.getAllProposePooling();
        column_visa.setCellValueFactory(new PropertyValueFactory<User,String>("visa"));
        column_date.setCellValueFactory(new PropertyValueFactory<PoolingPropose, Date>("date"));
        column_region.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("region"));
        column_pickup_point.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("pickUpPoint"));
        column_pickup_time.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("pickUpTime"));
        column_departure_time.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("departureTime"));
        column_seat.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("seat"));
        column_poolId.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("poolId"));


        tableView_request.setItems(data);

    }


    public void getSelectedRecord(MouseEvent e) throws IOException{
        PoolingPropose poolingPropose = (PoolingPropose) tableView_request.getSelectionModel().getSelectedItem();
        label_visa.setText(poolingPropose.getVisa());
        label_date.setText(poolingPropose.getDate());
        label_region.setText(poolingPropose.getRegion());
        label_pickup_point.setText(poolingPropose.getPickUpPoint());
        label_pickup_time.setText(poolingPropose.getPickUpTime());
        label_departure_time.setText(poolingPropose.getDepartureTime());
        poolingID = Integer.parseInt(poolingPropose.getPoolId());
    }

    public void createUserRequest(ActionEvent actionEvent) throws IOException{

        if (poolingID >0){

        String json = "    {\n        \"reservationStatus\": \"" + reservationStatus + "\",\n" +
                "        \"pooling\": {\"poolId\": " + poolingID + "},\n" +
                "        \"user\": {\"visa\":\"" + globalVisa + "\"},\n" +
                "        \"comment\": \"" + comment + "\"\n" +
                "    }";

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url("http://localhost:8080/cppk/createUserRequest").post(body).build();

        System.out.println(json);

        try (Response response = okHttpClient.newCall(request).execute()){
            System.out.println(response.body().string());
        }

        MessageBox("New user request added","User Request ");

        }else {
            MessageBox("Please select a proposal","No User Request ");
        }

        setup(globalVisa, hasCarBoolean);
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
