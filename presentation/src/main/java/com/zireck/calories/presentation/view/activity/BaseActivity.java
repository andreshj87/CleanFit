package com.zireck.calories.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zireck.calories.presentation.AndroidApplication;
import com.zireck.calories.presentation.dagger.component.ApplicationComponent;
import com.zireck.calories.presentation.dagger.module.ActivityModule;
import com.zireck.calories.presentation.mapper.FoodModelDataMapper;
import com.zireck.calories.presentation.navigation.Navigator;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Zireck on 16/07/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject Navigator mNavigator;
    @Inject FoodModelDataMapper mFoodModelDataMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }

    /**
     * Required for some fragments to receive requestCode and resultCode.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        bindViews();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * Replace every field annotated with ButterKnife annotations like @Bind with the proper
     * value.
     */
    private void bindViews() {
        ButterKnife.bind(this);
    }
}
