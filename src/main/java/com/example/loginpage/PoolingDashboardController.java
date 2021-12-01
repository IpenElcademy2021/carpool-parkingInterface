package com.example.loginpage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import okhttp3.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PoolingDashboardController implements Initializable {

    @FXML
    private DatePicker datePicker_date;

    @FXML
    private TextField textField_region, textField_pickup_point, textField_pickup_time, textField_departure_time;

    @FXML
    private ComboBox comboxBox_seat;

    @FXML
    private TableView tableView_propose;

    @FXML
    private TableColumn column_visa,column_name,column_date,column_region,column_pickup_point,column_pickup_time,column_departure_time,column_seat;

    @FXML
    String globalVisa;
    public void GetGlobalVisa(String visa){
        globalVisa = visa;
        System.out.println(globalVisa);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> seats = FXCollections.observableArrayList(1,2,3);
        comboxBox_seat.setItems(seats);
    }

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void CreatePooling(ActionEvent e) throws IOException {

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

        try (Response response = client.newCall(request).execute()){
            System.out.println(response.body().string());
        }

        MessageBox("New Pooling added","Propose Pooling");

    }

    private void MessageBox(String message, String title) {
        JOptionPane.showMessageDialog(null,message,"" +title,JOptionPane.INFORMATION_MESSAGE);
    }


}
