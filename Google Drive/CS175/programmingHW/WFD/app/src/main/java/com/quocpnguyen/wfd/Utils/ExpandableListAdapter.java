package com.quocpnguyen.wfd.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.quocpnguyen.wfd.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by cs on 10/6/17.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private List<String> listDataHeader;
    private HashMap<String, String[]>  listDataChild;
    public static ArrayList<String> mealsOptions = new ArrayList<>();

    private Context context;
    public static final String DEFAULT_SELECTION = "Eating Out";

    public ExpandableListAdapter(Context context) {
        this.context = context;
        listDataChild = new HashMap<>();

        listDataHeader = new ArrayList<>();
        listDataHeader.add("SUNDAY");
        listDataHeader.add("MONDAY");
        listDataHeader.add("TUESDAY");
        listDataHeader.add("WEDNESDAY");
        listDataHeader.add("THURSDAY");
        listDataHeader.add("FRIDAY");
        listDataHeader.add("SATURDAY");

    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String dayHeader = (String) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_group, null);
        }

        TextView header = (TextView) convertView.findViewById(R.id.day_header);
        header.setText(dayHeader);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meal_item, null);
        }

        DayView dayView = new DayView();
        dayView.spinner1 = (Spinner) convertView.findViewById(R.id.meal_selection1);
        dayView.spinner2 = (Spinner) convertView.findViewById(R.id.meal_selection2);
        dayView.spinner3 = (Spinner) convertView.findViewById(R.id.meal_selection3);

        dayView.spinner1.setTag(dayView);
        dayView.spinner2.setTag(dayView);
        dayView.spinner3.setTag(dayView);

        dayView.spinner1.setAdapter(newAdapter());
        dayView.spinner2.setAdapter(newAdapter());
        dayView.spinner3.setAdapter(newAdapter());

        dayView.spinner1.setOnItemSelectedListener(itemSelectedListener());
        dayView.spinner2.setOnItemSelectedListener(itemSelectedListener());
        dayView.spinner3.setOnItemSelectedListener(itemSelectedListener());


        convertView.setTag(dayView);

        return convertView;
    }


    private ArrayAdapter<String> newAdapter(){
        return  new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mealsOptions);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1; //each meal is a child
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(this.listDataHeader.get(groupPosition))
                [childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private AdapterView.OnItemSelectedListener itemSelectedListener(){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = parent.getItemAtPosition(position).toString();
                if(!selectedItemText.equals(DEFAULT_SELECTION)){
                    mealsOptions.remove(selectedItemText);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    public class DayView {
        public Spinner spinner1;
        public Spinner spinner2;
        public Spinner spinner3;
    }
}
