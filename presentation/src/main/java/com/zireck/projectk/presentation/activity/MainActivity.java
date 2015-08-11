package com.zireck.projectk.presentation.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionMenu;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.fragment.FoodListFragment;
import com.zireck.projectk.presentation.fragment.FoodRepositoryFragment;
import com.zireck.projectk.presentation.fragment.HomeFragment;
import com.zireck.projectk.presentation.helper.Navigator;
import com.zireck.projectk.presentation.listener.OnFoodRepositoryTabChangeListener;
import com.zireck.projectk.data.util.SnackbarUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements OnFoodRepositoryTabChangeListener {

    private static final String NAVIGATION_VIEW_SELECTED_ITEM = "NavigationViewSelectedItem";

    @Inject
    Navigator mNavigator;

    @Bind(R.id.navigation_view) NavigationView mNavigationView;
    @Bind(R.id.appBarLayout) AppBarLayout mAppBarLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.content) CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.fragment_container) RelativeLayout mContainer;
    @Bind(R.id.fab) FloatingActionButton mFloatingActionButton;
    private ActionBarDrawerToggle mDrawerToggle;

    @Bind(R.id.fab_menu)
    FloatingActionMenu mFabMenu;
    @Bind(R.id.fab_food)
    com.github.clans.fab.FloatingActionButton mFabFood;
    @Bind(R.id.fab_meal)
    com.github.clans.fab.FloatingActionButton mFabMeal;
    @Bind(R.id.fab_barcode)
    com.github.clans.fab.FloatingActionButton mFabBarcode;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Navigator.ADD_FOOD_REQUEST) {
            if (resultCode == RESULT_OK) {
                SnackbarUtils.showShortMessageWithElevation(mCoordinatorLayout, "Food successfully added", 8);
            }
        } else if (requestCode == Navigator.DELETE_FOOD_REQUEST) {
            if (resultCode == RESULT_OK) {
                SnackbarUtils.showShortMessageWithElevation(mCoordinatorLayout, "Food successfully deleted", 8);
            }
        }
    }

    @Override
    public void tabChanged() {
        showFAB();
    }

    @OnClick(R.id.fab)
    public void onClickFAB() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            if (fragment instanceof FoodRepositoryFragment) {
                mNavigator.openAddFoodActivity();
            } else {
                SnackbarUtils.showShortMessage(mCoordinatorLayout, "Nothing!");
            }
        }
    }

    @OnClick(R.id.fab_food)
    public void onClickFabFood() {
        mNavigator.openAddFoodActivity();
    }

    @OnClick(R.id.fab_meal)
    public void onClickFabMeal() {
        //mNavigator.openAddMealActivity();
        Intent intent = new Intent(this, AddMealActivity.class);
        startActivity(intent);
    }

    private void showFAB() {
        mFloatingActionButton.show();
    }

    private void hideFAB() {
        mFloatingActionButton.hide();
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
                fragment = HomeFragment.newInstance();
                //showFAB();
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

}
