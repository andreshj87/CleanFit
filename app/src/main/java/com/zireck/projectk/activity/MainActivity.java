package com.zireck.projectk.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.zireck.projectk.R;
import com.zireck.projectk.fragment.FoodListFragment;
import com.zireck.projectk.fragment.FoodRepositoryFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String NAVIGATION_VIEW_SELECTED_ITEM = "NavigationViewSelectedItem";

    @Bind(R.id.navigation_view) NavigationView mNavigationView;
    @Bind(R.id.appBarLayout) AppBarLayout mAppBarLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.content) CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.fab) FloatingActionButton mFloatingActionButton;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBar();

        initNavigationView();

        initDrawerToggle();

        selectNavigationViewFirstItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d(getClass().getSimpleName(), "save");
        // TODO not working
        outState.putInt(NAVIGATION_VIEW_SELECTED_ITEM, getNavigationViewSelectedItem());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(getClass().getSimpleName(), "restore");
        if (savedInstanceState.containsKey(NAVIGATION_VIEW_SELECTED_ITEM)) {
            setNavigationViewSelectedItem(savedInstanceState.getInt(NAVIGATION_VIEW_SELECTED_ITEM, 0));
        }
    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectNavigationViewItem(menuItem);
                return true;
            }
        });
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectNavigationViewFirstItem() {
        mNavigationView.getMenu().performIdentifierAction(R.id.drawer_home, 0);
    }

    private void selectNavigationViewItem(MenuItem menuItem) {
        Fragment fragment = null;

        showAppBarLayout();
        hideFAB();

        switch (menuItem.getItemId()) {
            case R.id.drawer_home:
                fragment = FoodRepositoryFragment.newInstance();
                showFAB();
                break;
            case R.id.drawer_food:
                fragment = FoodListFragment.newInstance(FoodListFragment.TAG_FOOD);
                break;
            case R.id.drawer_drink:
                fragment = FoodListFragment.newInstance(FoodListFragment.TAG_DRINK);
                break;
            case R.id.drawer_repository:
                fragment = FoodRepositoryFragment.newInstance();
                showFAB();
                break;
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        // Highlight the selected item, update the title and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }

    private void showAppBarLayout() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();

        if (behavior != null) {
            behavior.onNestedFling(mCoordinatorLayout, mAppBarLayout, null, 0, -1000, true);
        }
    }

    private int getNavigationViewSelectedItem() {
        Menu menu = mNavigationView.getMenu();
        int count = menu.size();

        for (int i=0; i<count; i++) {
            if (menu.getItem(i).isChecked()) {
                return i;
            }
        }

        return 0;
    }

    private void setNavigationViewSelectedItem(int position) {
        Menu menu = mNavigationView.getMenu();
        menu.getItem(position).setChecked(true);
    }

    private void showFAB() {
        mFloatingActionButton.show();
    }

    private void hideFAB() {
        mFloatingActionButton.hide();
    }

    @OnClick(R.id.fab)
    public void onClickFAB() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            if (fragment instanceof FoodRepositoryFragment) {
                Intent intent = new Intent(this, AddFoodActivity.class);
                startActivity(intent);
            } else {
                Snackbar.make(mCoordinatorLayout, "Nothing!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

}
