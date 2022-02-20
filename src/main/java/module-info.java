module com.example.pujprojekat {
    requires  javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.pujprojekat;
    opens com.example.pujprojekat.controller;
}