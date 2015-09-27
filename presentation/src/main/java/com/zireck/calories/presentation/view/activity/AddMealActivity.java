package com.zireck.calories.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zireck.calories.presentation.dagger.module.FoodModule;
import com.zireck.calories.R;
import com.zireck.calories.presentation.dagger.HasComponent;
import com.zireck.calories.presentation.dagger.component.DaggerFoodComponent;
import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.presentation.listener.OnAddMealFinishedListener;

import butterknife.Bind;

/**
 * Created by Zireck on 06/08/2015.
 */
public class AddMealActivity extends BaseActivity implements HasComponent<FoodComponent>, OnAddMealFinishedListener {

    private FoodComponent mFoodComponent;

    @Bind(R.id.toolbar) Toolbar mToolbar;

    /**
     * Generates the intent needed to launch this activity.
     */
    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, AddMealActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        initInjector();
        initActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_add_edit, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO goBack
                navigateBack(RESULT_CANCELED);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public FoodComponent getComponent() {
        return mFoodComponent;
    }

    private void initInjector() {
        mFoodComponent = DaggerFoodComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .foodModule(new FoodModule())
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

    private void navigateBack(int result) {
        Intent intent = new Intent();
        setResult(result, intent);
        finish();
    }

    @Override
    public void mealAdded() {
        navigateBack(RESULT_OK);
    }
}
