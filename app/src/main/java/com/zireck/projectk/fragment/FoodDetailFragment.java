package com.zireck.projectk.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.listener.FoodDetailCallback;
import com.zireck.projectk.presenter.FoodDetailPresenter;
import com.zireck.projectk.presenter.FoodDetailPresenterImpl;
import com.zireck.projectk.util.ToastUtils;
import com.zireck.projectk.view.FoodDetailView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.Bind;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailFragment extends BaseFragment implements FoodDetailView {
    private static final String EXTRA_FOOD_ID = "food_id";

    private FoodDetailCallback mCallback;
    private FoodDetailPresenter mPresenter;

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

    public static FoodDetailFragment newInstance(long foodId) {
        Bundle bundle = new Bundle();
        bundle.putLong(FoodDetailFragment.EXTRA_FOOD_ID, foodId);

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

        mPresenter = new FoodDetailPresenterImpl(this);
        mPresenter.mapExtras(getArguments());
        mPresenter.getFood();
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
                ToastUtils.showShortMessage(getActivity(), "edit");
                break;
            case R.id.action_delete:
                mPresenter.deleteFood();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getFoodIdTag() {
        return FoodDetailFragment.EXTRA_FOOD_ID;
    }

    @Override
    public void setFoodName(String foodName) {
        mFoodName.setText(foodName);
        mCallback.setFoodName(foodName);
    }

    @Override
    public void setFoodBrand(String foodBrand) {
        mFoodBrand.setText(foodBrand);
    }

    @Override
    public void setFoodCalories(String foodCalories) {
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

    @Override
    public void setNutrientsDescription(String description) {
        mNutrientsDescription.setText(description);
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
}
