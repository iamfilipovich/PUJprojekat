package com.example.pujprojekat.controller;

import com.example.pujprojekat.ActiveUser;
import com.example.pujprojekat.enums.UserType;
import com.example.pujprojekat.model.Database;
import com.example.pujprojekat.model.User;
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

public class UserController implements Initializable {

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> type_tc;

    @FXML
    private TableColumn<User, String> lastName_tc;

    @FXML
    private TableColumn<User, Integer> salary_tc;

    @FXML
    private TextField userUsername_txt;

    @FXML
    private PasswordField userPassword_txt;

    @FXML
    private TextField userFirstName_txt;

    @FXML
    private TextField userSalary_txt;

    @FXML
    private TextField userLastName_txt;

    @FXML
    private TableColumn<User, String> firstName_tc;

    @FXML
    private ChoiceBox<UserType> userType_cb;

    private Connection c = Database.CONNECTION;
    private ObservableList<User> list = FXCollections.observableArrayList();
    private UserType[] userTypes = {UserType.WORKER, UserType.ADMIN};
    private int editId;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        userType_cb.getItems().addAll(userTypes);
        updateTable();

        firstName_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirst_name()));
        lastName_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLast_name()));
        type_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getType()));
        salary_tc.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSalary()).asObject());

        table.setItems(list);

        table.setOnMousePressed(event -> {
            User u = table.getSelectionModel().getSelectedItem();
            editId = u.getId();
            userUsername_txt.setText(u.getUsername());
            userPassword_txt.setText(u.getPassword());
            userType_cb.setValue(UserType.valueOf(u.getType()));
            userFirstName_txt.setText(u.getFirst_name());
            userLastName_txt.setText(u.getLast_name());
            userSalary_txt.setText(Integer.toString(u.getSalary()));
        });
    }

    @FXML
    public void onAdd() {
        if (ActiveUser.user.getType().equals(UserType.ADMIN.toString())) {
            String username = userUsername_txt.getText();
            String password = userPassword_txt.getText();
            String type = userType_cb.getSelectionModel().getSelectedItem().toString();
            String firstName = userFirstName_txt.getText();
            String lastName = userLastName_txt.getText();
            int salary = Integer.parseInt(userSalary_txt.getText());

            boolean flag = false;
            for (User u : list) {
                if (username.equals(u.getUsername())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Add error");
                    alert.setHeaderText("Korisnicko ime vec postoji");
                    alert.showAndWait();
                    flag = true;
                }
            }

            if (!flag) {
                User u = new User(username, password, firstName, lastName, salary, type);
                try {
                    u.save();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                updateTable();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add error");
            alert.setHeaderText("Samo ADMIN može dodavati nove korisnike");
            alert.showAndWait();
        }
    }

    @FXML
    public void onEdit() throws Exception {
        if (ActiveUser.user.getType().equals(UserType.ADMIN.toString())) {
            String username = userUsername_txt.getText();
            String password = userPassword_txt.getText();
            String type = userType_cb.getSelectionModel().getSelectedItem().toString();
            String firstName = userFirstName_txt.getText();
            String lastName = userLastName_txt.getText();
            int salary = Integer.parseInt(userSalary_txt.getText());

            User user = null;
            for (User u : list) {
                if (u.getId() == editId)
                    user = u;
            }

            if (username.equals("") || password.equals("") || type.equals("") || firstName.equals("") || lastName.equals("") || salary == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update error");
                alert.setHeaderText("Molimo popunite sva polja");
                alert.showAndWait();
            }

            if (user != null) {
                user.setUsername(username);
                user.setPassword(password);
                user.setType(type);
                user.setFirst_name(firstName);
                user.setLast_name(lastName);
                user.setSalary(salary);
                user.update();
            }
            updateTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add error");
            alert.setHeaderText("Samo ADMIN može uređivati korisnike");
            alert.showAndWait();
        }
    }

    @FXML
    public void onDelete() throws Exception {
        if (ActiveUser.user.getType().equals(UserType.ADMIN.toString())) {
            for (User u : list) {
                if (u.getId() == editId)
                    u.delete();
            }

            updateTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add error");
            alert.setHeaderText("Samo ADMIN može brisati korisnike");
            alert.showAndWait();
        }
    }

    private void updateTable() {
        ResultSet rs = null;

        try {
            rs = c.createStatement().executeQuery("SELECT * FROM User");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        list.clear();

        while (true) {
            try {
                if (!rs.next()) break;
                list.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("first_name"), rs.getString("last_name"),
                        rs.getInt("salary"), rs.getString("type")));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
