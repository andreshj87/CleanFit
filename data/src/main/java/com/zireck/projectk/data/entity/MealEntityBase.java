package com.zireck.projectk.data.entity;

import java.util.Date;

/**
 * Created by Zireck on 15/07/2015.
 */
public abstract class MealEntityBase {

    public abstract Long getId();
    public abstract Date getDate();
    public abstract int getMealtime();
    public abstract int getGrams();
    public abstract double getCalories();
    public abstract double getFats();
    public abstract double getCarbohydrates();
    public abstract double getProteins();
    public abstract Long getFoodId();
    public abstract FoodEntity getFoodEntity();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Meal Entity Details *****\n");
        stringBuilder.append("id=" + String.valueOf(this.getId()) + "\n");
        stringBuilder.append("date=" + this.getDate() + "\n");
        stringBuilder.append("mealtime=" + String.valueOf(this.getMealtime()) + "\n");
        stringBuilder.append("grams=" + String.valueOf(this.getGrams()) + "\n");
        stringBuilder.append("calories=" + String.valueOf(this.getCalories()) + "\n");
        stringBuilder.append("fats=" + String.valueOf(this.getFats()) + "\n");
        stringBuilder.append("carbohydrates=" + String.valueOf(this.getCarbohydrates()) + "\n");
        stringBuilder.append("proteins=" + String.valueOf(this.getProteins()) + "\n");
        stringBuilder.append("foodId=" + String.valueOf(this.getFoodId()) + "\n");
        stringBuilder.append("food=" + this.getFoodEntity().getName() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

}
