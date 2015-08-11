package com.zireck.projectk.presentation.view;

import android.content.Intent;
import android.widget.ImageView;

import net.steamcrafted.materialiconlib.MaterialIconView;

/**
 * Created by Zireck on 24/07/2015.
 */
public interface AddFoodView {
    public void foodSuccessfullyAdded();
    public void setGr();
    public void setMl();
    public void setNameError();
    public void setBrandError();
    public void setCaloriesError();
    public void setFatsError();
    public void setCarbohydratesError();
    public void setProteinsError();

    public MaterialIconView getIconName();
    public MaterialIconView getIconBrand();
    public MaterialIconView getIconCalories();
    public MaterialIconView getIconNutrients();

    public void startIntentForCameraLaunch(Intent intent, final int requestCode);

    public ImageView getPictureImageView();

    public void deletePicture();
    public void showDeletePictureLayout();
    public void hideDeletePictureLayout();
}
