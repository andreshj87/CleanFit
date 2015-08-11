package com.zireck.projectk.presentation.interactor;

import com.zireck.projectk.presentation.listener.OnFoodDetailInteractorFinishedListener;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.util.PictureUtils;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailInteractorImpl implements FoodDetailInteractor {
    @Override
    public void getFood(OnFoodDetailInteractorFinishedListener listener, long foodId) {
        listener.onGetFoodFinished(retrieveFood(foodId));
    }

    @Override
    public void deleteFood(OnFoodDetailInteractorFinishedListener listener, FoodModel food) {
        deleteFoodPicture(food.getPicture());

        //GreenDaoUtils.getFoodDao().delete(food);
        listener.onDeleteFoodFinished();
    }

    private FoodModel retrieveFood(long foodId) {
        /*
        FoodDao foodDao = GreenDaoUtils.getFoodDao();
        Food food = foodDao.queryBuilder().where(FoodDao.Properties.Id.eq(foodId)).unique();
*/
        return new FoodModel(44);
    }

    private void deleteFoodPicture(String foodPicture) {
        PictureUtils.deletePicture(foodPicture);
    }
}
