package com.quocpnguyen.wfd.models;

import android.support.annotation.NonNull;

/**
 * Created by cs on 9/23/17.
 */

public class Item {
    private String description;
    private int quantity;

    public Item(String description, int quantity){
        this.description = description;
        this.quantity = quantity;

    }

    public Item(String description){
        this.description = description;
        this.quantity = 1;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString(){
        return "Item: " + description +"; quantity: " + quantity;
    }

    @Override
    public boolean equals(Object o) {
        Item other = (Item) o;
        return description.equals(other.description);
    }
}
