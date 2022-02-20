package com.example.pujprojekat.model;

public class Supplement extends Table{
    @Entity(type = "INTEGER", size = 10, primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 20)
    String name;
    @Entity(type = "INTEGER", size = 10)
    int amount;
    @Entity(type = "INTEGER", size = 10)
    int price;

    public Supplement(int id, String name, int amount, int price) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public Supplement(String name, int amount, int price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void decreaseAmount(int amount) {
        this.amount -= amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
