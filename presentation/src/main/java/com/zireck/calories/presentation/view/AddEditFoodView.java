package com.zireck.calories.presentation.view;

import android.content.Intent;
import android.widget.ImageView;

import net.steamcrafted.materialiconlib.MaterialIconView;

/**
 * Created by Zireck on 18/08/2015.
 */
public interface AddEditFoodView extends View {
    void setGr();
    void setMl();
    void setNameError();
    void setBrandError();
    void setCaloriesError();
    void setFatsError();
    void setCarbohydratesError();
    void setProteinsError();

    MaterialIconView getIconName();
    MaterialIconView getIconBrand();
    MaterialIconView getIconCalories();
    MaterialIconView getIconNutrients();

    ImageView getPictureImageView();
    void startIntentForCameraLaunch(Intent intent, final int requestCode);
    void deletePicture();
    void showDeletePictureLayout();
    void hideDeletePictureLayout();
}
