package com.zireck.projectk.model;

import com.zireck.projectk.enums.Mealtime;
import com.zireck.projectk.util.DateUtils;

import java.util.Date;

/**
 * Created by Zireck on 15/07/2015.
 */
public abstract class MealBase {

    public abstract Long getId();
    public abstract Date getDate();
    public abstract int getMealtime();
    public abstract int getGrams();
    public abstract double getCalories();
    public abstract void setCalories(double calories);
    public abstract double getFats();
    public abstract void setFats(double fats);
    public abstract double getCarbohydrates();
    public abstract void setCarbohydrates(double carbohydrates);
    public abstract double getProteins();
    public abstract void setProteins(double proteins);
    public abstract long getFoodId();
    public abstract Food getFood();

    @Override
    public String toString() {
        super.toString();

        StringBuilder meal = new StringBuilder(getFood().toString());

        meal.append("\nMEAL: [");
        meal.append(String.valueOf(getId()));
        meal.append("] date=");
        meal.append(DateUtils.getFormattedMealDate(getDate()));
        meal.append(" mealtime=");
        meal.append(Mealtime.values()[getMealtime()].getStringValue());
        meal.append(" grams=");
        meal.append(String.valueOf(getGrams()));
        meal.append(" nutrients = (");
        meal.append("c:");
        meal.append(String.valueOf(getCalories()));
        meal.append(" f:");
        meal.append(String.valueOf(getFats()));
        meal.append(" ch:");
        meal.append(String.valueOf(getCarbohydrates()));
        meal.append(" p:");
        meal.append(String.valueOf(getProteins()));
        meal.append(")");

        return meal.toString();
    }

    public void calculateNutrients() {
        if (getFood() == null) {
            throw new NullPointerException("Food not set");
        }

        if (getGrams() <= 0) {
            throw new IllegalStateException("Invalid grams");
        }

        calculateCalories();
        calculateFats();
        calculateCarbohydrates();
        calculateProteins();
    }

    private void calculateCalories() {
        setCalories((getGrams() * getFood().getCalories()) / 100);
    }

    private void calculateFats() {
        setFats((getGrams() * getFood().getFats()) / 100);
    }

    private void calculateCarbohydrates() {
        setCarbohydrates((getGrams() * getFood().getCarbohydrates()) / 100);
    }

    private void calculateProteins() {
        setProteins((getGrams() * getFood().getProteins()) / 100);
    }

    public String getFormattedDate() {
        return DateUtils.getFormattedMealDate(getDate());
    }

    public String getFormattedMealtime() {
        return Mealtime.values()[getMealtime()].getStringValue();
    }
}
