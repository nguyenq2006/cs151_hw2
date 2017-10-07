package com.quocpnguyen.wfd.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.quocpnguyen.wfd.R;

/**
 * Created by quoc on 9/8/17.
 */

public class MealsActivity extends AppCompatActivity {
    private ExpandableListView explistView;
    private com.quocpnguyen.wfd.Utils.ExpandableListAdapter mlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        explistView = (ExpandableListView) findViewById(R.id.expanded_list);
        mlistAdapter = new com.quocpnguyen.wfd.Utils.ExpandableListAdapter(this);

        explistView.setAdapter(mlistAdapter);

    }
}
