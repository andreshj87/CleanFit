package com.zireck.projectk.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.HasComponent;
import com.zireck.projectk.presentation.dagger.component.DaggerFoodComponent;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.dagger.module.FoodModule;
import com.zireck.projectk.presentation.listener.FoodDetailCallback;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.util.PictureUtils;
import com.zireck.projectk.presentation.view.fragment.FoodDetailFragment;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.Bind;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailActivity extends BaseActivity implements FoodDetailCallback,
                                                                HasComponent<FoodComponent> {

    private static final String EXTRA_FOOD_OBJECT = "food_object";

    private FoodComponent mFoodComponent;

    private FoodModel mFood;

    @Bind(R.id.food_image) ImageView mFoodImage;
    @Bind(R.id.fab) FloatingActionButton mFloatingActionButton;

    /**
     * Generates the intent needed to launch this activity.
     */
    public static Intent getLaunchIntent(final Context context, final FoodModel food) {
        if (food == null) {
            FoodDetailActivity.throwIllegalArgumentException();
        }

        Intent intent = new Intent(context, FoodDetailActivity.class);
        intent.putExtra(FoodDetailActivity.EXTRA_FOOD_OBJECT, food);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mapExtras();
        initInjector();
        initializeFragment();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MaterialIconView icon = new MaterialIconView(this);
        icon.setIcon(MaterialDrawableBuilder.IconValue.FOOD);
        icon.setColorResource(android.R.color.white);
        mFloatingActionButton.setImageDrawable(icon.getDrawable());

        //mCollapsingToolbar.setTitle("Food Detail");
        //mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * As FoodId is mandatory if there is not an extra inside the bundle that launches this activity
     * we are going to throw a new IllegalArgumentException.
     */
    private void mapExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throwIllegalArgumentException();
        }

        mFood = extras.getParcelable(FoodDetailActivity.EXTRA_FOOD_OBJECT);
        if (mFood == null) {
            throwIllegalArgumentException();
        }
    }

    private void initializeFragment() {
        if (mFood == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, FoodDetailFragment.newInstance(mFood)).commit();
    }

    private static void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "FoodDetailActivity has to be launched using a valid Food object as extra");
    }

    @Override
    public void setFoodPicture(String foodPicture) {
        Picasso.with(this).load(PictureUtils.getPhotoFileUri(foodPicture)).fit().centerCrop().into(mFoodImage);
    }

    private void initInjector() {
        mFoodComponent = DaggerFoodComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .foodModule(new FoodModule(mFoodModelDataMapper.transformInverse(mFood)))
                .build();
    }

    @Override
    public FoodComponent getComponent() {
        return mFoodComponent;
    }
}
