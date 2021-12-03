package com.example.loginpage;

import com.example.loginpage.models.PoolingPropose;
import com.example.loginpage.models.User;
import com.example.loginpage.models.UserRequest;
import com.example.loginpage.models.UserRequestPoolingProposeUser;
import com.example.loginpage.oop.PoolingMethodClass;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;


public class CarpoolDashboardController {

    @FXML
    private TableView tableView_information;

    @FXML
    private TableColumn column_driver_id,column_region,column_date,column_pickup_point,column_pickup_time,column_departure_time,column_reservation,column_comment;

    PoolingMethodClass poolingMethodClass = new PoolingMethodClass();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    String globalVisa;

    public void setup(String visa) throws IOException {

        globalVisa = visa;


        ObservableList<UserRequestPoolingProposeUser> data = poolingMethodClass.getUserRequestByVisa(visa);
        column_driver_id.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("visa"));
        column_date.setCellValueFactory(new PropertyValueFactory<PoolingPropose, Date>("region"));
        column_region.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("date"));
        column_pickup_point.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("pickUpPoint"));
        column_pickup_time.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("pickUpTime"));
        column_departure_time.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("departureTime"));
        column_reservation.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("reservationStatus"));
        column_comment.setCellValueFactory(new PropertyValueFactory<UserRequestPoolingProposeUser,String>("comment"));

        tableView_information.setItems(data);
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

}
