package com.example.loginpage;

import com.example.loginpage.models.PoolingPropose;
import com.example.loginpage.oop.RestAPI.OkHttpGet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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

    OkHttpClient okHttpClient = new OkHttpClient();
    //OkHttpGet okHttpGet = new OkHttpGet();

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

        try (Response response = okHttpClient.newCall(request).execute()){
            System.out.println(response.body().string());
        }

        MessageBox("New Pooling added","Propose Pooling");

    }

    private void MessageBox(String message, String title) {
        JOptionPane.showMessageDialog(null,message,"" +title,JOptionPane.INFORMATION_MESSAGE);
    }

    /*public ObservableList<PoolingPropose> getAllProposePooling() throws IOException{

        String url = "http://localhost:8080/cppk/getAllProposePooling";
        System.out.println(okHttpGet.run(url);
        String response = okHttpGet.run(url);
        ObservableList<PoolingPropose> poolingReturn = FXCollections.observableArrayList();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONArray jsonArray = (JSONArray) obj;
            ObservableList<PoolingPropose> poolingData = FXCollections.observableArrayList();
            String visa = "", region = "", pickUpPoint = "", pickUpTime = "", departureTime = "";
            Date date;
            int seat = 0;

            for (var i = 0; i< jsonArray.toArray().length; i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                JSONObject jsonUserObject = (JSONObject) jsonObject.get("User");
                JSONObject jsonPoolingObject  =(JSONObject) jsonUserObject.get("PoolingPropose");

                visa = jsonObject.get("visa").toString();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return poolingReturn;
    }*/


}
