package com.zireck.projectk.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zireck.projectk.activity.AddFoodActivity;
import com.zireck.projectk.activity.EditFoodActivity;
import com.zireck.projectk.activity.FoodDetailActivity;

/**
 * Class created to handle all the navigation between activities. This class knows how to open
 * every activity in the application and provides to the client code different methods to start
 * activities with the information needed.
 *
 * Created by Zireck on 29/07/2015.
 */
public class Navigator {

    private final Context mActivityContext;

    public static final int ADD_FOOD_REQUEST = 1;
    public static final int EDIT_FOOD_REQUEST = 2;
    public static final int DELETE_FOOD_REQUEST = 999;

    public Navigator(Context activityContext) {
        mActivityContext = activityContext;
    }

    /**
     * Open AddFoodActivity
     */
    public void openAddFoodActivity() {
        Intent intent = AddFoodActivity.getLaunchIntent(mActivityContext);
        startActivityForResult(intent, ADD_FOOD_REQUEST);
    }

    /**
     * Open FoodDetailActivity using a foodId
     * @param foodId
     */
    public void openFoodDetailActivity(final long foodId) {
        Intent intent = FoodDetailActivity.getLaunchIntent(mActivityContext, foodId);
        startActivityForResult(intent, DELETE_FOOD_REQUEST);
    }

    /**
     * Open EditFoodActivity
     * @param foodId
     */
    public void openEditFoodActivity(final long foodId) {
        Intent intent = EditFoodActivity.getLaunchIntent(mActivityContext, foodId);
        startActivityForResult(intent, EDIT_FOOD_REQUEST);
    }

    private void startActivity(Intent intent) {
        mActivityContext.startActivity(intent);
    }

    private void startActivityForResult(Intent intent, final int request) {
        if (mActivityContext instanceof Activity) {
            ((Activity) mActivityContext).startActivityForResult(intent, request);
        }
    }
}
