package com.zireck.projectk.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.HasComponent;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;

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
