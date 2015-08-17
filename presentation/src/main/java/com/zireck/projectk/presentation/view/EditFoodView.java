package com.zireck.projectk.presentation.view;

import android.content.Intent;
import android.widget.ImageView;

import net.steamcrafted.materialiconlib.MaterialIconView;

/**
 * Created by Zireck on 31/07/2015.
 */
public interface EditFoodView extends View {
    public String getFoodIdTag();
    public void foodSuccessfullyEdited();

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

    public void setFoodName(String foodName);
    public void setFoodBrand(String foodBrand);
    public void setFoodCalories(String foodCalories);
    public void setFoodFats(String foodFats);
    public void setFoodCarbohydrates(String foodCarbohydrates);
    public void setFoodProteins(String foodProteins);

    public void setIsDrink(boolean isDrink);
}
