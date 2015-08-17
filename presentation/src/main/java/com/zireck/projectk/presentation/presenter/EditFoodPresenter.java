package com.zireck.projectk.presentation.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zireck.projectk.R;
import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.EditFood;
import com.zireck.projectk.presentation.mapper.FoodModelDataMapper;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.util.PictureUtils;
import com.zireck.projectk.presentation.view.EditFoodView;
import com.zireck.projectk.presentation.view.View;

import net.steamcrafted.materialiconlib.MaterialIconView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 17/08/2015.
 */
public class EditFoodPresenter implements Presenter {

    private Context mContext;
    private EditFoodView mView;
    private EditFood mEditFoodInteractor;
    private FoodModelDataMapper mFoodModelDataMapper;

    private FoodModel mFood;

    private boolean mGetNewPicture;

    @Inject
    public EditFoodPresenter(Context context, @Named("editFood") EditFood editFoodInteractor,
                             FoodModelDataMapper foodModelDataMapper) {
        mContext = context;
        mEditFoodInteractor = editFoodInteractor;
        mFoodModelDataMapper = foodModelDataMapper;

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

            editFood(mFood);
        }
    }

    private void editFood(FoodModel food) {
        mEditFoodInteractor.setFood(mFoodModelDataMapper.transformInverse(food));
        mEditFoodInteractor.execute(new EditFoodSubscriber());
    }

    public void startCamera(Context context) {
        Intent intent = PictureUtils.getIntentForCameraLaunch(PictureUtils.TEMP_PICTURE_NAME);
        mView.startIntentForCameraLaunch(intent, PictureUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

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

    public void doNotReceivePicture() {
        if (mView.getPictureImageView().getDrawable() == null) {
            mView.hideDeletePictureLayout();
        } else {
            mView.showDeletePictureLayout();
        }
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

    public void deleteCurrentPicture() {
        mGetNewPicture = true;
        mView.deletePicture();
        mView.hideDeletePictureLayout();
    }

    private String consolidateNewPicture() {
        String pictureName = PictureUtils.generateFileName();
        PictureUtils.renameTempPictureTo(pictureName);

        return pictureName;
    }

    public void isDrink(boolean isDrink) {
        if (isDrink) {
            mView.setMl();
        } else {
            mView.setGr();
        }
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
        mView.getIconName().setColorResource(R.color.icon_deactivated);
        mView.getIconBrand().setColorResource(R.color.icon_deactivated);
        mView.getIconCalories().setColorResource(R.color.icon_deactivated);
        mView.getIconNutrients().setColorResource(R.color.icon_deactivated);
    }

    private void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "EditFoodFragment has to be launched using a valid Food identifier as extra");
    }

    private final class EditFoodSubscriber extends DefaultSubscriber {
        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onCompleted() {
            mView.foodSuccessfullyEdited();
        }
    }
}
