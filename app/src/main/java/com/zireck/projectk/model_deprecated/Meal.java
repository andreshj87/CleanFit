package com.zireck.projectk.model_deprecated;

import com.zireck.projectk.enums.Mealtime;
import com.zireck.projectk.util.DateUtils;

import java.util.Date;

/**
 * Created by Zireck on 13/07/2015.
 */
public class Meal {
    private Food food;
    private int id;
    private int grams;
    private Nutrients nutrients;
    private Mealtime mealtime;
    private Date date;

    public Meal() {
        nutrients = new Nutrients();
    }

    public Meal(Food food) {
        setFood(food);
    }

    public Meal(Food food, int id, int grams, Nutrients nutrients, Mealtime mealtime, Date date) {
        setFood(food);
        this.id = id;
        setGrams(grams);
        this.nutrients = nutrients;
        this.mealtime = mealtime;
        this.date = date;
    }

    public void calculateNutrients() {
        if (food == null) {
            throw new NullPointerException("Food not set");
        }

        if (grams <= 0) {
            throw new IllegalStateException("Invalid grams");
        }

        if (nutrients == null) {
            nutrients = new Nutrients();
        }

        nutrients.setCalories((grams * food.getDefaultNutrients().getCalories()) / 100);
        nutrients.setFats((grams * food.getDefaultNutrients().getFats()) / 100);
        nutrients.setCarbohydrates((grams * food.getDefaultNutrients().getCarbohydrates()) / 100);
        nutrients.setProteins((grams * food.getDefaultNutrients().getProteins()) / 100);
    }

    @Override
    public String toString() {
        super.toString();
        StringBuilder meal = new StringBuilder(food.toString());

        //meal.append(" mealtime=" + mealtime.getMealtime());
        meal.append(" date=" + DateUtils.getFormattedMealDate(date));
        meal.append(" grams=" + grams);
        meal.append(" final nutrients = (");
        meal.append("c:" + nutrients.getCalories());
        meal.append(" f:" + nutrients.getFats());
        meal.append(" ch:" + nutrients.getCarbohydrates());
        meal.append(" p:" + nutrients.getProteins());
        meal.append(")");

        return meal.toString();
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;

        if (grams > 0) {
            calculateNutrients();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;

        if (food != null) {
            calculateNutrients();
        }
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public Mealtime getMealtime() {
        return mealtime;
    }

    public void setMealtime(Mealtime mealtime) {
        this.mealtime = mealtime;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        return DateUtils.getFormattedMealDate(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
