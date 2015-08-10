package com.zireck.projectk.model;

/**
 * Created by Zireck on 15/07/2015.
 */
public abstract class FoodBase {

    public abstract Long getId();
    public abstract void setId(Long id);
    public abstract String getName();
    public abstract void setName(String name);
    public abstract String getBrand();
    public abstract void setBrand(String brand);
    public abstract boolean getIsDrink();
    public abstract void setIsDrink(boolean isDrink);
    public abstract double getCalories();
    public abstract void setCalories(double calories);
    public abstract double getFats();
    public abstract void setFats(double fats);
    public abstract double getCarbohydrates();
    public abstract void setCarbohydrates(double carbohydrates);
    public abstract double getProteins();
    public abstract void setProteins(double proteins);

    @Override
    public String toString() {
        super.toString();

        StringBuilder food = new StringBuilder();

        food.append("[");
        food.append(String.valueOf(getId()));
        food.append("] ");
        food.append(getName());
        food.append(" (");
        food.append(getBrand());
        food.append(") Drink?");
        food.append(getIsDrink());
        food.append(". Default Nutrients = (kcal:");
        food.append(String.valueOf(getCalories()));
        food.append(" f:");
        food.append(String.valueOf(getFats()));
        food.append(" ch:");
        food.append(String.valueOf(getCarbohydrates()));
        food.append(" p:");
        food.append(String.valueOf(getProteins()));
        food.append(")");

        return food.toString();
    }

    public double getFatsPercent() {
        return getNutrientPercent(getFats());
    }

    public double getCarbohydratesPercent() {
        return getNutrientPercent(getCarbohydrates());
    }

    public double getProteinsPercent() {
        return getNutrientPercent(getProteins());
    }

    private double getNutrientPercent(double nutrient) {
        if (nutrient <= 0) {
            return 0;
        }

        double nutrientsTotalAmount = getFats() + getCarbohydrates() + getProteins();
        return (nutrient * 100) / nutrientsTotalAmount;
    }
}
