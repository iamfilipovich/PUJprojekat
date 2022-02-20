package com.example.pujprojekat.controller;

import com.example.pujprojekat.model.Database;
import com.example.pujprojekat.model.Supplement;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplementsController implements Initializable {

    @FXML
    private TextField addSupplementName_txt;

    @FXML
    private TextField addSupplementAmount_txt;

    @FXML
    private TextField addSupplementPrice_txt;

    @FXML
    private TextField amountToSell_txt;

    @FXML
    private ChoiceBox<String> supplementToSell_cb;

    @FXML
    private ChoiceBox<String> memberToSell_cb;

    @FXML
    private TableColumn<Supplement, String> supplementName_tc;

    @FXML
    private TableColumn<Supplement, Integer> supplementAmount_tc;

    @FXML
    private TableColumn<Supplement, Integer> supplementPrice_tc;

    @FXML
    public TableView<Supplement> table;

    private Connection c = Database.CONNECTION;
    private ObservableList<Supplement> list = FXCollections.observableArrayList();
    private ObservableList<String> members = FXCollections.observableArrayList();
    private ObservableList<String> supplements = FXCollections.observableArrayList();

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateTable();
        updateMembers();
        updateSupplements();

        supplementName_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        supplementAmount_tc.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAmount()).asObject());
        supplementPrice_tc.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject());

        table.setItems(list);
    }

    @FXML
    void addSupplement() {
        String name = addSupplementName_txt.getText();
        int amount = Integer.parseInt(addSupplementAmount_txt.getText());
        int price = Integer.parseInt(addSupplementPrice_txt.getText());
        boolean flag = false;

        for(Supplement s : list) {
            if (name.equals(s.getName()) && price == s.getPrice()) {
                s.addAmount(amount);
                flag = true;

                try {
                    s.update();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        if(!flag) {
            Supplement s = new Supplement(name, amount, price);
            try {
                s.save();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        updateTable();
        updateSupplements();
    }

    @FXML
    void sellSupplement() throws Exception {
        String supplement = supplementToSell_cb.getValue();
        int amount = Integer.parseInt(amountToSell_txt.getText());

        for (Supplement s : list) {
            if (s.getName().equals(supplement)) {

                if (amount > s.getAmount()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Nedovoljna kolicina suplementa");
                    alert.showAndWait();
                    break;
                }

                s.decreaseAmount(amount);
                s.update();

                if (s.getAmount() <= 0)
                    s.delete();

                updateTable();
                updateSupplements();
                return;
            }
        }
    }

    private void updateTable() {
        ResultSet rs = null;

        try {
            rs = c.createStatement().executeQuery("SELECT * FROM Supplement");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        list.clear();

        while (true) {
            try {
                if (!rs.next()) break;
                list.add(new Supplement(rs.getInt("id"), rs.getString("name"), rs.getInt("amount"),
                        rs.getInt("price")));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void updateMembers() {
        ResultSet rs = null;

        try {
            rs = c.createStatement().executeQuery("SELECT * FROM Member");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        members.clear();

        while (true) {
            try {
                if (!rs.next()) break;
                members.add(rs.getString("first_name") + " " + rs.getString("last_name"));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        memberToSell_cb.getItems().addAll(members);
    }

    private void updateSupplements() {
        ResultSet rs = null;

        try {
            rs = c.createStatement().executeQuery("SELECT * FROM Supplement");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        supplements.clear();

        while (true) {
            try {
                if (!rs.next()) break;
                supplements.add(rs.getString("name"));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        supplementToSell_cb.getItems().addAll(supplements);
    }

}
