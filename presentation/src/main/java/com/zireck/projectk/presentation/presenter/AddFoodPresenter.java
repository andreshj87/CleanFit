package com.zireck.projectk.presentation.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;

import com.squareup.picasso.Picasso;
import com.zireck.projectk.R;
import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.util.PictureUtils;
import com.zireck.projectk.presentation.view.AddFoodView;
import com.zireck.projectk.presentation.view.View;

import net.steamcrafted.materialiconlib.MaterialIconView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 17/08/2015.
 */
public class AddFoodPresenter implements Presenter {

    private Context mContext;
    private AddFoodView mView;
    private Interactor mAddFoodInteractor;

    @Inject
    public AddFoodPresenter(Context context, @Named("addFood") Interactor addFoodInteractor) {
        mContext = context;
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

    public void addFood() {
        // TODO: You need to pass the FoodModel object somehow to FoodModule
        // See: https://github.com/android10/Android-CleanArchitecture/issues/32
        mAddFoodInteractor.execute(new AddFoodSubscriber());
    }

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
            FoodModel food = new FoodModel(88);
            food.setName(name);
            food.setBrand(brand);
            food.setIsDrink(isDrink);
            food.setCalories(Double.valueOf(calories));
            food.setFats(Double.valueOf(fats));
            food.setCarbohydrates(Double.valueOf(carboydrates));
            food.setProteins(Double.valueOf(proteins));
            food.setPicture(consolidateNewPicture());

            addFood();

        }
    }

    public void isDrink(boolean isDrink) {
        if (isDrink) {
            mView.setMl();
        } else {
            mView.setGr();
        }
    }

    public void startCamera(Context context) {
        Intent intent = PictureUtils.getIntentForCameraLaunch(PictureUtils.TEMP_PICTURE_NAME);
        mView.startIntentForCameraLaunch(intent, PictureUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
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
        picasso.load(pictureUri).fit().centerCrop().into(mView.getPictureImageView());

        mView.showDeletePictureLayout();
    }

    public void deleteCurrentPicture() {
        PictureUtils.deletePicture(PictureUtils.TEMP_PICTURE_NAME);
        mView.deletePicture();
        mView.hideDeletePictureLayout();
    }

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
        PictureUtils.renameTempPictureTo(pictureName);

        return pictureName;
    }

    private final class AddFoodSubscriber extends DefaultSubscriber {
        @Override
        public void onError(Throwable e) {
            // TODO warn error
        }

        @Override
        public void onCompleted() {
            mView.foodSuccessfullyAdded();
        }
    }
}
