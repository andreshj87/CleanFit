package com.zireck.projectk.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.squareup.picasso.Callback;
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

    private Context mContext;
    private EditFoodView mView;
    private EditFoodInteractor mInteractor;

    private long mFoodId;
    private Food mFood;

    private boolean mGetNewPicture;

    //private boolean mEdited;
    //private boolean mPictureMarkedForDeletion;

    //private String mNewPictureName;
    //private String mCurrentPictureName;

    public EditFoodPresenterImpl(Context context, EditFoodView view) {
        mContext = context;
        mView = view;
        mInteractor = new EditFoodInteractorImpl();

        //mEdited = false;
        //mPictureMarkedForDeletion = false;
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

    /*
    @Override
    public void onStop() {
        System.out.println("k9d3 onStop");
        if (mView != null && !mEdited) {
            //PictureUtils.deletePicture(mView.getPictureCurrentName());
            //PictureUtils.deletePicture(mView.getPictureNewName());
            System.out.println("k9d3 deleting picture");
            deleteCurrentPicture();
        }
    }*/

    @Override
    public void deleteCurrentPicture() {
        mGetNewPicture = true;
        //mPictureMarkedForDeletion = true;

        //PictureUtils.deletePicture(mView.getPictureCurrentName());
        //PictureUtils.deletePicture(mCurrentPictureName);
        //mView.setPictureCurrentName("");
        //mCurrentPictureName = "";
        mView.deletePicture();
        mView.hideDeletePictureLayout();
    }

    @Override
    public void validateData(String name, String brand, boolean isDrink, String calories, String fats, String carboydrates, String proteins) {
        //if (TextUtils.isEmpty(pictureFileName) || !pictureFileName.equalsIgnoreCase(mFood.getPicture())) {
        /*if (mPictureMarkedForDeletion || ( (!TextUtils.isEmpty(mCurrentPictureName)) && (!mCurrentPictureName.equalsIgnoreCase(mFood.getPicture())) ) ) {
            PictureUtils.deletePicture(mFood.getPicture());
            mFood.setPicture(mCurrentPictureName);
        }*/

        if (!mGetNewPicture) {
            mFood.setPicture(mFood.getPicture());
            System.out.println("k9d3 no se ha tocado la picture");
        } else {
            PictureUtils.deletePicture(mFood.getPicture());
            if (mView.getPictureImageView().getDrawable() == null) {
                mFood.setPicture("");
                System.out.println("k9d3 se ha tocado la picture, pero es null");
            } else {
                System.out.println("k9d3 drawable=" + mView.getPictureImageView().getDrawable().toString());

                mFood.setPicture(consolidateNewPicture());
                System.out.println("k9d3 se ha tocado la picture, estableciendo temp.jpg");
            }
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

            GreenDaoHelper greenDaoHelper = new GreenDaoHelper();
            FoodDao foodDao = greenDaoHelper.getFoodDao();
            foodDao.update(mFood);

            //mEdited = true;

            mView.foodSuccessfullyEdited();
        }
    }

    @Override
    public void startCamera(Context context) {
        //String fileName = PictureUtils.generateFileName();
        //mView.setPictureNewName(fileName);
        //mNewPictureName = fileName;

        //System.out.println("k9d3 generating new file name = " + fileName);

        Intent intent = PictureUtils.getIntentForCameraLaunch(PictureUtils.TEMP_PICTURE_NAME);
        mView.startIntentForCameraLaunch(intent, PictureUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void receivePicture() {
        mGetNewPicture = true;
        //mPictureMarkedForDeletion = true;

        /*
        System.out.println("k9d3 receiving picture");
        System.out.println("k9d3 currentFileName = " + mCurrentPictureName);
        System.out.println("k9d3 newFileName = " + mNewPictureName);

        System.out.println("k9d3 receivePicture EditFoodPresenterImpl *^^^^^ pcitureNewName = " + mNewPictureName);*/
        //if (TextUtils.isEmpty(mView.getPictureNewName())) {
        /*if (TextUtils.isEmpty(mNewPictureName)) {
            System.out.println("k9d3 primero");
            return;
        }*/

        //if (!TextUtils.isEmpty(mView.getPictureCurrentName())) {
        /*if (!TextUtils.isEmpty(mCurrentPictureName)) {
            System.out.println("k9d3 segundo");
            deleteCurrentPicture();
        }*/

        //Uri pictureUri = PictureUtils.getPhotoFileUri(mView.getPictureNewName());
        Uri pictureUri = PictureUtils.getPhotoFileUri(PictureUtils.TEMP_PICTURE_NAME);
        System.out.println("k9d3 loadan pic = " + pictureUri.getPath());
        Picasso.Builder picassoBuilder = new Picasso.Builder(mContext);
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                System.out.println("k9d3 called listner");
                exception.printStackTrace();
            }
        });
        Picasso picasso = picassoBuilder.build();
        picasso.load(pictureUri).fit().centerCrop().into(mView.getPictureImageView());


        /*
        Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(mView.getPictureImageView(), new Callback() {
            @Override
            public void onSuccess() {
                System.out.println("onSuccess");
            }

            @Override
            public void onError() {
                System.out.println("onError");
            }
        });*/

        //mView.setPictureCurrentName(mView.getPictureNewName());
        //mCurrentPictureName = mNewPictureName;
        //mView.setPictureNewName("");
        //mNewPictureName = "";

        System.out.println("k9d3 picture loaded, showing deletePicLayout");
        mView.showDeletePictureLayout();
    }

    @Override
    public void doNotReceivePicture() {
        //mView.setPictureNewName("");
        //mNewPictureName = "";

        //if (!TextUtils.isEmpty(mView.getPictureCurrentName()) || mView.getPictureImageView().getDrawable() != null) {
        /*if (!TextUtils.isEmpty(mCurrentPictureName) || !mPictureMarkedForDeletion) {
            mView.showDeletePictureLayout();
        } else {
            mView.hideDeletePictureLayout();
        }*/

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
        //Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(mView.getPictureImageView());
        Picasso.Builder picassoBuilder = new Picasso.Builder(mContext);
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                System.out.println("k9d3 called");
                mView.hideDeletePictureLayout();
            }
        });
        Picasso picasso = picassoBuilder.build();
        picasso.load(pictureUri).fit().centerCrop().into(mView.getPictureImageView(), new Callback() {
            @Override
            public void onSuccess() {
                System.out.println("k9d3 called callback succ");
                mView.showDeletePictureLayout();
            }

            @Override
            public void onError() {
                System.out.println("k9d3 called callback fail");
                mView.hideDeletePictureLayout();
            }
        });
    }

    private boolean isValidFoodId(long foodId) {
        return foodId >= 0;
    }

    private void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "EditFoodFragment has to be launched using a valid Food identifier as extra");
    }

    private String consolidateNewPicture() {
        String pictureName = PictureUtils.generateFileName();
        if (!PictureUtils.renameTempPictureTo(pictureName)) {
            /*throw new IllegalArgumentException(
                    "Picture origin must exists and Picture destiny must be a non-empty string");*/
            System.out.println("k9d3 exception lanzado (EditFoodPresenterImpl");
        }

        return pictureName;
    }
}
