package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodDetailFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.model.GreenDaoHelper;
import com.zireck.projectk.util.PictureUtils;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailInteractorImpl implements FoodDetailInteractor {
    @Override
    public void getFood(OnFoodDetailFinishedListener listener, long foodId) {
        listener.onGetFoodFinished(retrieveFood(foodId));
    }

    @Override
    public void deleteFood(OnFoodDetailFinishedListener listener, Food food) {
        deleteFoodPicture(food.getPicture());

        FoodDao foodDao = retrieveFoodDao();
        foodDao.delete(food);
        listener.onDeleteFoodFinished();
    }

    private Food retrieveFood(long foodId) {
        FoodDao foodDao = retrieveFoodDao();
        Food food = foodDao.queryBuilder().where(FoodDao.Properties.Id.eq(foodId)).unique();

        return food;
    }

    private FoodDao retrieveFoodDao() {
        GreenDaoHelper daoHelper = new GreenDaoHelper();
        return daoHelper.getFoodDao();
    }

    private void deleteFoodPicture(String foodPicture) {
        PictureUtils.deletePicture(foodPicture);
    }
}
