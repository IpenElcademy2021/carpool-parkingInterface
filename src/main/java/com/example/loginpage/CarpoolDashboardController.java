package com.example.loginpage;

import com.example.loginpage.models.PoolingPropose;
import com.example.loginpage.models.User;
import com.example.loginpage.models.UserRequest;
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
    private TableColumn column_driver_id,column_region,column_date,column_pickup_point,column_pickup_time,column_departure_time,column_comment;

    PoolingMethodClass poolingMethodClass = new PoolingMethodClass();

    @FXML
    public void initialize() throws IOException {

        ObservableList<UserRequest> data = poolingMethodClass.getUserRequestByVisa();
        column_driver_id.setCellValueFactory(new PropertyValueFactory<User,String>("visa"));
        column_date.setCellValueFactory(new PropertyValueFactory<PoolingPropose, Date>("date"));
        column_region.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("region"));
        column_pickup_point.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("pickUpPoint"));
        column_pickup_time.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("pickUpTime"));
        column_departure_time.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("departureTime"));
        column_comment.setCellValueFactory(new PropertyValueFactory<PoolingPropose,String>("comment"));

        tableView_information.setItems(data);

        //labelCurrentStatus.setText("Add required Information");
    }
}
