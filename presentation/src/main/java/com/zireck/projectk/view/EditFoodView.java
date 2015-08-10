package com.zireck.projectk.view;

/**
 * Created by Zireck on 31/07/2015.
 */
public interface EditFoodView extends AddFoodView {
    public String getFoodIdTag();
    public void foodSuccessfullyEdited();

    public void setFoodName(String foodName);
    public void setFoodBrand(String foodBrand);
    public void setFoodCalories(String foodCalories);
    public void setFoodFats(String foodFats);
    public void setFoodCarbohydrates(String foodCarbohydrates);
    public void setFoodProteins(String foodProteins);

    public void setIsDrink(boolean isDrink);
}
