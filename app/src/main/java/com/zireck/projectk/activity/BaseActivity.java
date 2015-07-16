package com.zireck.projectk.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import butterknife.ButterKnife;

/**
 * Created by Zireck on 16/07/2015.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO injectDependencies()
        injectViews();
    }

    private void injectViews() {
        ButterKnife.bind(this);
    }
}
