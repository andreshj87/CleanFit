package com.zireck.projectk.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.zireck.projectk.R;
import com.zireck.projectk.fragment.FoodRepositoryDrinkListFragment;
import com.zireck.projectk.fragment.FoodRepositoryFoodListFragment;
import com.zireck.projectk.fragment.FoodRepositoryFragment;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    private static final String NAVIGATION_VIEW_SELECTED_ITEM = "NavigationViewSelectedItem";

    @Bind(R.id.navigation_view) NavigationView mNavigationView;
    @Bind(R.id.appBarLayout) AppBarLayout mAppBarLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.content)
    CoordinatorLayout mContentLayout;
    private MaterialMenuIconToolbar mMaterialMenuIconToolbar;
    private boolean mDrawerDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMaterialMenuIconToolbar();

        initActionBar();

        initNavigationView();

        initDrawerLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        mMaterialMenuIconToolbar.syncState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        mMaterialMenuIconToolbar.onSaveInstanceState(outState);
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
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.drawer_food:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FoodRepositoryFoodListFragment.newInstance()).commit();
                        break;
                    case R.id.drawer_drink:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FoodRepositoryDrinkListFragment.newInstance()).commit();
                        break;
                    case R.id.drawer_repository:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FoodRepositoryFragment.newInstance()).commit();
                        break;
                }

                showAppBarLayout();

                //Snackbar.make(mContentLayout, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                mToolbar.setTitle(menuItem.getTitle());
                menuItem.setChecked(true);

                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initMaterialMenuIconToolbar() {
        mMaterialMenuIconToolbar = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN) {
            @Override
            public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };
    }

    private void initDrawerLayout() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mMaterialMenuIconToolbar.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        mDrawerDirection ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(android.view.View drawerView) {
                mDrawerDirection = true;
            }

            @Override
            public void onDrawerClosed(android.view.View drawerView) {
                mDrawerDirection = false;
            }
        });
    }

    private void showAppBarLayout() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();

        if (behavior != null) {
            int[] consumed = new int[2];
            behavior.onNestedFling(mContentLayout, mAppBarLayout, null, 0, -1000, true);
        }
    }

    private int getNavigationViewSelectedItem() {
        Menu menu = mNavigationView.getMenu();
        int count = menu.size();

        for (int i=0; i<count; i++) {
            if (menu.getItem(i).isChecked()) {
                Log.d(getClass().getSimpleName(), "get = " + i);
                return i;
            }
        }

        return 0;
    }

    private void setNavigationViewSelectedItem(int position) {
        Menu menu = mNavigationView.getMenu();
        menu.getItem(position).setChecked(true);
        Log.d(getClass().getSimpleName(), "set = " + position);
    }
}
