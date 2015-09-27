package com.zireck.calories.presentation.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;

import com.squareup.picasso.Picasso;
import com.zireck.calories.presentation.util.MathUtils;
import com.zireck.calories.R;
import com.zireck.calories.presentation.mapper.FoodModelDataMapper;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.util.PictureUtils;
import com.zireck.calories.presentation.view.AddEditFoodView;
import com.zireck.calories.presentation.view.View;

import net.steamcrafted.materialiconlib.MaterialIconView;

/**
 * Created by Zireck on 18/08/2015.
 */
public abstract class AddEditFoodPresenter implements Presenter {

    protected Context mContext;
    protected FoodModelDataMapper mFoodModelDataMapper;

    public AddEditFoodPresenter(Context context, FoodModelDataMapper foodModelDataMapper) {
        mContext = context;
        mFoodModelDataMapper = foodModelDataMapper;

        PictureUtils.deletePicture(PictureUtils.TEMP_PICTURE_NAME);
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public abstract AddEditFoodView getView();

    public abstract void addOrEditFood(FoodModel foodModel);

    public void startCamera(Context context) {
        Intent intent = PictureUtils.getIntentForCameraLaunch(PictureUtils.TEMP_PICTURE_NAME);
        getView().startIntentForCameraLaunch(intent, PictureUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void receivePicture() {
        Uri pictureUri = PictureUtils.getPhotoFileUri(PictureUtils.TEMP_PICTURE_NAME);
        Picasso.Builder picassoBuilder = new Picasso.Builder(mContext);
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        Picasso picasso = picassoBuilder.build();
        picasso.load(pictureUri).fit().centerCrop().into(getView().getPictureImageView());

        getView().showDeletePictureLayout();
    }

    public void deleteCurrentPicture() {
        getView().deletePicture();
        getView().hideDeletePictureLayout();
    }

    protected String consolidateNewPicture() {
        String pictureName = PictureUtils.generateFileName();
        PictureUtils.renameTempPictureTo(pictureName);

        return pictureName;
    }

    public void isDrink(boolean isDrink) {
        if (isDrink) {
            getView().setMl();
        } else {
            getView().setGr();
        }
    }

    public void hasFocus(EditText editText) {
        deactivateAllIcons();

        switch (editText.getId()) {
            case R.id.food_name_edit_text:
                activateIcon(getView().getIconName());
                break;
            case R.id.food_brand_edit_text:
                activateIcon(getView().getIconBrand());
                break;
            case R.id.food_calories_edit_text:
                activateIcon(getView().getIconCalories());
                break;
            case R.id.food_fats_edit_text:
            case R.id.food_carbohydrates_edit_text:
            case R.id.food_proteins_edit_text:
                activateIcon(getView().getIconNutrients());
                break;
        }
    }

    public void lostFocus(EditText editText) {
        switch (editText.getId()) {
            case R.id.food_name_edit_text:
                deactivateIcon(getView().getIconName());
                break;
            case R.id.food_brand_edit_text:
                deactivateIcon(getView().getIconBrand());
                break;
            case R.id.food_calories_edit_text:
                deactivateIcon(getView().getIconCalories());
                break;
            case R.id.food_fats_edit_text:
            case R.id.food_carbohydrates_edit_text:
            case R.id.food_proteins_edit_text:
                deactivateIcon(getView().getIconNutrients());
                break;
        }
    }

    private void activateIcon(MaterialIconView icon) {
        if (icon != null) {
            icon.setColorResource(R.color.icon_activated);
        }
    }

    private void deactivateIcon(MaterialIconView icon) {
        if (icon != null) {
            icon.setColorResource(R.color.icon_deactivated);
        }
    }

    private void deactivateAllIcons() {
        getView().getIconName().setColorResource(R.color.icon_deactivated);
        getView().getIconBrand().setColorResource(R.color.icon_deactivated);
        getView().getIconCalories().setColorResource(R.color.icon_deactivated);
        getView().getIconNutrients().setColorResource(R.color.icon_deactivated);
    }

    public void validateData(String name, String brand, boolean isDrink, String calories,
                             String fats, String carboydrates, String proteins) {
        boolean error = false;

        if (TextUtils.isEmpty(name)) {
            getView().setNameError();
            error = true;
        }

        if (TextUtils.isEmpty(brand)) {
            getView().setBrandError();
            error = true;
        }

        if (TextUtils.isEmpty(calories) || !MathUtils.isDouble(calories)) {
            getView().setCaloriesError();
            error = true;
        }

        if (TextUtils.isEmpty(fats) || !MathUtils.isDouble(fats)) {
            getView().setFatsError();
            error = true;
        }

        if (TextUtils.isEmpty(carboydrates) || !MathUtils.isDouble(carboydrates)) {
            getView().setCarbohydratesError();
            error = true;
        }

        if (TextUtils.isEmpty(proteins) || !MathUtils.isDouble(proteins)) {
            getView().setProteinsError();
            error = true;
        }

        if (!error) {
            FoodModel food = new FoodModel();
            food.setName(name);
            food.setBrand(brand);
            food.setIsDrink(isDrink);
            food.setCalories(Double.valueOf(calories));
            food.setFats(Double.valueOf(fats));
            food.setCarbohydrates(Double.valueOf(carboydrates));
            food.setProteins(Double.valueOf(proteins));

            addOrEditFood(food);
        }
    }
}
