package com.zireck.projectk.model_deprecated;

import android.content.Context;
import android.database.Cursor;

import com.zireck.projectk.util.DatabaseUtils;

/**
 * Created by Zireck on 14/07/2015.
 */
public class FoodDataSource {
    private DatabaseHelper mDatabaseHelper;

    public FoodDataSource(Context context) {
        mDatabaseHelper = DatabaseHelper.getInstance(context);
    }

    public Food getFood(int id) {
        Cursor cursor = mDatabaseHelper.getReadableDatabase().query(DatabaseUtils.TABLE_FOOD, null, DatabaseUtils.TABLE_FOOD_ID + "=" + id, null, null, null, null);

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();

        /*
            public static final String TABLE_FOOD = "food";
    public static final String TABLE_FOOD_ID = DEFAULT_ID;
    public static final String TABLE_FOOD_NAME = "name";
    public static final String TABLE_FOOD_BRAND = "brand";
    public static final String TABLE_FOOD_ISDRINK = "isdrink";
    public static final String TABLE_FOOD_CALORIES = NUTRIENTS_CALORIES;
    public static final String TABLE_FOOD_FATS = NUTRIENTS_FATS;
    public static final String TABLE_FOOD_CARBOHYDRATES = NUTRIENTS_CARBOHYDRATES;
    public static final String TABLE_FOOD_PROTEINS = NUTRIENTS_PROTEINS;
         */
        Food food = new Food();
        food.setId(cursor.getInt(cursor.getColumnIndex(DatabaseUtils.TABLE_FOOD_ID)));
        food.setName(cursor.getString(cursor.getColumnIndex(DatabaseUtils.TABLE_FOOD_NAME)));
        food.setBrand(cursor.getString(cursor.getColumnIndex(DatabaseUtils.TABLE_FOOD_BRAND)));

        cursor.close();

        return food;
    }
}
