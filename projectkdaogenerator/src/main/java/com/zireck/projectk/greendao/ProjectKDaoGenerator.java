package com.zireck.projectk.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class ProjectKDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.zireck.projectk.model");

        Entity food = schema.addEntity("Food");
        food.setSuperclass("FoodBase");
        food.addIdProperty().autoincrement().primaryKey();
        food.addStringProperty("name").notNull();
        food.addStringProperty("brand").notNull();
        food.addBooleanProperty("isDrink").notNull();
        food.addDoubleProperty("calories").notNull();
        food.addDoubleProperty("fats").notNull();
        food.addDoubleProperty("carbohydrates").notNull();
        food.addDoubleProperty("proteins").notNull();

        Entity meal = schema.addEntity("Meal");
        meal.setSuperclass("MealBase");
        meal.addIdProperty().autoincrement().primaryKey();
        meal.addDateProperty("date").notNull();
        meal.addIntProperty("mealtime").notNull();
        meal.addIntProperty("grams").notNull();
        meal.addDoubleProperty("calories").notNull();
        meal.addDoubleProperty("fats").notNull();
        meal.addDoubleProperty("carbohydrates").notNull();
        meal.addDoubleProperty("proteins").notNull();

        Property foodId = meal.addLongProperty("foodId").notNull().getProperty();
        meal.addToOne(food, foodId);

        /**
         *     private String name;
         private Gender gender;
         private int age;
         private MeasurementSystem measurementSystem;
         private double weight;
         private int height;
         private ActivityFactor activityFactor;
         private double bmr;
         private Goal goal;
         private double maintain;
         private double burn;
         private double gain;
         */
        Entity user = schema.addEntity("User");
        user.setSuperclass("UserBase");
        user.addStringProperty("name");
        user.addIntProperty("gender").notNull();
        user.addDateProperty("birthday").notNull();
        user.addIntProperty("age");
        user.addIntProperty("measurementSystem").notNull();
        user.addDoubleProperty("weight").notNull();
        user.addIntProperty("height").notNull();
        user.addIntProperty("activityFactor").notNull();
        user.addDoubleProperty("bmr");
        user.addIntProperty("goal").notNull();
        user.addDoubleProperty("maintain");
        user.addDoubleProperty("burn");
        user.addDoubleProperty("gain");


        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }
}
