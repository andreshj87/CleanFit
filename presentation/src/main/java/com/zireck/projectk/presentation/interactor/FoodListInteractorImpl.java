package com.zireck.projectk.presentation.interactor;

import com.zireck.projectk.presentation.listener.OnFoodListFinishedListener;
import com.zireck.projectk.presentation.model.FoodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zireck on 22/07/2015.
 */
public class FoodListInteractorImpl implements FoodListInteractor {
    @Override
    public void retrieveFoodRepository(OnFoodListFinishedListener listener) {
        listener.onFinished(retrieveFood(false));
    }

    @Override
    public void retrieveDrinkRepository(OnFoodListFinishedListener listener) {
        listener.onFinished(retrieveFood(true));
    }

    private List<FoodModel> retrieveFood(boolean isDrink) {
        /*
        FoodDao foodDao = GreenDaoUtils.getFoodDao();
        List<Food> foodList = foodDao.queryBuilder().where(FoodDao.Properties.IsDrink.eq(isDrink)).list();
*/
        return new ArrayList<FoodModel>();
    }
}
