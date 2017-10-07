package com.quocpnguyen.wfd.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quocpnguyen.wfd.R;
import com.quocpnguyen.wfd.Utils.CustomAdapter;
import com.quocpnguyen.wfd.models.GroceryList;
import com.quocpnguyen.wfd.models.Item;

/**
 * Created by quoc on 9/8/17.
 */

public class GroceriesActivity extends AppCompatActivity{
    private static String TAG = "GroceriesActivity";
    private ListView listView;
    private GroceryList groceryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate(): getting DBManager");
        setContentView(R.layout.activity_grocery);
        listView = (ListView) findViewById(R.id.list_item);

        Log.d(TAG, "OnCreate(): adding testing item");
        groceryList = GroceryList.getInstance();


        CustomAdapter customAdapter = new CustomAdapter(this, groceryList);
        listView.setAdapter(customAdapter);


    }

}
