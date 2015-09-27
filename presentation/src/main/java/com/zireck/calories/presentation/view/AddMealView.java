package com.zireck.calories.presentation.view;

import com.zireck.calories.presentation.model.FoodModel;

import java.util.Collection;

/**
 * Created by Zireck on 06/08/2015.
 */
public interface AddMealView extends View {
    public void setDateText(String date);
    public void setTimeText(String time);
    public void setAmountText(String amount);
    public void setCaloriesText(String calories);
    public void setFatsText(String fats);
    public void setCarbohydratesText(String carbohydrates);
    public void setProteinsText(String proteins);

    public String getAmount();

    public void setFoodError();
    public void setDateError();
    public void setTimeError();
    public void setDailyError();
    public void setAmountError();

    public void setGr();
    public void setMl();

    void setFoodItems(Collection<FoodModel> foodItems);
    void renderFoodInView(FoodModel food);

    void mealSuccessfullyAdded();
}
