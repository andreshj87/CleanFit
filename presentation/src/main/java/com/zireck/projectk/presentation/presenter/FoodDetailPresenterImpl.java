package com.zireck.projectk.presentation.presenter;

import android.os.Bundle;

import com.zireck.projectk.presentation.interactor.FoodDetailInteractor;
import com.zireck.projectk.presentation.interactor.FoodDetailInteractorImpl;
import com.zireck.projectk.presentation.listener.OnFoodDetailInteractorFinishedListener;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.view.FoodDetailView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailPresenterImpl implements FoodDetailPresenter, OnFoodDetailInteractorFinishedListener {

    private long mFoodId;
    private FoodModel mFood;

    private FoodDetailView mView;
    private FoodDetailInteractor mInteractor;

    public FoodDetailPresenterImpl(FoodDetailView view) {
        mView = view;
        //mInteractor = new FoodDetailInteractorImpl();
        mInteractor = new FoodDetailInteractorImpl();
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
    public void onGetFoodFinished(FoodModel food) {
        if (food == null) {
            throw new NullPointerException("Food is null");
        }

        loadFood(food);
    }

    @Override
    public void onDeleteFoodFinished() {
        mView.foodDeleted();
    }

    private void loadFood(FoodModel food) {
        mFood = food;

        mView.setFoodPicture(food.getPicture());

        mView.setFoodName(food.getName());
        mView.setFoodBrand(food.getBrand());

        if (!food.isDrink()) {
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

    private void setChartData(FoodModel food) {
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
