package com.zireck.projectk.model_deprecated;

/**
 * Created by Zireck on 14/07/2015.
 */
public class Day {
    /*
    private Date date;
    private List<Meal> meals;
    private int grams;
    private Nutrients nutrients;

    public Day() {
        meals = new ArrayList<Meal>();
    }

    public Day(Date date) {
        this.date = date;
        meals = new ArrayList<Meal>();
    }

    public Day(Date date, List<Meal> meals) {
        this.date = date;
        this.meals = meals;
    }

    public void calculateGrams() {
        if (meals == null) {
            throw new NullPointerException("Meals not found");
        }

        grams = 0;
        for (Meal meal : meals) {
            grams += meal.getGrams();
        }
    }

    public void calculateNutrients() {
        if (meals == null) {
            throw new NullPointerException("Meals not found");
        }

        if (nutrients == null) {
            nutrients = new Nutrients();
        }

        for (Meal meal : meals) {
            nutrients.setCalories(nutrients.getCalories() + meal.getNutrients().getCalories());
            nutrients.setFats(nutrients.getFats() + meal.getNutrients().getFats());
            nutrients.setCarbohydrates(nutrients.getCarbohydrates() + meal.getNutrients().getCarbohydrates());
            nutrients.setProteins(nutrients.getProteins() + meal.getNutrients().getProteins());
        }
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        return DateUtils.getFormattedDayDate(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public void addMeal(Meal meal) {
        if (meals == null) {
            meals = new ArrayList<Meal>();
        }

        meals.add(meal);
    }

    public List<Meal> getMeals(Mealtime mealtime) {
        if (meals == null) {
            throw new NullPointerException("Meals not found");
        }

        List<Meal> mealtimeMeals = new ArrayList<Meal>();

        for (Meal meal : meals) {
            if (meal.getMealtime().getMealtime().equals(mealtime.getMealtime())) {
                mealtimeMeals.add(meal);
            }
        }

        return mealtimeMeals;
    }

    public int getGrams() {
        return grams;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    */
}
