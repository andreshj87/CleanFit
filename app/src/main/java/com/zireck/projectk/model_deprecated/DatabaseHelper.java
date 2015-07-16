package com.zireck.projectk.model_deprecated;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zireck.projectk.util.DatabaseUtils;

/**
 * Created by Zireck on 14/07/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper mInstance = null;

    private static final String DATABASE_NAME = "projectk.db";
    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableFood(db);
        createTableMeal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTableFood(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE ");
        query.append(DatabaseUtils.TABLE_FOOD);
        query.append(" (");
        query.append(DatabaseUtils.TABLE_FOOD_ID);
        query.append(" int primary key autoincrement, ");
        query.append(DatabaseUtils.TABLE_FOOD_NAME);
        query.append(" text not null, ");
        query.append(DatabaseUtils.TABLE_FOOD_BRAND);
        query.append(" text not null, ");
        query.append(DatabaseUtils.TABLE_FOOD_ISDRINK);
        query.append(" boolean not null check (");
        query.append(DatabaseUtils.TABLE_FOOD_ISDRINK);
        query.append(" IN (0,1), ");
        query.append(DatabaseUtils.TABLE_FOOD_CALORIES);
        query.append(" real not null, ");
        query.append(DatabaseUtils.TABLE_FOOD_FATS);
        query.append(" real not null, ");
        query.append(DatabaseUtils.TABLE_FOOD_CARBOHYDRATES);
        query.append(" real not null, ");
        query.append(DatabaseUtils.TABLE_FOOD_PROTEINS);
        query.append(" real not null)");

        db.execSQL(query.toString());
    }

    private void createTableMeal(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE ");
        query.append(DatabaseUtils.TABLE_MEAL);
        query.append(" (");
        query.append(DatabaseUtils.TABLE_MEAL_ID);
        query.append(" int primary key autoincrement, ");
        query.append(DatabaseUtils.TABLE_MEAL_GRAMS);
        query.append(" int not null, ");
        query.append(DatabaseUtils.TABLE_MEAL_DATE);
        query.append(" int not null, ");
        query.append(DatabaseUtils.TABLE_MEAL_MEALTIME);
        query.append(" text not null, ");
        query.append(DatabaseUtils.TABLE_MEAL_CALORIES);
        query.append(" real not null, ");
        query.append(DatabaseUtils.TABLE_MEAL_FATS);
        query.append(" real not null, ");
        query.append(DatabaseUtils.TABLE_MEAL_CARBOHYDRATES);
        query.append(" real not null, ");
        query.append(DatabaseUtils.TABLE_MEAL_PROTEINS);
        query.append(" real not null)");

        db.execSQL(query.toString());
    }
}
