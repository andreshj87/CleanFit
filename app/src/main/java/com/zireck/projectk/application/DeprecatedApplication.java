package com.zireck.projectk.application;

import android.database.sqlite.SQLiteDatabase;

import com.zireck.projectk.model.DaoMaster;
import com.zireck.projectk.model.DaoSession;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;

import java.util.Date;
import java.util.List;

/**
 * Created by Zireck on 14/07/2015.
 */
@Deprecated
public class DeprecatedApplication {

    public static void main(String args[]) {



        /*
        Food leche = new Food();
        leche.setName("Leche");
        leche.setBrand("Puleva");
        leche.getDefaultNutrients().setCalories(152);
        leche.getDefaultNutrients().setFats(21);
        leche.getDefaultNutrients().setCarbohydrates(30);
        leche.getDefaultNutrients().setProteins(40);

        Food cereales = new Food();
        cereales.setName("Cereales Fitness");
        cereales.setBrand("Nestle");
        cereales.getDefaultNutrients().setCalories(210);
        cereales.getDefaultNutrients().setFats(18);
        cereales.getDefaultNutrients().setCarbohydrates(42);
        cereales.getDefaultNutrients().setProteins(25);

        Food apple = new Food();
        apple.setId(1);
        apple.setName("Apple");
        apple.setBrand("Hacendado");
        apple.getDefaultNutrients().setCalories(50);
        apple.getDefaultNutrients().setFats(5);
        apple.getDefaultNutrients().setCarbohydrates(25);
        apple.getDefaultNutrients().setProteins(30);

        System.out.println(apple.toString());

        Meal firstBreakfast = new Meal(cereales);
        firstBreakfast.setGrams(180);
        firstBreakfast.setMealtime(Mealtime.BREAKFAST);
        firstBreakfast.setDate(new Date());

        Meal secondBreakfast = new Meal(leche);
        secondBreakfast.setGrams(200);
        secondBreakfast.setMealtime(Mealtime.BREAKFAST);
        secondBreakfast.setDate(new Date());

        Meal snack = new Meal(apple);
        snack.setGrams(50);
        snack.setMealtime(Mealtime.SNACK);
        snack.setDate(new Date());

        Day day = new Day(new Date());
        day.addMeal(firstBreakfast);
        day.addMeal(secondBreakfast);
        day.addMeal(snack);

        day.calculateGrams();
        day.calculateNutrients();

        System.out.println("total daily grams = " + day.getGrams());
        System.out.println("total daily calories = " + day.getNutrients().getCalories());

        List<Meal> breakfasts = day.getMeals(Mealtime.BREAKFAST);
        for (Meal meal : breakfasts) {
            System.out.println("Breakfast item = " + meal.getFood().getName() + " grams = " + meal.getGrams());
        }
        */
    }
}
