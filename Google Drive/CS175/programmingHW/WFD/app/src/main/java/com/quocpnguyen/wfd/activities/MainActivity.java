package com.quocpnguyen.wfd.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.quocpnguyen.wfd.R;
import com.quocpnguyen.wfd.Utils.ExpandableListAdapter;
import com.quocpnguyen.wfd.models.DBHelper;
import com.quocpnguyen.wfd.models.GroceryList;
import com.quocpnguyen.wfd.models.Item;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDB();
    }

    public void newDish(View view){
        Intent intent = new Intent(this, NewDishActivity.class);
        startActivity(intent);

    }

    public void viewRecipes(View view){
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);

    }

    public void viewGroceries(View view){
        Log.d(TAG, "Groceries icon selected");
        Intent intent = new Intent(this, GroceriesActivity.class);
        startActivity(intent);

    }

    public void viewMeals(View view){
        Intent intent = new Intent(this, MealsActivity.class);
        startActivity(intent);

    }

    private void loadDB(){
        //add the default value to the meal spinner
        ExpandableListAdapter.mealsOptions.add(ExpandableListAdapter.DEFAULT_SELECTION);

        DBHelper dbHelper = DBHelper.instance(this);
        Cursor cursor = dbHelper.getAllData();
        GroceryList groceryList = GroceryList.getInstance();
        if(groceryList.getItemsList().size() <= 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int columnIndex = cursor.getColumnIndex(DBHelper.INGREDIENTS);
                String ingredients = cursor.getString(columnIndex);
                String[] ingredients_list = ingredients.split(",");
                for (String s : ingredients_list) {
                    groceryList.addItem(new Item(s.trim()));
                }

                columnIndex = cursor.getColumnIndex(DBHelper.RECIPE_NAME);
                String name = cursor.getString(columnIndex);
                ExpandableListAdapter.mealsOptions.add(name);
                cursor.moveToNext();
            }
        }
    }
}
