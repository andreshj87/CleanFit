package com.zireck.projectk.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zireck.projectk.presentation.enumeration.Mealtime;
import com.zireck.projectk.presentation.util.DateUtils;

import java.util.Date;

/**
 * Class that represents a meal in the presentation layer.
 */
public class MealModel implements Parcelable {

    private long id;
    private Date date;
    private int mealtime;
    private int grams;
    private double calories;
    private double fats;
    private double carbohydrates;
    private double proteins;
    private long foodId;
    private FoodModel foodModel;

    public MealModel() {

    }

    public MealModel(long id) {
        this.id = id;
    }

    protected MealModel(Parcel in) {
        id = in.readLong();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        mealtime = in.readInt();
        grams = in.readInt();
        calories = in.readDouble();
        fats = in.readDouble();
        carbohydrates = in.readDouble();
        proteins = in.readDouble();
        foodId = in.readLong();
        foodModel = (FoodModel) in.readValue(FoodModel.class.getClassLoader());
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

    public FoodModel getFoodModel() {
        return foodModel;
    }

    public void setFoodModel(FoodModel foodModel) {
        this.foodModel = foodModel;
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
        stringBuilder.append("foodModel=" + this.getFoodModel().getName() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

    /**
     * Calculate the amount of calories and nutrients of this meal.
     */
    public void calculateEnergyAndNutrients() {
        if (this.getFoodModel() == null) {
            throw new NullPointerException("Food not set");
        }

        if (this.getGrams() <= 0) {
            throw new IllegalStateException("Invalid grams");
        }

        this.calculateCalories();
        this.calculateFats();
        this.calculateCarbohydrates();
        this.calculateProteins();
    }

    private void calculateCalories() {
        this.setCalories((this.getGrams() * this.getFoodModel().getCalories()) / 100);
    }

    private void calculateFats() {
        this.setFats((this.getGrams() * this.getFoodModel().getFats()) / 100);
    }

    private void calculateCarbohydrates() {
        this.setCarbohydrates((this.getGrams() * this.getFoodModel().getCarbohydrates()) / 100);
    }

    private void calculateProteins() {
        this.setProteins((this.getGrams() * this.getFoodModel().getProteins()) / 100);
    }

    public String getFormattedDate() {
        return DateUtils.getFormattedMealDate(this.getDate());
    }

    public String getFormattedMealtime() {
        return Mealtime.values()[this.getMealtime()].getStringValue();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeInt(mealtime);
        dest.writeInt(grams);
        dest.writeDouble(calories);
        dest.writeDouble(fats);
        dest.writeDouble(carbohydrates);
        dest.writeDouble(proteins);
        dest.writeLong(foodId);
        dest.writeValue(foodModel);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MealModel> CREATOR = new Parcelable.Creator<MealModel>() {
        @Override
        public MealModel createFromParcel(Parcel in) {
            return new MealModel(in);
        }

        @Override
        public MealModel[] newArray(int size) {
            return new MealModel[size];
        }
    };
}
