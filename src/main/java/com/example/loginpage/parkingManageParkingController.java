package com.example.loginpage;

import com.example.loginpage.models.FreeParking;
import com.example.loginpage.oop.MethodClass;
import com.example.loginpage.oop.RestAPI.OkHttpGet;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class parkingManageParkingController {
    //OOP
    OkHttpGet okHttpGet = new OkHttpGet();
    MethodClass methodClass = new MethodClass();

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String globalVisa;


    public void setup(String globalvisa) throws IOException {
        globalVisa = globalvisa;
    }





}
