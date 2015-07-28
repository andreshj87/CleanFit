package com.zireck.projectk.view;

import android.content.Intent;
import android.graphics.Bitmap;

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

    public void startIntentForCameraLaunch(Intent intent, final int requestCode);
    public String getPictureCurrentName();
    public String getPictureNewName();
    public void setPictureCurrentName(String fileName);
    public void setPictureNewName(String fileName);
    public void setPicture(Bitmap picture);
    public void deletePicture();
}
