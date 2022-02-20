package com.example.pujprojekat.controller;

import com.example.pujprojekat.ActiveUser;
import com.example.pujprojekat.main;
import com.example.pujprojekat.model.Database;
import com.example.pujprojekat.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button exit_btn;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password_txt;

    @FXML
    private TextField username_txt;

    Connection c = Database.CONNECTION;

    @FXML
    void onExit() {
    }

    @FXML
    void onLogin() throws IOException {
        //provjeri jesu li uneseni i username i password
        if (username_txt.getText().equals("") || password_txt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login error");
            alert.setHeaderText("Molimo popunite sva polja");
            alert.showAndWait();
        } else {
            ObservableList<User> list = FXCollections.observableArrayList();
            ResultSet rs = null;
            try {
                rs = c.createStatement().executeQuery("SElECT * FROM User");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            //spremi sve usere u list
            while (true) {
                try {
                    if (!rs.next()) break;
                    list.add(new User(rs.getString("username"), rs.getString("password"),
                            rs.getString("first_name"), rs.getString("last_name"),
                            rs.getInt("salary"), rs.getString("type")));
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            }

            //potrazi poklapaju li se username i password sa ijednim iz baze
            boolean success = false;

            for (User u : list) {
                String username = u.getUsername();
                String password = u.getPassword();
                if (username.equals(username_txt.getText()) && password.equals(password_txt.getText())) {
                    //promijeni ekran

                    ActiveUser.user = u;

                    main.showWindow(
                            "sidenavigation.fxml",
                            "FIM GYM APLIKACIJA", 1000, 600);
                    success = true;
                    break;
                }
            }
            if(!success) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login error");
                alert.setHeaderText("Username ili password netočni");
                alert.showAndWait();
            }
        }
    }
}
