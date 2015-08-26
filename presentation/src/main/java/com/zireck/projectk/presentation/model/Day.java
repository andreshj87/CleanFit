package com.zireck.projectk.presentation.model;

import com.zireck.projectk.presentation.util.DateUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Zireck on 25/08/2015.
 */
public class Day {

    private final Date mDate;
    private Collection<MealModel> mMeals;

    private double mCalories;
    private double mFats;
    private double mCarbohydrates;
    private double mProteins;

    public Day(Date date) {
        mDate = date;
        mMeals = new ArrayList<MealModel>();
    }

    public Date getDate() {
        return mDate;
    }

    public Collection<MealModel> getMeals() {
        return mMeals;
    }

    public void setMeals(Collection<MealModel> meals) {
        mMeals = meals;
    }

    public void addMeal(MealModel meal) {
        if (mMeals == null) {
            throw new NullPointerException("Meal list cannot be null.");
        }

        mMeals.add(meal);
    }

    public double getCalories() {
        return mCalories;
    }

    public double getFats() {
        return mFats;
    }

    public double getCarbohydrates() {
        return mCarbohydrates;
    }

    public double getProteins() {
        return mProteins;
    }

    public void calculateEnergyAndNutrients() {
        if (mMeals == null) {
            throw new NullPointerException("Meal list cannot be null.");
        }

        calculateEnergy();
        calculateNutrients();
    }

    private void calculateEnergy() {
        mCalories = 0;

        for (MealModel meal : mMeals) {
            mCalories += meal.getCalories();
        }
    }

    private void calculateNutrients() {
        mFats = 0;
        mCarbohydrates = 0;
        mProteins = 0;

        for (MealModel meal : mMeals) {
            mFats += meal.getFats();
            mCarbohydrates += meal.getCarbohydrates();
            mProteins += meal.getProteins();
        }
    }

    @Deprecated
    @Override
    public String toString() {
        System.out.println("For day " + DateUtils.getFormattedDayDate(mDate) + " we have the following meals: ");
        for (MealModel meal : mMeals) {
            System.out.println(meal.getFoodModel().getName() + " -> " + meal.getGrams() + " in date: " + meal.getDate().toString());
        }

        return super.toString();
    }
}
