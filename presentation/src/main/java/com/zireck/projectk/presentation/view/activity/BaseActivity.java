package com.zireck.projectk.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zireck.projectk.presentation.AndroidApplication;
import com.zireck.projectk.presentation.dagger.component.ApplicationComponent;
import com.zireck.projectk.presentation.dagger.module.ActivityModule;
import com.zireck.projectk.presentation.mapper.FoodModelDataMapper;
import com.zireck.projectk.presentation.navigation.Navigator;

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
