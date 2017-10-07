package com.quocpnguyen.wfd.Utils;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.quocpnguyen.wfd.R;
import com.quocpnguyen.wfd.models.GroceryList;
import com.quocpnguyen.wfd.models.Item;

import java.util.ArrayList;

/**
 * Created by cs on 9/20/17.
 */

public class CustomAdapter extends ArrayAdapter<Item> {
    private GroceryList groceryList;

    public CustomAdapter(Context context, GroceryList groceryList) {
        super(context, R.layout.single_item_layout, groceryList.getItemsList());
        this.groceryList = groceryList;

    }

    public View.OnClickListener updateQuantity() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = (Integer) v.getTag();
                ArrayList<Item> list = groceryList.getItemsList();
                String description = list.get(position).getDescription();

                switch (v.getId()) {
                    case R.id.add_button:
                        groceryList.addItem(new Item(description));
                        break;
                    case R.id.sub_button:
                        groceryList.removeItem(new Item(description));
                        break;
                }
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        ItemView itemView;

        if(convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.single_item_layout, parent, false);
            itemView = new ItemView();
            itemView.txtDescription = (TextView) convertView.findViewById(R.id.textView);
            itemView.txtQuantity = (EditText) convertView.findViewById(R.id.num_text);

            itemView.add = (Button) convertView.findViewById(R.id.add_button);
            itemView.sub = (Button) convertView.findViewById(R.id.sub_button);
            itemView.add.setOnClickListener(updateQuantity());
            itemView.sub.setOnClickListener(updateQuantity());

            itemView.txtDescription.setTag(position);
            itemView.txtQuantity.setTag(position);
            itemView.add.setTag(position);
            itemView.sub.setTag(position);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }

        itemView.txtDescription.setText(dataModel.getDescription());
        if(dataModel.getQuantity() == 0){
            itemView.txtDescription.setPaintFlags(itemView.txtDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            itemView.add.setVisibility(View.INVISIBLE);
            itemView.sub.setVisibility(View.INVISIBLE);
            itemView.txtQuantity.setVisibility(View.INVISIBLE);
        } else {
            itemView.txtQuantity.setText(dataModel.getQuantity() + "");
        }

        // Return the completed view to render on screen
        return convertView;
    }


    public class ItemView {

        private TextView txtDescription;
        private EditText txtQuantity;
        private Button add;
        private Button sub;
    }
}
