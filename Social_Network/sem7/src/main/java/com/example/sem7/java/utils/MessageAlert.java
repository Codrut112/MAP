package com.example.sem7.java.utils;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    public static void showMessage(Stage owner, Alert.AlertType type,String header,String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }
    public static void showErrorMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.initOwner(owner);
        message.setTitle("Eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}
