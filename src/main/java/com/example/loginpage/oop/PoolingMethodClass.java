package com.example.loginpage.oop;

import com.example.loginpage.loginPageController;
import com.example.loginpage.models.PoolingPropose;
import com.example.loginpage.models.UserRequest;
import com.example.loginpage.oop.RestAPI.OkHttpGet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class PoolingMethodClass {

    String globalVariable;

    OkHttpGet okHttpGet = new OkHttpGet();

    public ObservableList<PoolingPropose> getAllProposePooling() throws IOException {

        String url = "http://localhost:8080/cppk/getAllProposePooling";
        System.out.println(okHttpGet.run(url));
        String response = okHttpGet.run(url);
        ObservableList<PoolingPropose> poolingReturn = FXCollections.observableArrayList();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONArray jsonArray = (JSONArray) obj;
            ObservableList<PoolingPropose> poolingData = FXCollections.observableArrayList();
            String region = "", pickUpPoint = "", pickUpTime = "", departureTime = "";
            String date ="";
            String seat = "";
            String visa ="";

            for (var i = 0; i< jsonArray.toArray().length; i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                JSONObject jsonUserObject = (JSONObject) jsonObject.get("user");

                visa = jsonUserObject.get("visa").toString();
                date = jsonObject.get("date").toString().substring(0,10);
                region = jsonObject.get("region").toString();
                pickUpPoint = jsonObject.get("pickUpPoint").toString();
                pickUpTime = jsonObject.get("pickUpTime").toString();
                departureTime = jsonObject.get("departureTime").toString();
                seat = jsonObject.get("seat").toString();

                poolingData.add(new PoolingPropose(date,region,pickUpPoint,pickUpTime,departureTime,seat,visa));

                poolingReturn = poolingData;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return poolingReturn;
    }

    public ObservableList<UserRequest> getUserRequestByVisa() throws IOException {

        globalVariable = loginPageController.GlobalVariable.globalVariable;

        String url = "http://localhost:8080/cppk/getUserRequestByVisa/AAA" +globalVariable;
        String response = okHttpGet.run(url);
        ObservableList<UserRequest> userRequestReturn = FXCollections.observableArrayList();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONArray jsonArray = (JSONArray) obj;
            ObservableList<UserRequest> userRequestData = FXCollections.observableArrayList();
            String visa ="",date ="",region = "", pickUpPoint = "", pickUpTime = "", departureTime = "",comment = "";

            for (var i = 0; i< jsonArray.toArray().length; i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                JSONObject jsonUserObject = (JSONObject) jsonObject.get("user");
                JSONObject jsonPoolingProposeObject = (JSONObject) jsonObject.get("pooling");

                visa = jsonUserObject.get("userVisa").toString();
                date = jsonPoolingProposeObject.get("date").toString().substring(0,10);
                region = jsonPoolingProposeObject.get("region").toString();
                pickUpPoint = jsonPoolingProposeObject.get("pickUpPoint").toString();
                pickUpTime = jsonPoolingProposeObject.get("pickUpTime").toString();
                departureTime = jsonPoolingProposeObject.get("departureTime").toString();
                comment = jsonObject.get("comment").toString();

                userRequestData.add(new UserRequest(comment,visa,date,region,pickUpPoint,pickUpTime,departureTime));


                userRequestReturn = userRequestData;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userRequestReturn;
    }


}