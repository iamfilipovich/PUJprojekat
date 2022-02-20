package com.example.pujprojekat.model;

public class Membership extends Table{
    @Entity(type = "INTEGER", size = 10, primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 15)
    String membershipType;
    @Entity(type = "INTEGER", size = 15)
    int price;
    @Entity(type = "INTEGER", size = 15)
    int amount;

    public Membership(String membershipType, int price, int amount) {
        this.membershipType = membershipType;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
