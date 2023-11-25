package com.example.sem7;

import com.example.sem7.java.service.Service;
import com.example.sem7.java.domain.Utilizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class UserController {


    Service service;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();


    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator, Long> tableColumnUtilizatorId;
    @FXML
    TableColumn<Utilizator, String> tableColumnFirstname;
    @FXML
    TableColumn<Utilizator, String> tableColumnLastName;

    public void setService(Service service) {
        tableColumnUtilizatorId.setVisible(false);
        this.service = service;
        initModel();
    }

    @FXML
    private void initialize() {
        tableColumnUtilizatorId.setCellValueFactory(new PropertyValueFactory<Utilizator, Long>("id"));
        tableColumnFirstname.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableView.setItems(model);
    }

    protected void initModel() {
        Iterable<Utilizator> users = service.getAllUtilizatori();
        List<Utilizator> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(usersList);
    }


    public void handleAddMessage(ActionEvent actionEvent) {
        showWindowUser(null);
    }

    private void showWindowUser(Utilizator user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditUser.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Edit User");
            newStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            EditUserController editUserController = loader.getController();
            editUserController.setService(service, newStage, user, this);
            newStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleUpdate(ActionEvent actionEvent) {

        Utilizator user = tableView.getSelectionModel().getSelectedItem();
        if (user != null) {
            showWindowUser(user);
        } else MessageAlert.showErrorMessage(null, "You must select an user for update");
    }

    public void handleDelete(ActionEvent actionEvent) {
        Utilizator user = tableView.getSelectionModel().getSelectedItem();
        if (user != null) {
            service.deleteUtilizator(user.getId());
            initModel();
        } else MessageAlert.showErrorMessage(null, "You did not select any user.");
    }
}