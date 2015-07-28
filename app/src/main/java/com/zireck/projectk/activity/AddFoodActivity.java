package com.zireck.projectk.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.zireck.projectk.R;
import com.zireck.projectk.fragment.AddFoodFragment;
import com.zireck.projectk.listener.OnAddFoodFinishedListener;
import com.zireck.projectk.listener.OnFoodPictureListener;

import java.io.File;

import butterknife.Bind;

/**
 * Created by Zireck on 24/07/2015.
 */
public class AddFoodActivity extends BaseActivity implements OnAddFoodFinishedListener {

    @Bind(R.id.root_layout) LinearLayout mRootLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        initActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_add_food, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                foodNotAdded();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void foodAdded() {
        navigateBack(RESULT_OK);
    }

    @Override
    public void foodNotAdded() {
        navigateBack(RESULT_CANCELED);
    }

    private void navigateBack(int result) {
        Intent intent = new Intent();
        setResult(result, intent);
        finish();
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

    /*
    private void sendPictureUriToFragment(Uri pictureUri) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_add_food);
        if (fragment != null && fragment instanceof AddFoodFragment) {
            AddFoodFragment addFoodFragment = (AddFoodFragment) fragment;
            addFoodFragment.receivePictureUri(mPictureFileName, pictureUri);
        }
    }*/
}
