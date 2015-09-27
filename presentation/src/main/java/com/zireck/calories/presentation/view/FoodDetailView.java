package com.zireck.calories.presentation.view;

import lecho.lib.hellocharts.model.PieChartData;

/**
 * Created by Zireck on 29/07/2015.
 */
public interface FoodDetailView extends View {
    void setName(String foodName);
    void setBrand(String foodBrand);
    void setCalories(String foodCalories);
    void setFatsAmount(String fatsAmount);
    void setFatsPercent(String fatsPercent);
    void setCarbohydratesAmount(String carbohydratesAmount);
    void setCarbohydratesPercent(String carbohydratesPercent);
    void setProteinsAmount(String proteinsAmount);
    void setProteinsPercent(String proteinsPercent);
    void setNutrientsDescription(String description);
    void setFoodPicture(String foodImage);
    void setPieChartData(PieChartData pieChartData);
    int getFatsColor();
    int getCarbohydrtesColor();
    int getProteinsColor();
    void foodDeleted();
}
