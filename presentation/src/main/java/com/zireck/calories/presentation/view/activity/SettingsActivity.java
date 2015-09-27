package com.zireck.calories.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zireck.calories.presentation.dagger.component.DaggerFoodComponent;
import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.presentation.dagger.module.FoodModule;
import com.zireck.calories.presentation.view.fragment.SettingsFragment;
import com.zireck.calories.presentation.dagger.HasComponent;

import butterknife.Bind;

/**
 * Created by Zireck on 11/09/2015.
 */
public class SettingsActivity extends BaseActivity implements HasComponent<FoodComponent> {

    private FoodComponent mFoodComponent;

    @Bind(com.zireck.calories.R.id.toolbar) Toolbar mToolbar;

    /**
     * Generates the intent needed to launch this activity.
     */
    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.zireck.calories.R.layout.activity_settings);

        initInjector();
        initActionBar();
        initFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO call fragment, check if valid data, save and exit
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(com.zireck.calories.R.id.fragment_container, SettingsFragment.newInstance()).commit();
    }
}
