package com.zireck.projectk.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;

import com.squareup.picasso.Picasso;
import com.zireck.projectk.R;
import com.zireck.projectk.util.PictureUtils;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.model.GreenDaoHelper;
import com.zireck.projectk.util.MathUtils;
import com.zireck.projectk.view.AddFoodView;

import net.steamcrafted.materialiconlib.MaterialIconView;

/**
 * Created by Zireck on 24/07/2015.
 */
public class AddFoodPresenterImpl implements AddFoodPresenter {

    protected Context mContext;
    private AddFoodView mView;

    //private boolean mAdded;

    //private String mCurrentPictureName;
    //private String mNewPictureName;

    public AddFoodPresenterImpl() {

    }

    public AddFoodPresenterImpl(Context context, AddFoodView view) {
        mContext = context;
        mView = view;

        //mAdded = false;
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
            food.setPicture(consolidateNewPicture());

            GreenDaoHelper greenDaoHelper = new GreenDaoHelper();
            FoodDao foodDao = greenDaoHelper.getFoodDao();
            foodDao.insert(food);

            //mAdded = true;

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

    @Override
    public void startCamera(Context context) {
        //String fileName = PictureUtils.generateFileName();
        //mView.setPictureNewName(fileName);
        //mNewPictureName = fileName;

        Intent intent = PictureUtils.getIntentForCameraLaunch(PictureUtils.TEMP_PICTURE_NAME);
        mView.startIntentForCameraLaunch(intent, PictureUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void receivePicture() {
        //if (TextUtils.isEmpty(mView.getPictureNewName())) {
        /*if (TextUtils.isEmpty(mNewPictureName)) {
            return;
        }*/

        //if (!TextUtils.isEmpty(mView.getPictureCurrentName())) {
        /*if (!TextUtils.isEmpty(mCurrentPictureName)) {
            deleteCurrentPicture();
        }*/

        //Uri pictureUri = PictureUtils.getPhotoFileUri(mView.getPictureNewName());
        Uri pictureUri = PictureUtils.getPhotoFileUri(PictureUtils.TEMP_PICTURE_NAME);
        //Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(mView.getPictureImageView());
        Picasso.Builder picassoBuilder = new Picasso.Builder(mContext);
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        Picasso picasso = picassoBuilder.build();
        picasso.load(pictureUri).fit().centerCrop().into(mView.getPictureImageView());

        //mView.setPictureCurrentName(mView.getPictureNewName());
        //mCurrentPictureName = mNewPictureName;
        //mView.setPictureNewName("");
        //mNewPictureName = "";

        mView.showDeletePictureLayout();
    }

    @Override
    public void doNotReceivePicture() {
        //mView.setPictureNewName("");
        //mNewPictureName = "";

        //if (!TextUtils.isEmpty(mView.getPictureCurrentName())) {
        /*if (!TextUtils.isEmpty(mCurrentPictureName)) {
            mView.showDeletePictureLayout();
        } else {
            mView.hideDeletePictureLayout();
        }*/
    }

    @Override
    public void deleteCurrentPicture() {
        PictureUtils.deletePicture(PictureUtils.TEMP_PICTURE_NAME);
        mView.deletePicture();
        mView.hideDeletePictureLayout();

        //PictureUtils.deletePicture(mView.getPictureCurrentName());
        //PictureUtils.deletePicture(mCurrentPictureName);
        //mView.setPictureCurrentName("");
        //mCurrentPictureName = "";
        //mView.deletePicture();
        //mView.hideDeletePictureLayout();
    }

    @Override
    public void hasFocus(EditText editText) {
        deactivateAllIcons();

        switch (editText.getId()) {
            case R.id.food_name_edit_text:
                activateIcon(mView.getIconName());
                break;
            case R.id.food_brand_edit_text:
                activateIcon(mView.getIconBrand());
                break;
            case R.id.food_calories_edit_text:
                activateIcon(mView.getIconCalories());
                break;
            case R.id.food_fats_edit_text:
            case R.id.food_carbohydrates_edit_text:
            case R.id.food_proteins_edit_text:
                activateIcon(mView.getIconNutrients());
                break;
        }
    }

    @Override
    public void lostFocus(EditText editText) {
        switch (editText.getId()) {
            case R.id.food_name_edit_text:
                deactivateIcon(mView.getIconName());
                break;
            case R.id.food_brand_edit_text:
                deactivateIcon(mView.getIconBrand());
                break;
            case R.id.food_calories_edit_text:
                deactivateIcon(mView.getIconCalories());
                break;
            case R.id.food_fats_edit_text:
            case R.id.food_carbohydrates_edit_text:
            case R.id.food_proteins_edit_text:
                deactivateIcon(mView.getIconNutrients());
                break;
        }
    }

    private void activateIcon(MaterialIconView icon) {
        icon.setColorResource(R.color.icon_activated);
    }

    private void deactivateIcon(MaterialIconView icon) {
        icon.setColorResource(R.color.icon_deactivated);
    }

    private void deactivateAllIcons() {
        mView.getIconName().setColorResource(R.color.icon_deactivated);
        mView.getIconBrand().setColorResource(R.color.icon_deactivated);
        mView.getIconCalories().setColorResource(R.color.icon_deactivated);
        mView.getIconNutrients().setColorResource(R.color.icon_deactivated);
    }

    private String consolidateNewPicture() {
        String pictureName = PictureUtils.generateFileName();
        if (!PictureUtils.renameTempPictureTo(pictureName)) {
            /*throw new IllegalArgumentException(
                    "Picture origin must exists and Picture destiny must be a non-empty string");*/
            System.out.println("k9d3 exception lanzado...");
        }

        return pictureName;
    }
}
