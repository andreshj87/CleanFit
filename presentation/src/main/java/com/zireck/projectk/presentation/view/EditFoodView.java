package com.zireck.projectk.presentation.view;

import android.content.Intent;
import android.widget.ImageView;

import net.steamcrafted.materialiconlib.MaterialIconView;

/**
 * Created by Zireck on 31/07/2015.
 */
public interface EditFoodView extends View {
    String getFoodIdTag();
    void foodSuccessfullyEdited();

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

    void setFoodName(String foodName);
    void setFoodBrand(String foodBrand);
    void setFoodCalories(String foodCalories);
    void setFoodFats(String foodFats);
    void setFoodCarbohydrates(String foodCarbohydrates);
    void setFoodProteins(String foodProteins);

    void setIsDrink(boolean isDrink);
}
