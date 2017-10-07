package com.quocpnguyen.wfd.Utils;

import android.content.Context;
import android.database.Cursor;

import com.quocpnguyen.wfd.models.DBHelper;
import com.quocpnguyen.wfd.models.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class RecipeContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Recipe> ITEMS;

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Recipe> ITEM_MAP;


    public RecipeContent(Context context) {
//        if(ITEMS.size() <= 0) {

        ITEMS = new ArrayList<Recipe>();
        ITEM_MAP = new HashMap<String, Recipe>();

            DBHelper dbHelper = DBHelper.instance(context);
            Cursor cursor = dbHelper.getAllData();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int columnIndex = cursor.getColumnIndex(DBHelper.RECIPE_NAME);
                String recipeName = cursor.getString(columnIndex);

                columnIndex = cursor.getColumnIndex(DBHelper.INGREDIENTS);
                String ingredients = cursor.getString(columnIndex);

                columnIndex = cursor.getColumnIndex(DBHelper.IMAGE_SRC);
                String img_src = cursor.getString(columnIndex);

                columnIndex = cursor.getColumnIndex(DBHelper.DESCRIPTION);
                String recipeDescription = cursor.getString(columnIndex);

                Recipe recipe = new Recipe(recipeName, ingredients, img_src, recipeDescription);
                addItem(recipe);

                cursor.moveToNext();
            }
//        }
    }

    private static void addItem(Recipe item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getName(), item);
    }


}
