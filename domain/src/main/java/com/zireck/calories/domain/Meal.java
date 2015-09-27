package com.zireck.calories.domain;

import java.util.Date;

/**
 * Created by Zireck on 11/08/2015.
 */
public class Meal {

    private final long id;
    private Date date;
    private int mealtime;
    private int grams;
    private double calories;
    private double fats;
    private double carbohydrates;
    private double proteins;
    private long foodId;
    private String foodName;
    private Food food;

    public Meal(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMealtime() {
        return mealtime;
    }

    public void setMealtime(int mealtime) {
        this.mealtime = mealtime;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

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
        stringBuilder.append("foodName=" + this.getFoodName());
        //stringBuilder.append("food=" + this.getFood().getName() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
