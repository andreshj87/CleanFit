package com.zireck.projectk.presentation.activity;

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
import com.zireck.projectk.presentation.fragment.FoodDetailFragment;
import com.zireck.projectk.presentation.listener.FoodDetailCallback;
import com.zireck.projectk.presentation.util.PictureUtils;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.Bind;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailActivity extends BaseActivity implements FoodDetailCallback {

    private static final String EXTRA_FOOD_ID = "food_id";

    private long mFoodId;

    @Bind(R.id.food_image) ImageView mFoodImage;
    @Bind(R.id.fab) FloatingActionButton mFloatingActionButton;

    /**
     * Generates the intent needed to launch this activity.
     */
    public static Intent getLaunchIntent(final Context context, final long foodId) {
        if (foodId < 0) {
            FoodDetailActivity.throwIllegalArgumentException();
        }

        Intent intent = new Intent(context, FoodDetailActivity.class);
        intent.putExtra(FoodDetailActivity.EXTRA_FOOD_ID, foodId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mapExtras();
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

        mFoodId = extras.getLong(FoodDetailActivity.EXTRA_FOOD_ID);
        if (mFoodId < 0) {
            throwIllegalArgumentException();
        }
    }

    private void initializeFragment() {
        if (mFoodId < 0) {
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, FoodDetailFragment.newInstance(mFoodId)).commit();
    }

    private static void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "FoodDetailActivity has to be launched using a valid Food identifier as extra");
    }

    @Override
    public void setFoodPicture(String foodPicture) {
        Picasso.with(this).load(PictureUtils.getPhotoFileUri(foodPicture)).fit().centerCrop().into(mFoodImage);
    }
}
