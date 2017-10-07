package com.quocpnguyen.wfd.models;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;
import android.widget.Toast;

import com.quocpnguyen.wfd.activities.GroceriesActivity;

/**
 * Created by cs on 9/17/17.
 */

public class DBHelper {
    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "wtfDatabase.db";

    //table configuration
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "recipes";
    public static final String ID = "_id";
    public static final String RECIPE_NAME = "name";
    public static final String INGREDIENTS = "ingredients";
    public static final String IMAGE_SRC = "img_src";
    public static final String DESCRIPTION = "description";

    private static DBHelper mInstance;

    private Context mContext; // application context

    private SQLiteDatabase sqlDB;


    private DBHelper(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        mContext = context;

        //initialize DB
        sqlDB = new SQLiteOpenHelper(mContext, DB_NAME, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RECIPE_NAME + " TEXT, " +
                        INGREDIENTS + " TEXT, " +
                        IMAGE_SRC + " TEXT," +
                        DESCRIPTION + " TEXT)";
                Log.d(TAG, "onCreate SQL: " + query);
                Toast.makeText(mContext.getApplicationContext(), query, Toast.LENGTH_LONG).show();

                db.execSQL(query);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                String query = "DROP TABLE IF EXIST " + TABLE_NAME;
                Log.d(TAG, "onUpgrade SQL: " + query);

                db.execSQL(query);

                onCreate(db);
            }
        }.getWritableDatabase();
    }

    public static DBHelper instance(Context context) {
        mInstance = new DBHelper(context.getApplicationContext());
        return mInstance;
    }

    public void insertData(Recipe recipe) {
        //using ContentValues to avoid sql format errors
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(RECIPE_NAME, recipe.getName());
            contentValues.put(DESCRIPTION, recipe.getDescription());
            contentValues.put(IMAGE_SRC, recipe.getImg_src());

            String ingredients = recipe.getIngredients().toString();
            contentValues.put(INGREDIENTS, ingredients);

            sqlDB.insert(TABLE_NAME, null, contentValues) ;
        }catch (SQLiteDatabaseCorruptException e){
            Toast.makeText(mContext.getApplicationContext(), recipe.getName() +" is already existed", Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor getAllData() {

        String query = "SELECT * FROM " + TABLE_NAME;
        Log.d(TAG, "getAllData SQL: " + query);
        return sqlDB.rawQuery(query, null);
    }

//    public void updateQunatity(Item item) {
////        String q2 = "UPDATE " + TABLE_NAME + " SET " + QUANTITY_COLUMN + " = " + item.getQuantity()
////                + " WHERE _id " + " = " + item.getDescription();
////        sqlDB.execSQL(q2);
//
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(QUANTITY_COLUMN, item.getQuantity());
//        sqlDB.update(TABLE_NAME, contentValues, ITEM_COLUMN + " = " + item.getDescription(), null) ;
//    }

//    public Cursor getListItem() {
//
//        String query = "SELECT " + ITEM_COLUMN +
//                " FROM " + TABLE_NAME;
//        return sqlDB.rawQuery(query, null);
//    }

    //testing purpose
    public boolean isTableExists() {

        Cursor cursor = sqlDB.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_NAME + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public boolean findItem(String recipeName){
        String q = "SELECT * FROM " + TABLE_NAME + " WHERE " + RECIPE_NAME
                + " = \"" + recipeName + "\"";
        Cursor cursor = sqlDB.rawQuery(q, null);
        return cursor.moveToFirst();
    }
}
