package com.zireck.projectk.presentation.view;

import android.content.Intent;
import android.widget.ImageView;

import net.steamcrafted.materialiconlib.MaterialIconView;

/**
 * Created by Zireck on 24/07/2015.
 */
public interface AddFoodView extends View {
    void foodSuccessfullyAdded();
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

    void startIntentForCameraLaunch(Intent intent, final int requestCode);

    ImageView getPictureImageView();

    void deletePicture();
    void showDeletePictureLayout();
    void hideDeletePictureLayout();
}
