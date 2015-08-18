package com.zireck.projectk.presentation.view;

/**
 * Created by Zireck on 31/07/2015.
 */
public interface EditFoodView extends AddEditFoodView {
    void foodSuccessfullyEdited();

    void setFoodName(String foodName);
    void setFoodBrand(String foodBrand);
    void setIsDrink(boolean isDrink);
    void setFoodCalories(String foodCalories);
    void setFoodFats(String foodFats);
    void setFoodCarbohydrates(String foodCarbohydrates);
    void setFoodProteins(String foodProteins);
}
