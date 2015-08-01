package com.zireck.projectk.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;
import com.zireck.projectk.interactor.EditFoodInteractor;
import com.zireck.projectk.interactor.EditFoodInteractorImpl;
import com.zireck.projectk.listener.OnEditFoodInteractorFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.model.GreenDaoHelper;
import com.zireck.projectk.util.MathUtils;
import com.zireck.projectk.util.PictureUtils;
import com.zireck.projectk.view.EditFoodView;

/**
 * Created by Zireck on 31/07/2015.
 */
public class EditFoodPresenterImpl extends AddFoodPresenterImpl implements EditFoodPresenter, OnEditFoodInteractorFinishedListener {

    private EditFoodView mView;
    private EditFoodInteractor mInteractor;

    private long mFoodId;
    private Food mFood;

    private boolean mEdited;

    public EditFoodPresenterImpl(Context context, EditFoodView view) {
        mView = view;
        mInteractor = new EditFoodInteractorImpl();

        mEdited = false;
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
    public void onGetFoodFinished(Food food) {
        mFood = food;

        //mView.setPictureNewName(food.getPicture());
        //receivePicture();
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
    public void onStop() {
        System.out.println("k9d3 EditFoodPresenterImpl.onStop");
        if (mView != null && !mEdited) {
            System.out.println("k9d3 mEdited value = " + mEdited);
            System.out.println("k9d3 deleting");
            PictureUtils.deletePicture(mView.getPictureCurrentName());
            PictureUtils.deletePicture(mView.getPictureNewName());
        }
    }

    @Override
    public void validateData(String name, String brand, boolean isDrink, String calories, String fats, String carboydrates, String proteins, String pictureFileName) {
        if (TextUtils.isEmpty(pictureFileName) || !pictureFileName.equalsIgnoreCase(mFood.getPicture())) {
            System.out.println("k9d3 validateData deletePicture " + mFood.getPicture());
            PictureUtils.deletePicture(mFood.getPicture());
        }

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
            mFood.setPicture(pictureFileName);

            GreenDaoHelper greenDaoHelper = new GreenDaoHelper();
            FoodDao foodDao = greenDaoHelper.getFoodDao();
            foodDao.update(mFood);

            mEdited = true;

            mView.foodSuccessfullyEdited();
        }
    }

    @Override
    public void startCamera(Context context) {
        String fileName = PictureUtils.generateFileName();
        mView.setPictureNewName(fileName);
        System.out.println("k9d3 setted PictureNewName = " + mView.getPictureNewName());

        Intent intent = PictureUtils.getIntentForCameraLaunch(fileName);
        mView.startIntentForCameraLaunch(intent, PictureUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void receivePicture() {
        System.out.println("k9d3 receivePicture EditFoodPresenterImpl *^^^^^ pcitureNewName = " + mView.getPictureNewName());
        if (TextUtils.isEmpty(mView.getPictureNewName())) {
            System.out.println("k9d3 primero");
            return;
        }

        if (!TextUtils.isEmpty(mView.getPictureCurrentName())) {
            System.out.println("k9d3 segundo");
            deleteCurrentPicture();
        }

        Uri pictureUri = PictureUtils.getPhotoFileUri(mView.getPictureNewName());
        Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(mView.getPictureImageView());

        mView.setPictureCurrentName(mView.getPictureNewName());
        mView.setPictureNewName("");

        mView.showDeletePictureLayout();
    }

    @Override
    public void doNotReceivePicture() {
        mView.setPictureNewName("");

        if (!TextUtils.isEmpty(mView.getPictureCurrentName()) || mView.getPictureImageView().getDrawable() != null) {
            mView.showDeletePictureLayout();
        } else {
            mView.hideDeletePictureLayout();
        }
    }

    @Override
    public void loadPicture() {
        if (TextUtils.isEmpty(mFood.getPicture())) {
            mView.deletePicture();
            mView.hideDeletePictureLayout();
            return;
        }

        Uri pictureUri = PictureUtils.getPhotoFileUri(mFood.getPicture());
        Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(mView.getPictureImageView());
        mView.showDeletePictureLayout();
    }

    private boolean isValidFoodId(long foodId) {
        return foodId >= 0;
    }

    private void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "EditFoodFragment has to be launched using a valid Food identifier as extra");
    }
}
