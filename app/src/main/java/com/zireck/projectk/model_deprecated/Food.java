package com.zireck.projectk.model_deprecated;

/**
 * Created by Zireck on 13/07/2015.
 */
public class Food {
    protected int id;
    protected String name;
    protected String brand;
    protected boolean isDrink;
    protected Nutrients defaultNutrients;

    public Food() {
        isDrink = false;
        defaultNutrients = new Nutrients();
    }

    public Food(String name, String brand) {
        this.name = name;
        this.brand = brand;
        isDrink = false;
        defaultNutrients = new Nutrients();
    }

    public Food(String name, String brand, boolean isDrink, Nutrients defaultNutrients) {
        this.name = name;
        this.brand = brand;
        this.isDrink = isDrink;
        this.defaultNutrients = defaultNutrients;
    }

    public Food(int id, String name, String brand, boolean isDrink, Nutrients defaultNutrients) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.isDrink = isDrink;
        this.defaultNutrients = defaultNutrients;
    }

    @Override
    public String toString() {
        super.toString();

        StringBuilder food = new StringBuilder();

        food.append("[id=");
        food.append(String.valueOf(id));
        food.append("] ");
        food.append(name + " - " + brand);
        food.append(" - default nutrients = (c:" + defaultNutrients.getCalories());
        food.append(" f:" + defaultNutrients.getFats());
        food.append(" ch:" + defaultNutrients.getCarbohydrates());
        food.append(" p:" + defaultNutrients.getProteins());
        food.append(")");

        return food.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isDrink() {
        return isDrink;
    }

    public void setIsDrink(boolean isDrink) {
        this.isDrink = isDrink;
    }

    public Nutrients getDefaultNutrients() {
        return defaultNutrients;
    }

    public void setDefaultNutrients(Nutrients defaultNutrients) {
        this.defaultNutrients = defaultNutrients;
    }
}
