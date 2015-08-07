package com.zireck.projectk.presenter;

import com.google.common.base.Strings;
import com.zireck.projectk.enums.Mealtime;
import com.zireck.projectk.interactor.AddMealInteractor;
import com.zireck.projectk.listener.OnAddMealInteractorFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.util.DateUtils;
import com.zireck.projectk.util.MathUtils;
import com.zireck.projectk.view.AddMealView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by Zireck on 06/08/2015.
 */
public class AddMealPresenterImpl implements AddMealPresenter, OnAddMealInteractorFinishedListener {

    private AddMealView mView;
    @Inject AddMealInteractor mInteractor;

    private Date mCurrentDate;
    private Date mCurrentTime;

    private String mCurrentMeasure = "GR";

    @Inject
    public AddMealPresenterImpl(AddMealView view, AddMealInteractor interactor) {
        mView = view;
        mInteractor = interactor;

        mInteractor.getFoods(this);

    }

    @Override
    public void onGetFoodsFinished(List<Food> foods) {
        mView.setSpinnerFoodItems(foods);
    }

    @Override
    public void initialize() {
        updateEnergyAndNutrients(mView.getFood(), MathUtils.getAmountFromText(mView.getAmount()));
    }

    @Override
    public void setFood(Food food) {
        if (food == null) {
            setEnergyAndNutrientsToZero();
            return;
        }

        if (food.getIsDrink()) {
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

    @Override
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
            mCurrentDate = DateUtils.getDateFromText(year + "/" + (monthOfYear+1) + "/" + dayOfMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mView.setDateText(date);
    }

    @Override
    public void setTime(int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;

        try {
            mCurrentTime = DateUtils.getTimeFromText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCurrentTime);

        mView.setTimeText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + MathUtils.getTwoDigitsString(calendar.get(Calendar.MINUTE)));
    }

    @Override
    public void setAmount(int amount) {
        mView.setAmountText(amount + mCurrentMeasure.toLowerCase());

        updateEnergyAndNutrients(mView.getFood(), amount);
    }

    @Override
    public Date getCurrentDate() {
        return mCurrentDate;
    }

    @Override
    public Date getCurrentTime() {
        return mCurrentTime;
    }

    @Override
    public String getCurrentMeasure() {
        return mCurrentMeasure;
    }

    @Override
    public void validateData(Food food, String date, String time, String daily, String amount) {
        boolean error = false;

        if (food == null) {
            mView.setFoodError();
            error = true;
        }

        if (Strings.isNullOrEmpty(date)) {
            mView.setDateError();
            error = true;
        }

        if (Strings.isNullOrEmpty(time)) {
            mView.setTimeError();
            error = true;
        }

        if (Strings.isNullOrEmpty(daily)) {
            mView.setDailyError();
            error = true;
        }

        if (Strings.isNullOrEmpty(amount)) {
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

            int actualDaily = Mealtime.getIntValueForString(daily);
            if (actualDaily < 0 || actualDaily >= Mealtime.getValues().length) {
                mView.setDailyError();
                return;
            }

            int actualAmount = MathUtils.getAmountFromText(amount);
            if (actualAmount < 0) {
                mView.setAmountError();
                return;
            }

            // TODO Add Meal
        }
    }

    private void updateEnergyAndNutrients(Food food, int amount) {
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

}
