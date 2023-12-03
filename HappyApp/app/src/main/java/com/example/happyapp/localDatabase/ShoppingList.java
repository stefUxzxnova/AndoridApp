package com.example.happyapp.localDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class ShoppingList {
    private String id;
    private String title;
    private double totalPrice;

    public ShoppingList(String id, String title, double totalPrice) {
        this.id = id;
        this.title = title;
        this.totalPrice = totalPrice;
    }
    public ShoppingList(String title, double totalPrice) {
        this.title = title;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
