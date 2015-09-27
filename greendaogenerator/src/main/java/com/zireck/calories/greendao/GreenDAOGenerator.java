package com.zireck.calories.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDAOGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.zireck.projectk.data.entity");

        Entity food = addFood(schema);
        addMeal(schema, food);
        addUser(schema);

        new DaoGenerator().generateAll(schema, "../data/src/main/java");
    }

    private static Entity addFood(Schema schema) {
        Entity food = schema.addEntity("FoodEntity");
        food.setSuperclass("FoodEntityBase");
        food.addIdProperty().autoincrement().primaryKey();
        food.addStringProperty("name").notNull();
        food.addStringProperty("brand").notNull();
        food.addBooleanProperty("isDrink").notNull();
        food.addDoubleProperty("calories").notNull();
        food.addDoubleProperty("fats").notNull();
        food.addDoubleProperty("carbohydrates").notNull();
        food.addDoubleProperty("proteins").notNull();
        food.addStringProperty("picture");

        return food;
    }

    private static void addMeal(Schema schema, Entity food) {
        Entity meal = schema.addEntity("MealEntity");
        meal.setSuperclass("MealEntityBase");
        meal.addIdProperty().autoincrement().primaryKey();
        meal.addDateProperty("date").notNull();
        meal.addIntProperty("mealtime").notNull();
        meal.addIntProperty("grams").notNull();
        meal.addDoubleProperty("calories").notNull();
        meal.addDoubleProperty("fats").notNull();
        meal.addDoubleProperty("carbohydrates").notNull();
        meal.addDoubleProperty("proteins").notNull();
        meal.addStringProperty("foodName");

        Property foodId = meal.addLongProperty("foodId").getProperty();
        meal.addToOne(food, foodId);
    }

    private static void addUser(Schema schema) {
        Entity user = schema.addEntity("UserEntity");
        user.setSuperclass("UserEntityBase");
        user.addIdProperty().primaryKey();
        user.addStringProperty("name");
        user.addIntProperty("gender").notNull();
        user.addDateProperty("birthday").notNull();
        user.addIntProperty("age");
        //user.addIntProperty("measurementSystem").notNull();
        user.addDoubleProperty("weight").notNull();
        user.addIntProperty("height").notNull();
        user.addIntProperty("activityFactor").notNull();
        user.addDoubleProperty("bmr");
        user.addIntProperty("goal").notNull();
        user.addDoubleProperty("maintain");
        user.addDoubleProperty("burn");
        user.addDoubleProperty("gain");
    }
}
