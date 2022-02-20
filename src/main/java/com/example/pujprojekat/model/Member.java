package com.example.pujprojekat.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Member extends Table {
    @Entity(type = "INTEGER", size = 10, primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 20)
    String first_name;
    @Entity(type = "VARCHAR", size = 20)
    String last_name;
    @Entity(type = "VARCHAR", size = 15)
    String phone;
    @Entity(type = "VARCHAR", size = 15)
    String birth_date;
    @Entity(type = "INTEGER", size = 10)
    @ForeignKey(table = "Coach", attribute = "id")
    int coach;
    @Entity(type = "INTEGER", size = 10)
    @ForeignKey(table = "Membership", attribute = "id")
    int membership;
    @Entity(type = "INTEGER", size = 10)
    int count;


    public Member(String first_name, String last_name, String phone, String birth_date, int coach,
                  int membership, int count) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.birth_date = birth_date;
        this.coach = coach;
        this.membership = membership;
        this.count = count;
    }

    public Member(int id, String first_name, String last_name, String phone, String birth_date, int coach,
                  int membership, int count) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.birth_date = birth_date;
        this.coach = coach;
        this.membership = membership;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public Coach getCoach() throws Exception {
        return (Coach) Table.get(Membership.class, coach);
    }

    public String getCoachFromId() {
        String query = "SELECT * FROM Coach WHERE id = " + this.coach;
        ResultSet rs;
        try {
            rs = Database.CONNECTION.createStatement().executeQuery(query);
            rs.next();
            return rs.getString("first_name") + " " + rs.getString("last_name");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String getCoachFirstNameFromId() {
        String query = "SELECT * FROM Coach WHERE id = " + this.coach;
        ResultSet rs;
        try {
            rs = Database.CONNECTION.createStatement().executeQuery(query);
            rs.next();
            return rs.getString("first_name");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void setCoach(int coach) {
        this.coach = coach;
    }

    public Membership getMembership() throws Exception {
        return (Membership) Table.get(Membership.class, membership);
    }

    public String getMembershipFromId() {
        String query = "SELECT * FROM Membership WHERE id = " + this.membership;
        ResultSet rs;
        try {
            rs = Database.CONNECTION.createStatement().executeQuery(query);
            rs.next();
            return rs.getString("membershipType");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public int getMembershipCount() {
        String query = "SELECT * FROM Membership WHERE id = " + this.membership;
        ResultSet rs;
        try {
            rs = Database.CONNECTION.createStatement().executeQuery(query);
            rs.next();
            return rs.getInt("amount");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 1000;
    }

    public void setMembership(int membership) {
        this.membership = membership;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount() {
        this.count = this.count + 1;
    }
}
