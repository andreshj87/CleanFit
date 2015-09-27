package com.zireck.calories.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.zireck.calories.presentation.dagger.component.DaggerFoodComponent;
import com.zireck.calories.presentation.dagger.module.FoodModule;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.view.fragment.EditFoodFragment;
import com.zireck.calories.presentation.listener.OnEditFoodFinishedListener;

/**
 * Created by Zireck on 31/07/2015.
 */
public class EditFoodActivity extends AddEditFoodActivity implements OnEditFoodFinishedListener {

    private static final String EXTRA_FOOD_OBJECT = "food_object";

    private FoodModel mFood;

    /**
     * Generates the intent needed to launch this activity.
     */
    public static Intent getLaunchIntent(final Context context, final FoodModel food) {
        if (food == null) {
            EditFoodActivity.throwIllegalArgumentException();
        }

        Intent intent = new Intent(context, EditFoodActivity.class);
        intent.putExtra(EditFoodActivity.EXTRA_FOOD_OBJECT, food);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mapExtras();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void foodEdited() {
        navigateBack(RESULT_OK);
    }

    @Override
    public void initInjector() {
        mFoodComponent = DaggerFoodComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .foodModule(new FoodModule(mFoodModelDataMapper.transformInverse(mFood)))
                .build();
    }

    @Override
    public void initializeFragment() {
        if (mFood == null) {
            throw new IllegalArgumentException("Cannot initialize fragment using a null object.");
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(com.zireck.calories.R.id.fragment_container, EditFoodFragment.newInstance(mFood))
                .commit();
    }

    /**
     * As FoodId is mandatory if there is not an extra inside the bundle that launches this activity
     * we are going to throw a new IllegalArgumentException.
     */
    private void mapExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throwIllegalArgumentException();
        }

        mFood = extras.getParcelable(EditFoodActivity.EXTRA_FOOD_OBJECT);
        if (mFood == null) {
            throwIllegalArgumentException();
        }
    }

    private static void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "EditFoodActivity has to be launched using a valid Food identifier as extra");
    }
}
