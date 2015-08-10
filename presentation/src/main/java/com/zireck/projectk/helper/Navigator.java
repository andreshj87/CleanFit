package com.zireck.projectk.helper;

import android.app.Activity;
import android.content.Intent;

import com.zireck.projectk.activity.AddFoodActivity;
import com.zireck.projectk.activity.AddMealActivity;
import com.zireck.projectk.activity.EditFoodActivity;
import com.zireck.projectk.activity.FoodDetailActivity;

import javax.inject.Inject;

/**
 * Class created to handle all the navigation between activities. This class knows how to open
 * every activity in the application and provides to the client code different methods to start
 * activities with the information needed.
 *
 * Created by Zireck on 29/07/2015.
 */
public class Navigator {

    @Inject Activity mActivity;

    public static final int ADD_FOOD_REQUEST = 1;
    public static final int EDIT_FOOD_REQUEST = 2;
    public static final int DELETE_FOOD_REQUEST = 999;

    public static final int ADD_MEAL_REQUEST = 4;

    @Inject
    public Navigator(Activity activity) {
        mActivity = activity;
    }

    /**
     * Open AddFoodActivity
     */
    public void openAddFoodActivity() {
        Intent intent = AddFoodActivity.getLaunchIntent(mActivity);
        startActivityForResult(intent, ADD_FOOD_REQUEST);
    }

    /**
     * Open FoodDetailActivity using a foodId
     * @param foodId
     */
    public void openFoodDetailActivity(final long foodId) {
        Intent intent = FoodDetailActivity.getLaunchIntent(mActivity, foodId);
        startActivityForResult(intent, DELETE_FOOD_REQUEST);
    }

    /**
     * Open EditFoodActivity
     * @param foodId
     */
    public void openEditFoodActivity(final long foodId) {
        Intent intent = EditFoodActivity.getLaunchIntent(mActivity, foodId);
        startActivityForResult(intent, EDIT_FOOD_REQUEST);
    }

    /**
     * Open AddMealActivity
     */
    public void openAddMealActivity() {
        Intent intent = AddMealActivity.getLaunchIntent(mActivity);
        startActivityForResult(intent, ADD_MEAL_REQUEST);
    }

    private void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    private void startActivityForResult(Intent intent, final int request) {
        if (mActivity != null) {
            mActivity.startActivityForResult(intent, request);
        } else {
            throw new NullPointerException("Activity can't be null");
        }
    }
}
