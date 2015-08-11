package com.zireck.projectk.presentation.view;

import lecho.lib.hellocharts.model.PieChartData;

/**
 * Created by Zireck on 29/07/2015.
 */
public interface FoodDetailView {
    public String getFoodIdTag();
    public void setFoodName(String foodName);
    public void setFoodBrand(String foodBrand);
    public void setFoodCalories(String foodCalories);
    public void setFatsAmount(String fatsAmount);
    public void setFatsPercent(String fatsPercent);
    public void setCarbohydratesAmount(String carbohydratesAmount);
    public void setCarbohydratesPercent(String carbohydratesPercent);
    public void setProteinsAmount(String proteinsAmount);
    public void setProteinsPercent(String proteinsPercent);
    public void setFoodPicture(String foodImage);
    public void setPieChartData(PieChartData pieChartData);
    public int getFatsColor();
    public int getCarbohydrtesColor();
    public int getProteinsColor();
    public void foodDeleted();
    public void setNutrientsDescription(String description);
}
