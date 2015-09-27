package com.zireck.calories.presentation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zireck.calories.domain.interactor.AddFood;
import com.zireck.calories.domain.interactor.DefaultSubscriber;
import com.zireck.calories.presentation.mapper.FoodModelDataMapper;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.util.PictureUtils;
import com.zireck.calories.presentation.view.AddEditFoodView;
import com.zireck.calories.presentation.view.AddFoodView;
import com.zireck.calories.presentation.view.View;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 17/08/2015.
 */
public class AddFoodPresenter extends AddEditFoodPresenter {

    private AddFoodView mView;
    private AddFood mAddFoodInteractor;

    private boolean mGetPicture = false;

    @Inject
    public AddFoodPresenter(Context context, @Named("addFood") AddFood addFoodInteractor,
                            FoodModelDataMapper foodModelDataMapper) {
        super(context, foodModelDataMapper);

        mAddFoodInteractor = addFoodInteractor;
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((AddFoodView) view);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mAddFoodInteractor.unsubscribe();
    }

    @Override
    public AddEditFoodView getView() {
        return mView;
    }

    @Override
    public void receivePicture() {
        super.receivePicture();
        mGetPicture = true;
    }

    @Override
    public void deleteCurrentPicture() {
        super.deleteCurrentPicture();
        mGetPicture = false;
        PictureUtils.deletePicture(PictureUtils.TEMP_PICTURE_NAME);
    }

    @Override
    public void addOrEditFood(FoodModel foodModel) {
        addFood(foodModel);
    }

    public void addFood(FoodModel foodModel) {
        if (mGetPicture) {
            foodModel.setPicture(consolidateNewPicture());
        }

        mAddFoodInteractor.setFood(mFoodModelDataMapper.transformInverse(foodModel));
        mAddFoodInteractor.execute(new AddFoodSubscriber());
    }

    private final class AddFoodSubscriber extends DefaultSubscriber {
        @Override
        public void onError(Throwable e) {
            // TODO warn error properly
            System.out.println("AddFoodSubscriber onError");
        }

        @Override
        public void onCompleted() {
            mView.foodSuccessfullyAdded();
        }
    }
}
