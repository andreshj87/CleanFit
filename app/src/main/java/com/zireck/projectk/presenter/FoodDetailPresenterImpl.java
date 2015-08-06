package com.zireck.projectk.presenter;

import android.os.Bundle;

import com.zireck.projectk.interactor.FoodDetailInteractor;
import com.zireck.projectk.listener.OnFoodDetailInteractorFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.util.MathUtils;
import com.zireck.projectk.view.FoodDetailView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailPresenterImpl implements FoodDetailPresenter, OnFoodDetailInteractorFinishedListener {

    private long mFoodId;
    private Food mFood;

    private FoodDetailView mView;
    @Inject FoodDetailInteractor mInteractor;

    @Inject
    public FoodDetailPresenterImpl(FoodDetailView view, FoodDetailInteractor interactor) {
        mView = view;
        //mInteractor = new FoodDetailInteractorImpl();
        mInteractor = interactor;
    }

    @Override
    public void mapExtras(Bundle bundle) {
        if (bundle == null) {
            throwIllegalArgumentException();
        }

        mFoodId = bundle.getLong(mView.getFoodIdTag());
        if (!isValidFoodId(mFoodId)) {
            throwIllegalArgumentException();
        }
    }

    @Override
    public void getFood() {
        if (isValidFoodId(mFoodId)) {
            mInteractor.getFood(this, mFoodId);
        }
    }

    @Override
    public void deleteFood() {
        mInteractor.deleteFood(this, mFood);
    }

    @Override
    public long getFoodId() {
        return mFoodId;
    }

    private boolean isValidFoodId(long foodId) {
        return foodId >= 0;
    }

    private void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "FoodDetailFragment has to be launched using a valid Food identifier as extra");
    }

    @Override
    public void onGetFoodFinished(Food food) {
        if (food == null) {
            throw new NullPointerException("Food is null");
        }

        loadFood(food);
    }

    @Override
    public void onDeleteFoodFinished() {
        mView.foodDeleted();
    }

    private void loadFood(Food food) {
        mFood = food;

        mView.setFoodPicture(food.getPicture());

        mView.setFoodName(food.getName());
        mView.setFoodBrand(food.getBrand());

        if (!food.getIsDrink()) {
            mView.setNutrientsDescription("Energy & Nutrients (per 100gr)");
        } else {
            mView.setNutrientsDescription("Energy & Nutrients (per 100ml)");
        }

        mView.setFoodCalories(MathUtils.formatDouble(food.getCalories()));

        mView.setFatsAmount(MathUtils.formatDouble(food.getFats()) + "g");
        mView.setCarbohydratesAmount(MathUtils.formatDouble(food.getCarbohydrates()) + "g");
        mView.setProteinsAmount(MathUtils.formatDouble(food.getProteins()) + "g");

        mView.setFatsPercent(MathUtils.betterFormatDouble(food.getFatsPercent()) + "%");
        mView.setCarbohydratesPercent(MathUtils.betterFormatDouble(food.getCarbohydratesPercent()) + "%");
        mView.setProteinsPercent(MathUtils.betterFormatDouble(food.getProteinsPercent()) + "%");

        setChartData(food);
    }

    private void setChartData(Food food) {
        List<SliceValue> values = new ArrayList<SliceValue>();
        values.add(new SliceValue((float) food.getFatsPercent(), mView.getFatsColor()));
        values.add(new SliceValue((float) food.getCarbohydratesPercent(), mView.getCarbohydrtesColor()));
        values.add(new SliceValue((float) food.getProteinsPercent(), mView.getProteinsColor()));
        PieChartData pieChartData = new PieChartData(values);
        pieChartData.setHasCenterCircle(true);
        pieChartData.setHasLabels(false);
        mView.setPieChartData(pieChartData);
    }

}
