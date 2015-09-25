package com.zireck.projectk.presentation.presenter;

import android.support.annotation.NonNull;

import com.zireck.projectk.domain.Food;
import com.zireck.projectk.domain.interactor.AddMeal;
import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.presentation.enumeration.Mealtime;
import com.zireck.projectk.presentation.mapper.FoodModelDataMapper;
import com.zireck.projectk.presentation.mapper.MealModelDataMapper;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.model.MealModel;
import com.zireck.projectk.presentation.util.DateUtils;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.util.StringUtils;
import com.zireck.projectk.presentation.view.AddMealView;
import com.zireck.projectk.presentation.view.View;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 24/08/2015.
 */
public class AddMealPresenter implements Presenter {

    private AddMealView mView;
    private Interactor mGetAllFoodListInteractor;
    private AddMeal mAddMealInteractor;
    private FoodModelDataMapper mFoodModelDataMapper;
    private MealModelDataMapper mMealModelDataMapper;

    private FoodModel mFood;

    private Date mCurrentDate;
    private Date mCurrentTime;

    private String mCurrentDateReadable;
    private String mCurrentTimeReadable;

    private String mCurrentMeasure = "GR";

    @Inject
    public AddMealPresenter(@Named("allFoodList") Interactor getAllFoodListInteractor,
                            @Named("addMeal") AddMeal addMealInteractor,
                            FoodModelDataMapper foodModelDataMapper,
                            MealModelDataMapper mealModelDataMapper) {
        mGetAllFoodListInteractor = getAllFoodListInteractor;
        mAddMealInteractor = addMealInteractor;
        mFoodModelDataMapper = foodModelDataMapper;
        mMealModelDataMapper = mealModelDataMapper;
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((AddMealView) view);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mGetAllFoodListInteractor.unsubscribe();
        mAddMealInteractor.unsubscribe();
    }

    public void initialize() {
        //updateEnergyAndNutrients(mFood, MathUtils.getAmountFromText(mView.getAmount()));
        setEnergyAndNutrientsToZero();
        mGetAllFoodListInteractor.execute(new AllFoodListSubscriber());
    }

    public void setFood(FoodModel food) {
        mFood = food;

        if (food == null) {
            setEnergyAndNutrientsToZero();
            return;
        }

        renderFoodInView(food);

        if (food.isDrink()) {
            mCurrentMeasure = "ML";
            mView.setMl();
            mView.setAmountText(MathUtils.getAmountFromText(mView.getAmount()) + "ml");
        } else {
            mCurrentMeasure = "GR";
            mView.setGr();
            mView.setAmountText(MathUtils.getAmountFromText(mView.getAmount()) + "gr");
        }

        updateEnergyAndNutrients(food, MathUtils.getAmountFromText(mView.getAmount()));
    }

    public FoodModel getFood() {
        return mFood;
    }

    private void renderFoodInView(FoodModel food) {
        mView.renderFoodInView(food);
    }

    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        String date;

        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.YEAR) == year && now.get(Calendar.MONTH) == monthOfYear
                && now.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
            date = "Today";
        } else {

            now.add(Calendar.DAY_OF_YEAR, -1);
            if (now.get(Calendar.YEAR) == year && now.get(Calendar.MONTH) == monthOfYear
                    && now.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                date = "Yesterday";
            } else {
                String monthName = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                date = monthName + " " +  dayOfMonth + ", " + year;
            }

        }

        try {
            mCurrentDate = DateUtils.getDateFromText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mCurrentDateReadable = year + "/" + (monthOfYear+1) + "/" + dayOfMonth;

        mView.setDateText(date);
    }

    public void setTime(int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;

        try {
            mCurrentTime = DateUtils.getTimeFromText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentTime);

        mCurrentTimeReadable = hourOfDay + ":" + minute;

        mView.setTimeText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + MathUtils.getTwoDigitsString(calendar.get(Calendar.MINUTE)));
    }

    public void setAmount(int amount) {
        mView.setAmountText(amount + mCurrentMeasure.toLowerCase());

        updateEnergyAndNutrients(mFood, amount);
    }

    public Date getCurrentDate() {
        return mCurrentDate;
    }

    public String getCurrentDateReadable() {
        return mCurrentDateReadable;
    }

    public Date getCurrentTime() {
        return mCurrentTime;
    }

    public String getCurrentTimeReadable() {
        return mCurrentTimeReadable;
    }

    public String getCurrentMeasure() {
        return mCurrentMeasure;
    }

    public void validateData(FoodModel food, String date, String time, Mealtime daily, String amount) {
        boolean error = false;

        if (food == null) {
            mView.setFoodError();
            error = true;
        }

        if (StringUtils.isNullOrEmpty(date)) {
            mView.setDateError();
            error = true;
        }

        if (StringUtils.isNullOrEmpty(time)) {
            mView.setTimeError();
            error = true;
        }

        if (daily == null) {
            mView.setDailyError();
            error = true;
        }

        if (StringUtils.isNullOrEmpty(amount)) {
            mView.setAmountError();
            error = true;
        }

        if (!error) {
            Date actualDate;
            try {
                actualDate = DateUtils.getDateTimeFromText(date + " " + time);
            } catch (ParseException e) {
                mView.setDateError();
                mView.setTimeError();
                e.printStackTrace();
                return;
            }

            int actualAmount = MathUtils.getAmountFromText(amount);
            if (actualAmount < 0) {
                mView.setAmountError();
                return;
            }

            MealModel meal = new MealModel();
            meal.setDate(actualDate);
            meal.setMealtime(daily.getIntValue());
            meal.setGrams(actualAmount);

            meal.setFoodId(food.getId());
            meal.setFoodModel(food);

            meal.calculateEnergyAndNutrients();

            addMeal(meal);
        }
    }

    private void addMeal(MealModel meal) {
        mAddMealInteractor.setMeal(mMealModelDataMapper.transformInverse(meal));
        mAddMealInteractor.execute(new AddMealSubscriber());
    }

    private void updateEnergyAndNutrients(FoodModel food, int amount) {
        if (food == null || amount < 0) {
            setEnergyAndNutrientsToZero();
            return;
        }

        double calories = getAmountForEnergyOrNutrient(amount, food.getCalories());
        double fats = getAmountForEnergyOrNutrient(amount, food.getFats());
        double carbohydrates = getAmountForEnergyOrNutrient(amount, food.getCarbohydrates());
        double proteins = getAmountForEnergyOrNutrient(amount, food.getProteins());

        mView.setCaloriesText(String.valueOf(MathUtils.formatDouble(calories)) + " kcal");
        mView.setFatsText(String.valueOf(MathUtils.betterFormatDouble(fats)) + " fats");
        mView.setCarbohydratesText(String.valueOf(MathUtils.betterFormatDouble(carbohydrates)) + " carbs");
        mView.setProteinsText(String.valueOf(MathUtils.betterFormatDouble(proteins)) + " proteins");
    }

    private void setEnergyAndNutrientsToZero() {
        mView.setCaloriesText("0 kcal");
        mView.setFatsText("0 fats");
        mView.setCarbohydratesText("0 carbs");
        mView.setProteinsText("0 proteins");
    }

    private double getAmountForEnergyOrNutrient(int initialAmount, double energyOrNutrient) {
        return ((initialAmount * energyOrNutrient) / 100);
    }

    private final class AllFoodListSubscriber extends DefaultSubscriber<List<Food>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Food> foods) {
            mView.setFoodItems(mFoodModelDataMapper.transform(foods));
        }
    }

    private final class AddMealSubscriber extends DefaultSubscriber {

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onCompleted() {
            mView.mealSuccessfullyAdded();
        }
    }
}
