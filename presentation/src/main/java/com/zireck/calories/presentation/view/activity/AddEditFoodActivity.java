package com.zireck.calories.presentation.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.R;
import com.zireck.calories.presentation.dagger.HasComponent;

import butterknife.Bind;

/**
 * Created by Zireck on 18/08/2015.
 */
public abstract class AddEditFoodActivity extends BaseActivity
        implements HasComponent<FoodComponent> {

    protected FoodComponent mFoodComponent;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_food);

        colorizeStatusBar();
        initInjector();
        initActionBar();
        initializeFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateBack(RESULT_CANCELED);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public FoodComponent getComponent() {
        return mFoodComponent;
    }

    private void colorizeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }
    }

    public abstract void initInjector();

    public abstract void initializeFragment();

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

    protected void navigateBack(int result) {
        Intent intent = new Intent();
        setResult(result, intent);
        finish();
    }
}
