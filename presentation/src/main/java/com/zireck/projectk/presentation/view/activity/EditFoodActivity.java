package com.zireck.projectk.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.HasComponent;
import com.zireck.projectk.presentation.dagger.component.DaggerFoodComponent;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.dagger.module.FoodModule;
import com.zireck.projectk.presentation.listener.OnEditFoodFinishedListener;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.view.fragment.EditFoodFragment;

import butterknife.Bind;

/**
 * Created by Zireck on 31/07/2015.
 */
public class EditFoodActivity extends BaseActivity implements OnEditFoodFinishedListener,
                                                                HasComponent<FoodComponent> {

    private static final String EXTRA_FOOD_OBJECT = "food_oject";

    private FoodComponent mFoodComponent;

    private FoodModel mFood;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        mapExtras();
        initInjector();
        initActionBar();
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

        mFood = extras.getParcelable(EditFoodActivity.EXTRA_FOOD_OBJECT);
        if (mFood == null) {
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
        if (mFood == null) {
            throw new IllegalArgumentException("Cannot initialize fragment using a null object.");
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, EditFoodFragment.newInstance(mFood)).commit();
    }

    private static void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "EditFoodActivity has to be launched using a valid Food identifier as extra");
    }

    @Override
    public FoodComponent getComponent() {
        return mFoodComponent;
    }

    private void navigateBack(int result) {
        Intent intent = new Intent();
        setResult(result, intent);
        finish();
    }

    private void initInjector() {
        mFoodComponent = DaggerFoodComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .foodModule(new FoodModule(mFoodModelDataMapper.transformInverse(mFood)))
                .build();
    }

    private void initActionBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);

        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
