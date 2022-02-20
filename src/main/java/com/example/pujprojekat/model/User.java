package com.example.pujprojekat.model;


public class User extends Table {
    @Entity(type = "INTEGER", size = 10, primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 15)
    String username;
    @Entity(type = "VARCHAR", size = 20)
    String password;
    @Entity(type = "VARCHAR", size = 20)
    String first_name;
    @Entity(type = "VARCHAR", size = 20)
    String last_name;
    @Entity(type = "INTEGER", size = 10)
    int salary;
    @Entity(type = "VARCHAR", size = 10)
    String type;

    public User(int id, String username, String password, String first_name, String last_name, int salary, String type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.salary = salary;
        this.type = type;
    }

    public User(String username, String password, String first_name, String last_name, int salary, String type) {
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.salary = salary;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() { return first_name; }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() { return last_name; }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getSalary() { return salary; }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
