package com.example.servingwebcontent.Model;

public class Room {
    public int id;
    public String type;
    public double price;
    public boolean status;

    public Room(int id, String type, double price, boolean status) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room{id=" + id + ", type='" + type + "', price=" + price + ", status=" + (status ? "Available" : "Not Available") + "}";
    }
}