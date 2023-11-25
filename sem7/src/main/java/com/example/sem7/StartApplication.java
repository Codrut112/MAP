package com.example.sem7;

import com.example.sem7.java.repository.DbFriendshipRepository;
import com.example.sem7.java.repository.DbRepository;
import com.example.sem7.java.repository.FriendshipsRepository;
import com.example.sem7.java.service.Service;
import com.example.sem7.java.validators.UtilizatorValidator;

import com.example.sem7.java.validators.ValidarePrietenie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {

    private Service networkService;

    @Override
    public void start(Stage stage) throws IOException {


        networkService = getService();
        initView(stage);
        stage.setWidth(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void initView(Stage primaryStage) throws IOException {

        FXMLLoader userLoader = new FXMLLoader();
        userLoader.setLocation(getClass().getResource("hello-view.fxml"));
        AnchorPane userLayout = userLoader.load();
        primaryStage.setScene(new Scene(userLayout));

        UserController userController = userLoader.getController();
        userController.setService(networkService);

    }

    private Service getService() {
        String url = "jdbc:postgresql://localhost:5432/Social_Network";
        String username = "postgres";
        String password = "coco";

        DbRepository userDBRepository = new DbRepository(url, username, password, new UtilizatorValidator());
        FriendshipsRepository friendshipDBRepository = new DbFriendshipRepository(url, username, password, new ValidarePrietenie());
        return new Service(userDBRepository, friendshipDBRepository);
    }
}