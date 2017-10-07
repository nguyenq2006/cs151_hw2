package com.quocpnguyen.wfd.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.quocpnguyen.wfd.R;
import com.quocpnguyen.wfd.models.DBHelper;
import com.quocpnguyen.wfd.models.GroceryList;
import com.quocpnguyen.wfd.models.Item;
import com.quocpnguyen.wfd.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by quoc on 9/8/17.
 */

public class NewDishActivity extends AppCompatActivity {
    private LinearLayout ingredient_list;
    private EditText dish_name;
    private ArrayList<CustomSpinner> customSpinners;
    private EditText dish_description;
    private ImageView dish_image;

    private final String DEFAULT_IMG = "http://www.whatsfordinnernh.com/wp-content/uploads/2016/06/WFD_FNL_Facebook-01.png";

    private DBHelper dbHelper;

    private static final int SELECTED_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        dbHelper = DBHelper.instance(this);

        ingredient_list = (LinearLayout) findViewById(R.id.ingredients_list);
        dish_name = (EditText) findViewById(R.id.dish_name);
        dish_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String name = dish_name.getText().toString().trim();
                    if(dbHelper.findItem(name)){
                        Toast.makeText(v.getContext(), "Recipe Already Existed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        dish_description =(EditText) findViewById(R.id.description);
        dish_image = (ImageView) findViewById(R.id.img_src);
        dish_image.setTag(R.drawable.wfd);
        displayImage(DEFAULT_IMG);

        customSpinners = new ArrayList<>();
        addIngredientInput(ingredient_list);

    }

    public void addIngredientInput(View view){
        if(customSpinners.size() < 10) {

            GroceryList groceryList = GroceryList.getInstance();
            ArrayList<Item> itemsList = groceryList.getItemsList();
            ArrayList<String> ingredients = new ArrayList<>();
            for (Item item : itemsList){
                ingredients.add(item.getDescription());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ingredients);

            //create a new AutoCompleteTextView dynamically

            CustomSpinner custom = new CustomSpinner(this, arrayAdapter);
            customSpinners.add(custom);
            AutoCompleteTextView autoCompleteTextView = custom.autoCompleteTextView;
            autoCompleteTextView.setTag(custom);

            ingredient_list.addView(autoCompleteTextView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }else {
            Toast.makeText(this, "You have reach the max number of ingredients", Toast.LENGTH_SHORT).show();
        }

    }

    public class CustomSpinner{
        public AutoCompleteTextView autoCompleteTextView;



        public CustomSpinner(Context context, ArrayAdapter<String> arrayAdapter) {

            autoCompleteTextView = new AutoCompleteTextView(context);
            autoCompleteTextView.setAdapter(arrayAdapter);
            autoCompleteTextView.setThreshold(0);
            autoCompleteTextView.setHint("Ingredient");
            autoCompleteTextView.setLines(1);
            autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        autoCompleteTextView.showDropDown();
                    }
                }
            });

        }
    }

    public void buttonClickListener(View view){
        switch (view.getId()){
            case R.id.save_action:
                String name = dish_name.getText().toString().trim();
                if(dbHelper.findItem(name)){
                    Toast.makeText(view.getContext(), "Recipe Already Existed", Toast.LENGTH_LONG).show();
                    return;
                }
                String description = dish_description.getText().toString();
                String img_src = dish_image.getTag().toString();

                String ingredients = "";
                for (CustomSpinner c : customSpinners) {
                    AutoCompleteTextView text = c.autoCompleteTextView;
                    String s = text.getText().toString().trim();
                    if(!s.equals("")) {
                        ingredients += s + ", ";
                        GroceryList gl = GroceryList.getInstance();
                        gl.addItem(new Item(s));
                    }
                }
                ingredients = ingredients.substring(0, ingredients.lastIndexOf(","));

                Recipe newRecipe = new Recipe(name, ingredients,img_src, description);

                dbHelper.insertData(newRecipe);

                Toast.makeText(this, "Recipe Saved", Toast.LENGTH_LONG).show();
            default:
                Intent homeActivity = new Intent(this, MainActivity.class);
                customSpinners = new ArrayList<>();
                startActivity(homeActivity);
        }

    }

    public void loadImage(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.add_img_dialog, null);

        ImageView gallery = (ImageView) mView.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);

            }
        });


        ImageView web = (ImageView) mView.findViewById(R.id.web);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewDishActivity.this);
                builder.setTitle("Image Url");

                // Set up the input
                final EditText input = new EditText(NewDishActivity.this);
                input.setText("https://truffle-assets.imgix.net/pxqrocxwsjcc_5E1zqcdOhOmC6sEseaOMOa_chicken-alfredo_squareThumbnail_en-US.jpeg");
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface input_dialog, int which) {
                        String uri = input.getText().toString();
                        displayImage(uri);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface input_dialog, int which) {
                        input_dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SELECTED_PICTURE:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getApplication().getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePatch = cursor.getString(columnIndex);
                    cursor.close();

                    displayImage(filePatch);
//                    Bitmap selection = BitmapFactory.decodeFile(filePatch);
//                    Drawable img = new BitmapDrawable(getResources(), selection);
//
//                    dish_image.setTag(img);
                }
        }
    }

    public void displayImage(String uri){
        Picasso.with(NewDishActivity.this).load(uri).into(dish_image);
        dish_image.setTag(uri);
    }
}
