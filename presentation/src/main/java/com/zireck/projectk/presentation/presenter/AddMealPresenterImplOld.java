package com.zireck.projectk.presentation.presenter;

import com.zireck.projectk.presentation.enumeration.Mealtime;
import com.zireck.projectk.presentation.interactor.AddMealInteractor;
import com.zireck.projectk.presentation.interactor.AddMealInteractorImpl;
import com.zireck.projectk.presentation.listener.OnAddMealInteractorFinishedListener;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.util.DateUtils;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.util.StringUtils;
import com.zireck.projectk.presentation.view.AddMealView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zireck on 06/08/2015.
 */
public class AddMealPresenterImplOld implements AddMealPresenterOld, OnAddMealInteractorFinishedListener {

    private AddMealView mView;
    private AddMealInteractor mInteractor;

    private Date mCurrentDate;
    private Date mCurrentTime;

    private String mCurrentMeasure = "GR";

    public AddMealPresenterImplOld(AddMealView view) {
        mView = view;
        mInteractor = new AddMealInteractorImpl();

        //mInteractor.getFoods(this);
    }

    @Override
    public void onGetFoodsFinished(List<FoodModel> foods) {
        /*if (foods == null) {
            throw new IllegalArgumentException("Foods cannot be null.");
        }*/

        if (foods != null) {
            //mView.setSpinnerFoodItems(foods);
        }
    }

    @Override
    public void initialize() {
        //updateEnergyAndNutrients(mView.getFoodModel(), MathUtils.getAmountFromText(mView.getAmount()));
    }

    @Override
    public void setFood(FoodModel food) {
        if (food == null) {
            setEnergyAndNutrientsToZero();
            return;
        }

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
            mCurrentDate = DateUtils.getDateFromText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
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

        //updateEnergyAndNutrients(mView.getFood(), amount);
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
    public void validateData(FoodModel food, String date, String time, String daily, String amount) {
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

        if (StringUtils.isNullOrEmpty(daily)) {
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

}
