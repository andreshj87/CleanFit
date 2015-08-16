package com.zireck.projectk.presentation.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.view.FoodDetailView;
import com.zireck.projectk.presentation.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

/**
 * Created by Zireck on 16/08/2015.
 */
public class FoodDetailPresenter implements Presenter {

    private FoodDetailView mView;
    private Interactor mDeleteFoodInteractor;
    private FoodModel mFood;

    @Inject
    public FoodDetailPresenter(@Named("deleteFood") Interactor deleteFoodInteractor) {
        mDeleteFoodInteractor = deleteFoodInteractor;
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((FoodDetailView) view);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mDeleteFoodInteractor.unsubscribe();
    }

    public void mapExtras(Bundle bundle) {
        if (bundle == null) {
            throwIllegalArgumentException();
        }

        mFood = bundle.getParcelable(mView.getFoodExtraKey());
        if (mFood == null) {
            throwIllegalArgumentException();
        } else {
            showFoodDetailsInView(mFood);
        }
    }

    private void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "FoodDetailFragment has to be launched using a valid Food object as extra");
    }

    private void showFoodDetailsInView(FoodModel food) {
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

    public FoodModel getFood() {
        return mFood;
    }

    public void deleteFood() {
        mDeleteFoodInteractor.execute(new DeleteFoodSubscriber());
    }

    private final class DeleteFoodSubscriber extends DefaultSubscriber {
        @Override
        public void onCompleted() {
            mView.foodDeleted();
        }
    }
}
