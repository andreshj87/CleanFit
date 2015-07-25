package com.zireck.projectk.presenter;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;

import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.model.GreenDaoHelper;
import com.zireck.projectk.util.MathUtils;
import com.zireck.projectk.view.AddFoodView;

import org.w3c.dom.Text;

/**
 * Created by Zireck on 24/07/2015.
 */
public class AddFoodPresenterImpl implements AddFoodPresenter {

    private AddFoodView mView;

    public AddFoodPresenterImpl(AddFoodView view) {
        mView = view;
    }

    @Override
    public void validateData(String name, String brand, boolean isDrink, String calories, String fats, String carboydrates, String proteins) {
        boolean error = false;

        if (TextUtils.isEmpty(name)) {
            mView.setNameError();
            error = true;
        }

        if (TextUtils.isEmpty(brand)) {
            mView.setBrandError();
            error = true;
        }

        if (TextUtils.isEmpty(calories) || !MathUtils.isDouble(calories)) {
            mView.setCaloriesError();
            error = true;
        }

        if (TextUtils.isEmpty(fats) || !MathUtils.isDouble(fats)) {
            mView.setFatsError();
            error = true;
        }

        if (TextUtils.isEmpty(carboydrates) || !MathUtils.isDouble(carboydrates)) {
            mView.setCarbohydratesError();
            error = true;
        }

        if (TextUtils.isEmpty(proteins) || !MathUtils.isDouble(proteins)) {
            mView.setProteinsError();
            error = true;
        }

        if (!error) {
            Food food = new Food();
            food.setName(name);
            food.setBrand(brand);
            food.setIsDrink(isDrink);
            food.setCalories(Double.valueOf(calories));
            food.setFats(Double.valueOf(fats));
            food.setCarbohydrates(Double.valueOf(carboydrates));
            food.setProteins(Double.valueOf(proteins));

            GreenDaoHelper greenDaoHelper = new GreenDaoHelper();
            FoodDao foodDao = greenDaoHelper.getFoodDao();
            foodDao.insert(food);

            mView.foodSuccessfullyAdded();
        }
    }

    @Override
    public void isDrink(boolean isDrink) {
        if (isDrink) {
            mView.setMl();
        } else {
            mView.setGr();
        }
    }
}
