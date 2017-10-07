package com.quocpnguyen.wfd.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cs on 9/27/17.
 */

public class GroceryList {
    private static final GroceryList groceryList = new GroceryList();
    private ArrayList<Item> itemsList;

    public GroceryList(){
        itemsList = new ArrayList<>();

    }

    public static GroceryList getInstance(){
        return groceryList;
    }

    public void addItem(Item newItem){
        if(itemsList.contains(newItem)){
            int i = itemsList.indexOf(newItem);
            Item item = itemsList.get(i);
            int quantity = item.getQuantity() + 1;
            itemsList.set(i, new Item(item.getDescription(), quantity));
        } else {
            itemsList.add(newItem);
        }
    }

    public void removeItem(Item deleteItem){
        if(itemsList.contains(deleteItem)){
            int i = itemsList.indexOf(deleteItem);
            Item item = itemsList.get(i);
            int quantity = item.getQuantity() - 1;
            if(quantity >= 0){
                itemsList.set(i, new Item(item.getDescription(), quantity));
            }
        }
    }

    public ArrayList<Item> getItemsList(){
        return itemsList;
    }
}
