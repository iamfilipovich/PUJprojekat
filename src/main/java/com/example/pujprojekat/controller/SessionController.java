package com.example.pujprojekat.controller;

import com.example.pujprojekat.model.Database;
import com.example.pujprojekat.model.Member;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SessionController implements Initializable {
    @FXML
    private TableColumn<Member, String> activityBirthday_tc;

    @FXML
    private TableColumn<Member, Integer> activityCounter_tc;

    @FXML
    private TableColumn<Member, String> activityMembership_tc;

    @FXML
    private TableColumn<Member, String> activityName_tc;

    @FXML
    private TableColumn<Member, String> activitySurname_tc;

    @FXML
    private TableColumn<Member, String> activityPhone_tc;

    @FXML
    private TableView<Member> table;


    private Connection c = Database.CONNECTION;
    private ObservableList<Member> list = FXCollections.observableArrayList();
    private Member currentMember = null;

    @FXML
    void onActivity() throws Exception {

        if (currentMember == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Molimo odaberite korisnika");
            alert.showAndWait();
        }
        else {
            if(currentMember.getMembershipCount() == 1000) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Greska u bazi podataka");
                alert.showAndWait();
                return;
            }

            if (currentMember.getCount() >= currentMember.getMembershipCount()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Prekoracen broj dolazaka");
                alert.showAndWait();
            } else {
                currentMember.incrementCount();
                currentMember.update();
                updateTable();
            }
        }
    }

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateTable();

        activityName_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirst_name()));
        activitySurname_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLast_name()));
        activityBirthday_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getBirth_date()));
        activityPhone_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhone()));
        activityCounter_tc.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        activityMembership_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMembershipFromId()));

        table.setItems(list);

        table.setOnMousePressed(event -> {
            currentMember = table.getSelectionModel().getSelectedItem();
        });

    }

    private void updateTable() {
        ResultSet rs = null;

        try {
            rs = c.createStatement().executeQuery("SELECT * FROM Member");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        list.clear();

        while (true) {
            try {
                if (!rs.next()) break;
                list.add(new Member(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("phone"), rs.getString("birth_date"),
                        rs.getInt("coach"), rs.getInt("membership"),
                         rs.getInt("count")));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
