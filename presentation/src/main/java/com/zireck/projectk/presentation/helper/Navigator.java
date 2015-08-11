package com.zireck.projectk.presentation.helper;

import android.content.Context;
import android.content.Intent;

import com.zireck.projectk.presentation.activity.AddFoodActivity;
import com.zireck.projectk.presentation.activity.AddMealActivity;
import com.zireck.projectk.presentation.activity.EditFoodActivity;
import com.zireck.projectk.presentation.activity.FoodDetailActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class created to handle all the navigation between activities. This class knows how to open
 * every activity in the application and provides to the client code different methods to start
 * activities with the information needed.
 *
 * Created by Zireck on 29/07/2015.
 */
@Singleton
public class Navigator {

    public static final int ADD_FOOD_REQUEST = 1;
    public static final int EDIT_FOOD_REQUEST = 2;
    public static final int DELETE_FOOD_REQUEST = 999;

    public static final int ADD_MEAL_REQUEST = 4;

    @Inject
    public Navigator() {

    }

    /**
     * Open AddFoodActivity
     */
    public void openAddFoodActivity(Context context) {
        Intent intent = AddFoodActivity.getLaunchIntent(context);
        startActivityForResult(context, intent, ADD_FOOD_REQUEST);
    }

    /**
     * Open FoodDetailActivity using a foodId
     * @param foodId
     */
    public void openFoodDetailActivity(Context context, final long foodId) {
        Intent intent = FoodDetailActivity.getLaunchIntent(context, foodId);
        startActivityForResult(context, intent, DELETE_FOOD_REQUEST);
    }

    /**
     * Open EditFoodActivity
     * @param foodId
     */
    public void openEditFoodActivity(Context context, final long foodId) {
        Intent intent = EditFoodActivity.getLaunchIntent(context, foodId);
        startActivityForResult(context, intent, EDIT_FOOD_REQUEST);
    }

    /**
     * Open AddMealActivity
     */
    public void openAddMealActivity(Context context) {
        Intent intent = AddMealActivity.getLaunchIntent(context);
        startActivityForResult(context, intent, ADD_MEAL_REQUEST);
    }

    private void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    private void startActivityForResult(Context context, Intent intent, final int request) {
        if (context != null) {
            // TODO: fix this
            //context.startActivityForResult(intent, request);
        } else {
            throw new NullPointerException("Activity can't be null");
        }
    }
}
