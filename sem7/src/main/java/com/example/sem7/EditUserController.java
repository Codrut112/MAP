package com.example.sem7;

import com.example.sem7.java.domain.Utilizator;
import com.example.sem7.java.service.Service;
import com.example.sem7.java.validators.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUserController {
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private Stage stage;
    private Service service;
    private Utilizator user;

    private UserController parent;

    public void setService(Service service, Stage newStage, Utilizator user, UserController parent) {
        this.service = service;
        this.stage = newStage;
        this.user = user;
        this.parent = parent;
        if (user != null) {
            firstNameText.setText(user.getFirstName());
            lastNameText.setText(user.getLastName());
        }
    }

    public void handleSave() {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        try {
            if (user == null) {
                addUser(firstName, lastName);
            } else {
                updateUser(firstName, lastName);
            }
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        } finally {
            parent.initModel();
            stage.close();
        }

    }

    private void updateUser(String firstName, String lastName) {
        service.updateUtiliator(user.getId(), firstName, lastName);
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Succes", "User Modified");
    }


    private void addUser(String firstName, String lastName) {
        service.addUtilizator(firstName, lastName);
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Succes", "User Saved");
    }

    public void handleCancel() {
        stage.close();
    }


}
