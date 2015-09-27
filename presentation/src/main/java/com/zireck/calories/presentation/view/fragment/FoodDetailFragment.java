package com.zireck.calories.presentation.view.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;
import com.zireck.calories.presentation.listener.FoodDetailCallback;
import com.zireck.calories.presentation.navigation.Navigator;
import com.zireck.calories.presentation.presenter.FoodDetailPresenter;
import com.zireck.calories.R;
import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.view.FoodDetailView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import javax.inject.Inject;

import butterknife.Bind;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailFragment extends BaseFragment implements FoodDetailView {

    private static final String EXTRA_FOOD_OBJECT = "food_object";

    @Inject
    Navigator mNavigator;
    @Inject
    FoodDetailPresenter mPresenter;
    private FoodDetailCallback mCallback;

    @Bind(R.id.food_name) TextView mFoodName;
    @Bind(R.id.food_brand) TextView mFoodBrand;
    @Bind(R.id.nutrients_description) TextView mNutrientsDescription;
    @Bind(R.id.food_calories) TextView mFoodCalories;
    @Bind(R.id.fats_amount) TextView mFatsAmount;
    @Bind(R.id.fats_percent) TextView mFatsPercent;
    @Bind(R.id.carbohydrates_amount) TextView mCarbohydratesAmount;
    @Bind(R.id.carbohydrates_percent) TextView mCarbohydratesPercent;
    @Bind(R.id.proteins_amount) TextView mProteinsAmount;
    @Bind(R.id.proteins_percent) TextView mProteinsPercent;
    @Bind(R.id.pie_chart) PieChartView mPieChart;

    public static FoodDetailFragment newInstance(FoodModel food) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_FOOD_OBJECT, food);

        FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
        foodDetailFragment.setArguments(bundle);

        return foodDetailFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (FoodDetailCallback) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFoodBrandIcon();

        mFoodName.setTypeface(EasyFonts.robotoLight(getActivity()));
        mFoodCalories.setTypeface(EasyFonts.robotoLight(getActivity()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_food_detail;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_food_detail, menu);
        setOptionsMenuIcons(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                mNavigator.openEditFoodActivity(getActivity(), mPresenter.getFood());
                break;
            case R.id.action_delete:
                showDeleteDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setName(String foodName) {
        mFoodName.setText(foodName);
    }

    @Override
    public void setBrand(String foodBrand) {
        mFoodBrand.setText(foodBrand);
    }

    @Override
    public void setCalories(String foodCalories) {
        mFoodCalories.setText(foodCalories);
    }

    @Override
    public void setFatsAmount(String fatsAmount) {
        mFatsAmount.setText(fatsAmount);
    }

    @Override
    public void setFatsPercent(String fatsPercent) {
        mFatsPercent.setText(fatsPercent);
    }

    @Override
    public void setCarbohydratesAmount(String carbohydratesAmount) {
        mCarbohydratesAmount.setText(carbohydratesAmount);
    }

    @Override
    public void setCarbohydratesPercent(String carbohydratesPercent) {
        mCarbohydratesPercent.setText(carbohydratesPercent);
    }

    @Override
    public void setProteinsAmount(String proteinsAmount) {
        mProteinsAmount.setText(proteinsAmount);
    }

    @Override
    public void setProteinsPercent(String proteinsPercent) {
        mProteinsPercent.setText(proteinsPercent);
    }

    @Override
    public void setNutrientsDescription(String description) {
        mNutrientsDescription.setText(description);
    }

    @Override
    public void setFoodPicture(String foodImage) {
        mCallback.setFoodPicture(foodImage);
    }

    @Override
    public void setPieChartData(PieChartData pieChartData) {
        mPieChart.setPieChartData(pieChartData);
    }

    @Override
    public int getFatsColor() {
        return getResources().getColor(R.color.fats);
    }

    @Override
    public int getCarbohydrtesColor() {
        return getResources().getColor(R.color.carbohydrates);
    }

    @Override
    public int getProteinsColor() {
        return getResources().getColor(R.color.proteins);
    }

    @Override
    public void foodDeleted() {
        Intent intent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    private void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mPresenter.setView(this);
        mPresenter.mapExtras(getArguments(), FoodDetailFragment.EXTRA_FOOD_OBJECT);
    }

    private void setFoodBrandIcon() {
        MaterialIconView icon = new MaterialIconView(getActivity());
        icon.setIcon(MaterialDrawableBuilder.IconValue.TAG_TEXT_OUTLINE);
        icon.setColorResource(R.color.icon_deactivated);
        mFoodBrand.setCompoundDrawables(icon.getDrawable(), null, null, null);
        mFoodBrand.setCompoundDrawablePadding(16);
    }

    private void setOptionsMenuIcons(Menu menu) {
        MenuItem itemEdit = menu.findItem(R.id.action_edit);
        MenuItem itemDelete = menu.findItem(R.id.action_delete);

        MaterialIconView iconEdit = new MaterialIconView(getActivity());
        iconEdit.setIcon(MaterialDrawableBuilder.IconValue.PENCIL);
        iconEdit.setColorResource(R.color.white);

        MaterialIconView iconDelete = new MaterialIconView(getActivity());
        iconDelete.setIcon(MaterialDrawableBuilder.IconValue.DELETE);
        iconDelete.setColorResource(R.color.white);

        itemEdit.setIcon(iconEdit.getDrawable());
        itemDelete.setIcon(iconDelete.getDrawable());
    }

    private void showDeleteDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Delete selected item?");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteFood();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.show();
    }
}
