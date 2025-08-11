package com.example.servingwebcontent.Model;
public class Booking {
    public int C_id;
    public int R_id;  

    public Booking(int c_id, int r_id) {
        this.C_id = c_id;
        this.R_id = r_id;
    }

    @Override
    public String toString() {
        return "Booking{C_id=" + C_id + ", R_id=" + R_id + "}";
    }
}