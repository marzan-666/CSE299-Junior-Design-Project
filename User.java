package com.example.pizzaorder;

public class User {

    private String Address;
    private String Block;
    private String House;
    private String Name;
    private String Password;
    private String Road;

    public User(String address, String block, String house, String name, String password, String road) {
        Address = address;
        Block = block;
        House = house;
        Name = name;
        Password = password;
        Road = road;
    }

    public User() {

    }

    public String getAddress() {
        return Address;
    }

    public String getBlock() {
        return Block;
    }

    public String getHouse() {
        return House;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public String getRoad() {
        return Road;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public void setHouse(String house) {
        House = house;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setRoad(String road) {
        Road = road;
    }
}
