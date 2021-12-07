package com.example.loginpage.oop;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendEmail {
    MethodClass methodClass = new MethodClass();

    public void sendEmailNotfication(String visa, String drivervisa) throws IOException {

        JSONObject jsonUserObject = methodClass.findUserbyVisa(drivervisa);
        String address = jsonUserObject.get("address").toString();


        // Recipient's email ID needs to be mentioned.
        String to = address;

        // Sender's email ID needs to be mentioned
        String from = "postpilot130@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("postpilot130@gmail.com", "rauelcadb");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Hello " + drivervisa + ", You got a new request from " + visa);

            // Now set the actual message
            message.setText("You have a new parking request from " + visa + ". Please check your iPension Carpool&Parking Application. - Elcademy");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }


    }
}
