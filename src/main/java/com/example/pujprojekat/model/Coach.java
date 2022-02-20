package com.example.pujprojekat.model;

public class Coach extends Table{
    @Entity(type = "INTEGER",size = 10, primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 20)
    String first_name;
    @Entity(type = "VARCHAR", size = 20)
    String last_name;
    @Entity(type = "INTEGER", size = 20)
    String years_of_experience;

    public Coach(String first_name, String last_name, String years_of_experience) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.years_of_experience = years_of_experience;
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

    public String getYears_of_experience() {
        return years_of_experience;
    }

    public void setYears_of_experience(String years_of_experience) {
        this.years_of_experience = years_of_experience;
    }
}

