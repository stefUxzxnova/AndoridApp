package com.example.happyapp.localDatabase;

public class ShoppingListItem {
    private int itemId;
    private String name;
    private int quantity;
    private double price;
    private int shoppingListId;

    public ShoppingListItem(String name, int quantity, double price, int shoppingListId) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.shoppingListId = shoppingListId;
    }
    public ShoppingListItem(int id, String name, int quantity, double price, int shoppingListId) {
        this.itemId = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public int getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
}
