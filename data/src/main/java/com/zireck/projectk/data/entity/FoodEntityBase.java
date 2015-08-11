package com.zireck.projectk.data.entity;

/**
 * Created by Zireck on 15/07/2015.
 */
public abstract class FoodEntityBase {

    public abstract Long getId();
    public abstract String getName();
    public abstract String getBrand();
    public abstract boolean getIsDrink();
    public abstract double getCalories();
    public abstract double getFats();
    public abstract double getCarbohydrates();
    public abstract double getProteins();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Food Entity Details *****\n");
        stringBuilder.append("id=" + String.valueOf(this.getId()) + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("brand=" + this.getBrand() + "\n");
        stringBuilder.append("isDrink=" + String.valueOf(this.getIsDrink()) + "\n");
        stringBuilder.append("calories=" + String.valueOf(this.getCalories()) + "\n");
        stringBuilder.append("fats=" + String.valueOf(this.getFats()) + "\n");
        stringBuilder.append("carbohydrates=" + String.valueOf(this.getCarbohydrates()) + "\n");
        stringBuilder.append("proteins=" + String.valueOf(this.getProteins()) + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

}
