package com.zireck.projectk.presentation.presenter;

import com.zireck.projectk.presentation.model.FoodModel;

import java.util.Date;

/**
 * Created by Zireck on 06/08/2015.
 */
public interface AddMealPresenterOld {
    public void initialize();
    public void setFood(FoodModel food);
    public void setDate(int year, int monthOfYear, int dayOfMonth);
    public void setTime(int hour, int minute);
    public void setAmount(int amount);

    public Date getCurrentDate();
    public Date getCurrentTime();
    public String getCurrentMeasure();

    public void validateData(FoodModel food, String date, String time, String daily, String amount);
}
