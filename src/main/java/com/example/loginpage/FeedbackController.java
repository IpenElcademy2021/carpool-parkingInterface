package com.example.loginpage;

import com.example.loginpage.oop.MethodClass;
import com.example.loginpage.oop.SendEmail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FeedbackController {
    //oop
    MethodClass methodClass = new MethodClass();
    SendEmail sendEmail = new SendEmail();

    @FXML
    private TextArea textareaEnterFeedback;
    @FXML
    private Button buttonCancel;

    public void SendFeedBack() throws IOException {
        sendEmail.sendEmailNotfication("New feedback on iPension Carpool&Parking (Anonymous)", textareaEnterFeedback.getText(), "feedback");
        System.exit(0);
    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

}
