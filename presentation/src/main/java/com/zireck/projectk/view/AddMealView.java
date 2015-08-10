package com.zireck.projectk.view;

import com.zireck.projectk.model.Food;

import java.util.List;

/**
 * Created by Zireck on 06/08/2015.
 */
public interface AddMealView {
    public void setSpinnerFoodItems(List<Food> foodItems);

    public void setDateText(String date);
    public void setTimeText(String time);
    public void setAmountText(String amount);
    public void setCaloriesText(String calories);
    public void setFatsText(String fats);
    public void setCarbohydratesText(String carbohydrates);
    public void setProteinsText(String proteins);

    public Food getFood();
    public String getAmount();

    public void setFoodError();
    public void setDateError();
    public void setTimeError();
    public void setDailyError();
    public void setAmountError();

    public void setGr();
    public void setMl();

}
