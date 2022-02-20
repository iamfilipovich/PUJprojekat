package com.example.pujprojekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;

public class main extends Application {
    public static Stage primarystage;
    ImageView image = new ImageView("C:\\Users\\Korisnik\\IdeaProjects\\PUJprojekat\\src\\main\\resources\\com\\example\\pujprojekat\\images\\gym.png");

    @Override
    public void start(Stage stage) throws Exception {

        main.primarystage = stage;
        main.showWindow("login.fxml", "Prijavite se na sustav", 500, 400);

    }

    public static void showWindow(String viewName, String title, int w, int h) throws IOException {
        FXMLLoader root = new FXMLLoader(main.class.getResource(viewName));
        primarystage.setTitle(title);
        primarystage.setScene(new Scene(root.load(), w, h));
        primarystage.show();
    }


    public static void main(String[] args) {


        launch();
    }
}