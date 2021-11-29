package com.example.loginpage.oop;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;

public class MethodClass {
    //OOP


    public void Exit() {
        messageBox("Goodbye", "Exit");
        System.exit(0);
    }

    public static void messageBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

}
