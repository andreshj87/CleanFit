package com.zireck.projectk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.zireck.projectk.R;
import com.zireck.projectk.fragment.EditFoodFragment;
import com.zireck.projectk.listener.OnEditFoodFinishedListener;

/**
 * Created by Zireck on 31/07/2015.
 */
public class EditFoodActivity extends AddFoodActivity implements OnEditFoodFinishedListener {

    private static final String EXTRA_FOOD_ID = "food_id";

    private long mFoodId;

    /**
     * Generates the intent needed to launch this activity.
     */
    public static Intent getLaunchIntent(final Context context, final long foodId) {
        if (foodId < 0) {
            EditFoodActivity.throwIllegalArgumentException();
        }

        Intent intent = new Intent(context, EditFoodActivity.class);
        intent.putExtra(EditFoodActivity.EXTRA_FOOD_ID, foodId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        initActionBar();
        mapExtras();
        initializeFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                foodNotEdited();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

        mFoodId = extras.getLong(EditFoodActivity.EXTRA_FOOD_ID);
        if (mFoodId < 0) {
            throwIllegalArgumentException();
        }
    }

    @Override
    public void foodEdited() {
        navigateBack(RESULT_OK);
    }

    @Override
    public void foodNotEdited() {
        navigateBack(RESULT_CANCELED);
    }

    private void initializeFragment() {
        if (mFoodId < 0) {
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, EditFoodFragment.newInstance(mFoodId)).commit();
    }

    private static void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "EditFoodActivity has to be launched using a valid Food identifier as extra");
    }
}
