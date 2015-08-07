package com.zireck.projectk.presenter;

import com.zireck.projectk.model.Food;

import java.util.Date;

/**
 * Created by Zireck on 06/08/2015.
 */
public interface AddMealPresenter {
    public void initialize();
    public void setFood(Food food);
    public void setDate(int year, int monthOfYear, int dayOfMonth);
    public void setTime(int hour, int minute);
    public void setAmount(int amount);

    public Date getCurrentDate();
    public Date getCurrentTime();
    public String getCurrentMeasure();

    public void validateData(Food food, String date, String time, String daily, String amount);
}
