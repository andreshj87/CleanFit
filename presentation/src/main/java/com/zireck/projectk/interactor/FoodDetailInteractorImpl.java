package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodDetailInteractorFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.util.GreenDaoUtils;
import com.zireck.projectk.util.PictureUtils;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailInteractorImpl implements FoodDetailInteractor {
    @Override
    public void getFood(OnFoodDetailInteractorFinishedListener listener, long foodId) {
        listener.onGetFoodFinished(retrieveFood(foodId));
    }

    @Override
    public void deleteFood(OnFoodDetailInteractorFinishedListener listener, Food food) {
        deleteFoodPicture(food.getPicture());

        GreenDaoUtils.getFoodDao().delete(food);
        listener.onDeleteFoodFinished();
    }

    private Food retrieveFood(long foodId) {
        FoodDao foodDao = GreenDaoUtils.getFoodDao();
        Food food = foodDao.queryBuilder().where(FoodDao.Properties.Id.eq(foodId)).unique();

        return food;
    }

    private void deleteFoodPicture(String foodPicture) {
        PictureUtils.deletePicture(foodPicture);
    }
}
