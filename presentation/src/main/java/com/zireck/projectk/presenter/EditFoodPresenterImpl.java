package com.zireck.projectk.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zireck.projectk.interactor.EditFoodInteractor;
import com.zireck.projectk.listener.OnEditFoodInteractorFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.util.MathUtils;
import com.zireck.projectk.util.PictureUtils;
import com.zireck.projectk.view.EditFoodView;

import javax.inject.Inject;

/**
 * Created by Zireck on 31/07/2015.
 */
public class EditFoodPresenterImpl extends AddFoodPresenterImpl implements EditFoodPresenter, OnEditFoodInteractorFinishedListener {

    private Context mContext;
    private EditFoodView mView;
    @Inject EditFoodInteractor mInteractor;

    private long mFoodId;
    private Food mFood;

    private boolean mGetNewPicture;

    @Inject
    public EditFoodPresenterImpl(Context context, EditFoodView view, EditFoodInteractor interactor) {
        mContext = context;
        mView = view;
        mInteractor = interactor;

        mGetNewPicture = false;
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
            mFood.setName(name);
            mFood.setBrand(brand);
            mFood.setIsDrink(isDrink);
            mFood.setCalories(Double.valueOf(calories));
            mFood.setFats(Double.valueOf(fats));
            mFood.setCarbohydrates(Double.valueOf(carboydrates));
            mFood.setProteins(Double.valueOf(proteins));

            if (!mGetNewPicture) {
                mFood.setPicture(mFood.getPicture());
            } else {
                PictureUtils.deletePicture(mFood.getPicture());
                if (mView.getPictureImageView().getDrawable() == null) {
                    mFood.setPicture("");
                } else {
                    mFood.setPicture(consolidateNewPicture());
                }
            }

            mInteractor.updateFood(this, mFood);
        }
    }

    @Override
    public void startCamera(Context context) {
        Intent intent = PictureUtils.getIntentForCameraLaunch(PictureUtils.TEMP_PICTURE_NAME);
        mView.startIntentForCameraLaunch(intent, PictureUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void receivePicture() {
        mGetNewPicture = true;

        Uri pictureUri = PictureUtils.getPhotoFileUri(PictureUtils.TEMP_PICTURE_NAME);
        Picasso.Builder picassoBuilder = new Picasso.Builder(mContext);
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        Picasso picasso = picassoBuilder.build();
        picasso.load(pictureUri).fit().centerCrop().into(mView.getPictureImageView());

        mView.showDeletePictureLayout();
    }

    @Override
    public void doNotReceivePicture() {
        if (mView.getPictureImageView().getDrawable() == null) {
            mView.hideDeletePictureLayout();
        } else {
            mView.showDeletePictureLayout();
        }
    }

    @Override
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

    @Override
    public void onGetFoodFinished(Food food) {
        mFood = food;

        loadPicture();

        mView.setFoodName(food.getName());
        mView.setFoodBrand(food.getBrand());
        mView.setIsDrink(food.getIsDrink());
        mView.setFoodCalories(MathUtils.formatDouble(food.getCalories()));
        mView.setFoodFats(MathUtils.betterFormatDouble(food.getFats()));
        mView.setFoodCarbohydrates(MathUtils.betterFormatDouble(food.getCarbohydrates()));
        mView.setFoodProteins(MathUtils.betterFormatDouble(food.getProteins()));
    }

    @Override
    public void onFoodUpdated() {
        mView.foodSuccessfullyEdited();
    }

    @Override
    public void deleteCurrentPicture() {
        mGetNewPicture = true;
        mView.deletePicture();
        mView.hideDeletePictureLayout();
    }

    private boolean isValidFoodId(long foodId) {
        return foodId >= 0;
    }

    private String consolidateNewPicture() {
        String pictureName = PictureUtils.generateFileName();
        PictureUtils.renameTempPictureTo(pictureName);

        return pictureName;
    }

    private void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "EditFoodFragment has to be launched using a valid Food identifier as extra");
    }
}
