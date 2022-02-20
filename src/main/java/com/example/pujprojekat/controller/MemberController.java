package com.example.pujprojekat.controller;

import com.example.pujprojekat.model.Database;
import com.example.pujprojekat.model.Member;
import javafx.beans.property.ReadOnlyStringWrapper;
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

public class MemberController implements Initializable {

    @FXML
    public TableColumn<Member, String> memberFirstName_tc;

    @FXML
    public TableColumn<Member, String> memberLastName_tc;

    @FXML
    public TableColumn<Member, String> memberPhone_tc;

    @FXML
    public TableColumn<Member, String> memberBirthday_tc;

    @FXML
    public TableColumn<Member, String> memberMembership_tc;

    @FXML
    public TableColumn<Member, String> memberCoach_tc;

    @FXML
    public TableView<Member> table;

    @FXML
    private TextField memberBirthday_txt;

    @FXML
    private TextField memberFirstName_txt;

    @FXML
    private TextField memberPhone_txt;

    @FXML
    private TextField memberLastName_txt;

    @FXML
    private ChoiceBox<String> membershipOptions_cb;

    @FXML
    private CheckBox coachCheckBox;


    @FXML
    private ChoiceBox<String> coachOptions_cb;

    private Connection c = Database.CONNECTION;
    private ObservableList<Member> list = FXCollections.observableArrayList();
    private ObservableList<String> memberships = FXCollections.observableArrayList();
    private ObservableList<String> coaches = FXCollections.observableArrayList();
    private int editId;

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        updateTable();
        updateMemberships();
        updateCoaches();
        membershipOptions_cb.getItems().addAll(memberships);
        coachOptions_cb.getItems().addAll(coaches);

        memberFirstName_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirst_name()));
        memberLastName_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLast_name()));
        memberPhone_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhone()));
        memberBirthday_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getBirth_date()));
        memberCoach_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCoachFromId()));
        memberMembership_tc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMembershipFromId()));

        table.setItems(list);

        table.setOnMousePressed(event -> {
            Member m = table.getSelectionModel().getSelectedItem();
            editId = m.getId();
            memberFirstName_txt.setText(m.getFirst_name());
            memberLastName_txt.setText(m.getLast_name());
            memberBirthday_txt.setText(m.getBirth_date());
            memberPhone_txt.setText(m.getPhone());

            try {
                membershipOptions_cb.setValue(m.getMembershipFromId());
                coachOptions_cb.setValue(m.getCoachFirstNameFromId());
                coachCheckBox.setSelected(true);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @FXML
    public void onAdd() throws SQLException {
        String firstName = memberFirstName_txt.getText();
        String lastName = memberLastName_txt.getText();
        String birthday = memberBirthday_txt.getText();
        String phone = memberPhone_txt.getText();
        String membership = membershipOptions_cb.getSelectionModel().getSelectedItem();
        String coach = coachOptions_cb.getSelectionModel().getSelectedItem();
        int membershipId = 0, coachId = 0;
        Member m = null;

        String query = "SELECT * FROM Membership WHERE membershipType = \'" + membership + "\'";
        ResultSet rs = c.createStatement().executeQuery(query);
        if (rs.next())
            membershipId = rs.getInt("id");

        if (coachCheckBox.isSelected()) {
            String query2 = "SELECT * FROM Coach WHERE first_name = \'" + coach + "\'";
            ResultSet rs2 = c.createStatement().executeQuery(query2);
            if (rs2.next())
                coachId = rs2.getInt("id");

            m = new Member(firstName, lastName, phone, birthday, coachId, membershipId, 0);
        } else {
            // id 2 je prazan
            m = new Member(firstName, lastName, phone, birthday, 2, membershipId, 0);
        }

        try {
            m.save();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        updateTable();
    }

    @FXML
    public void onEdit() throws Exception {
        String firstName = memberFirstName_txt.getText();
        String lastName = memberLastName_txt.getText();
        String birthday = memberBirthday_txt.getText();
        String phone = memberPhone_txt.getText();
        String membership = membershipOptions_cb.getSelectionModel().getSelectedItem();
        String coach = coachOptions_cb.getSelectionModel().getSelectedItem();
        int membershipId = 0, coachId = 0;

        String query = "SELECT * FROM Membership WHERE membershipType = \'" + membership + "\'";
        ResultSet rs = c.createStatement().executeQuery(query);
        if (rs.next())
            membershipId = rs.getInt("id");

        if (coachCheckBox.isSelected()) {
            String query2 = "SELECT * FROM Coach WHERE first_name = \'" + coach + "\'";
            ResultSet rs2 = c.createStatement().executeQuery(query2);
            if (rs2.next())
                coachId = rs2.getInt("id");
        }

        Member member = null;
        for (Member m : list) {
            if (m.getId() == editId)
                member = m;
        }

        if (firstName.equals("") || lastName.equals("") || birthday.equals("") || phone.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update error");
            alert.setHeaderText("Molimo popunite sva polja");
            alert.showAndWait();
        }

        if (member != null) {
            member.setFirst_name(firstName);
            member.setLast_name(lastName);
            member.setPhone(phone);
            member.setBirth_date(birthday);
            member.setMembership(membershipId);
            if (coachCheckBox.isSelected())
                member.setCoach(coachId);
            member.setCount(0);
            member.update();
        }

        updateTable();
    }

    @FXML
    public void onDelete() throws Exception {
        Member member = null;
        for (Member m : list) {
            if (m.getId() == editId)
                member = m;
        }

        if (member != null)
            member.delete();

        updateTable();
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
                        rs.getInt("coach"), rs.getInt("membership"), rs.getInt("count")));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void updateMemberships() {
        ResultSet rs = null;

        try {
            rs = c.createStatement().executeQuery("SELECT * FROM Membership");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        memberships.clear();

        while (true) {
            try {
                if (!rs.next()) break;
                memberships.add(rs.getString("membershipType"));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void updateCoaches() {
        ResultSet rs = null;

        try {
            rs = c.createStatement().executeQuery("SELECT * FROM Coach");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        coaches.clear();

        while (true) {
            try {
                if (!rs.next()) break;
                coaches.add(rs.getString("first_name"));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}