package com.example.pujprojekat.controller;

import com.example.pujprojekat.ActiveUser;
import com.example.pujprojekat.main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SideNavigationController implements Initializable {

    @FXML
    private Label activeUser;

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1){
        activeUser.setText(ActiveUser.user.getUsername());
    }


    @FXML
    void onAddActivity(ActionEvent event)throws Exception {
        main.showWindow("session.fxml","FIM GYM APLIKACIJA", 1000, 600);
    }

    @FXML
    void onAddMember(ActionEvent event)throws Exception{
            main.showWindow("Member.fxml","FIM GYM APLIKACIJA", 1000, 600);
    }

    @FXML
    void onAddSupplement(ActionEvent event)throws Exception {
            main.showWindow("suplements.fxml","FIM GYM APLIKACIJA", 1000, 600);
    }

    @FXML
    void onAddUser(ActionEvent event)throws Exception {
        main.showWindow("User.fxml","FIM GYM APLIKACIJA", 1000, 600);
    }

    @FXML
    void onLogout(ActionEvent event)throws Exception
    {
        ActiveUser.user = null;
        main.showWindow("login.fxml","Prijavite se na sustav", 500, 400);
    }

}
