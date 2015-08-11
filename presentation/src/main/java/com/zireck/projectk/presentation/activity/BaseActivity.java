package com.zireck.projectk.presentation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zireck.projectk.presentation.AndroidApplication;
import com.zireck.projectk.presentation.dagger.component.ApplicationComponent;
import com.zireck.projectk.presentation.dagger.module.ActivityModule;
import com.zireck.projectk.presentation.helper.Navigator;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Zireck on 16/07/2015.
 */
public class BaseActivity extends AppCompatActivity {

    @Inject Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        //injectDependencies();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        injectViews();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        injectViews();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * Replace every field annotated with ButterKnife annotations like @InjectView with the proper
     * value.
     */
    private void injectViews() {
        ButterKnife.bind(this);
    }
}
