package com.example.loginpage.oop;


import com.example.loginpage.models.FreeParking;
import com.example.loginpage.models.FreeParkingUserCarOwners;
import com.example.loginpage.oop.RestAPI.OkHttpGet;
import com.example.loginpage.oop.RestAPI.OkHttpPost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MethodClass {
    //OOP
    OkHttpGet okHttpGet = new OkHttpGet();
    OkHttpPost okHttpPost = new OkHttpPost();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");

    public void Exit() {
        messageBox("Goodbye", "Exit");
        System.exit(0);
    }

    public static void messageBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public void PostNewRequest() {


    }



    public ObservableList<FreeParkingUserCarOwners> getAllFreeParking() throws IOException {

        String url = "http://localhost:8080/cppk/getAllFreeParking/";
        System.out.println(okHttpGet.run(url));
        String response = okHttpGet.run(url);
        ObservableList<FreeParkingUserCarOwners> datareturn = FXCollections.observableArrayList();

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONArray jsonArray = (JSONArray) obj;

            ObservableList<FreeParkingUserCarOwners> data = FXCollections.observableArrayList();

            String freeParkingID = "", date = "", visa = "", password = "", name = "", address = "", phoneNumber = "", carPlate = "", parkingSlot = "";
            for (var i = 0; i < jsonArray.toArray().length; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                JSONObject jsonUsersObject = (JSONObject)jsonObject.get("user");
                JSONObject jsonCarownersObject = (JSONObject)jsonUsersObject.get("carOwners");

                 freeParkingID = jsonObject.get("freeParkingID").toString();
                 date = jsonObject.get("date").toString().substring(0,10);
                 visa = jsonUsersObject.get("visa").toString();
                 password = jsonUsersObject.get("password").toString();
                 name = jsonUsersObject.get("name").toString();
                 address = jsonUsersObject.get("address").toString();
                 phoneNumber = jsonUsersObject.get("phoneNumber").toString();
                 carPlate = jsonCarownersObject.get("carPlate").toString();
                 parkingSlot = jsonCarownersObject.get("parkingSlot").toString();

                 data.add(new FreeParkingUserCarOwners(freeParkingID, date, visa, password,name, address, phoneNumber, carPlate, parkingSlot));

                datareturn = data;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datareturn;
    }
}
