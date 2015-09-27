package com.zireck.calories.presentation.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zireck.calories.presentation.mapper.FoodModelDataMapper;
import com.zireck.calories.presentation.util.MathUtils;
import com.zireck.calories.presentation.util.PictureUtils;
import com.zireck.calories.domain.interactor.DefaultSubscriber;
import com.zireck.calories.domain.interactor.EditFood;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.view.AddEditFoodView;
import com.zireck.calories.presentation.view.EditFoodView;
import com.zireck.calories.presentation.view.View;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 17/08/2015.
 */
public class EditFoodPresenter extends AddEditFoodPresenter {

    private EditFoodView mView;
    private EditFood mEditFoodInteractor;

    private FoodModel mFood;
    private boolean mGetNewPicture;

    @Inject
    public EditFoodPresenter(Context context, @Named("editFood") EditFood editFoodInteractor,
                             FoodModelDataMapper foodModelDataMapper) {
        super(context, foodModelDataMapper);

        mEditFoodInteractor = editFoodInteractor;
        mGetNewPicture = false;
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((EditFoodView) view);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mEditFoodInteractor.unsubscribe();
    }

    @Override
    public AddEditFoodView getView() {
        return mView;
    }

    public void mapExtras(Bundle bundle, String extraFoodObjectKey) {
        if (bundle == null) {
            throwIllegalArgumentException();
        }

        mFood = bundle.getParcelable(extraFoodObjectKey);
        if (mFood == null) {
            throwIllegalArgumentException();
        } else {
            loadPicture();
            showFoodModelInView(mFood);
        }
    }

    private void showFoodModelInView(FoodModel foodModel) {
        mView.setFoodName(foodModel.getName());
        mView.setFoodBrand(foodModel.getBrand());
        mView.setFoodCalories(MathUtils.formatDouble(foodModel.getCalories()));
        mView.setFoodFats(MathUtils.betterFormatDouble(foodModel.getFats()));
        mView.setFoodCarbohydrates(MathUtils.betterFormatDouble(foodModel.getCarbohydrates()));
        mView.setFoodProteins(MathUtils.betterFormatDouble(foodModel.getProteins()));

        mView.setIsDrink(foodModel.isDrink());
        if (foodModel.isDrink()) {
            mView.setMl();
        } else {
            mView.setGr();
        }
    }

    @Override
    public void receivePicture() {
        super.receivePicture();
        mGetNewPicture = true;
    }

    public void doNotReceivePicture() {
        if (mView.getPictureImageView().getDrawable() == null) {
            mView.hideDeletePictureLayout();
        } else {
            mView.showDeletePictureLayout();
        }
    }

    @Override
    public void deleteCurrentPicture() {
        super.deleteCurrentPicture();
        mGetNewPicture = true;
    }

    @Override
    public void addOrEditFood(FoodModel foodModel) {
        editFood(foodModel);
    }

    private FoodModel completeFoodModel(FoodModel foodModel) {
        foodModel.setId(mFood.getId());

        if (!mGetNewPicture) {
            foodModel.setPicture(mFood.getPicture());
        } else {
            PictureUtils.deletePicture(mFood.getPicture());

            if (mView.getPictureImageView().getDrawable() == null) {
                System.out.println("k9d3 getPictureImageView().getDrawable == NULL");
                foodModel.setPicture("");
            } else {
                System.out.println("k9d3 getPictureImageView().getDrawable != NULL -> " + mView.getPictureImageView().toString());
                foodModel.setPicture(consolidateNewPicture());
            }
        }

        return foodModel;
    }

    private void editFood(FoodModel foodModel) {
        FoodModel foodModelComplete = completeFoodModel(foodModel);

        mEditFoodInteractor.setFood(mFoodModelDataMapper.transformInverse(foodModelComplete));
        mEditFoodInteractor.execute(new EditFoodSubscriber());
    }

    public void loadPicture() {
        if (TextUtils.isEmpty(mFood.getPicture())) {
            mView.deletePicture();
            mView.hideDeletePictureLayout();
            return;
        }

        mView.showDeletePictureLayout();

        Uri pictureUri = PictureUtils.getPhotoFileUri(mFood.getPicture());
        Picasso.Builder picassoBuilder = new Picasso.Builder(mContext);
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                mView.hideDeletePictureLayout();
            }
        });
        Picasso picasso = picassoBuilder.build();
        picasso.load(pictureUri).fit().centerCrop().into(mView.getPictureImageView(), new Callback() {
            @Override
            public void onSuccess() {
                mView.showDeletePictureLayout();
            }

            @Override
            public void onError() {
                mView.hideDeletePictureLayout();
            }
        });
    }

    private void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "EditFoodFragment has to be launched using a valid Food identifier as extra");
    }

    private final class EditFoodSubscriber extends DefaultSubscriber {
        @Override
        public void onError(Throwable e) {
            // TODO warn error properly
            System.out.println("EditFoodSubscriber onError");
        }

        @Override
        public void onCompleted() {
            mView.foodSuccessfullyEdited();
        }
    }
}
