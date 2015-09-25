package com.zireck.projectk.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.domain.User;
import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.DeleteMeal;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.presentation.mapper.MealModelDataMapper;
import com.zireck.projectk.presentation.mapper.UserModelDataMapper;
import com.zireck.projectk.presentation.model.Day;
import com.zireck.projectk.presentation.model.MealModel;
import com.zireck.projectk.presentation.model.UserModel;
import com.zireck.projectk.presentation.view.DiaryView;
import com.zireck.projectk.presentation.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 22/09/2015.
 */
public class DiaryPresenter implements Presenter {

    private DiaryView mView;

    private Interactor mGetUserDetails;
    private Interactor mGetMealList;
    private DeleteMeal mDeleteMeal;

    private UserModelDataMapper mUserModelDataMapper;
    private MealModelDataMapper mMealModelDataMapper;

    private UserModel mUserModel;
    private Collection<MealModel> mMeals;

    @Inject
    public DiaryPresenter(@Named("userDetails") Interactor getUserDetails,
                          @Named("mealList") Interactor getMealList,
                          @Named("deleteMeal") DeleteMeal deleteMeal,
                         UserModelDataMapper userModelDataMapper,
                         MealModelDataMapper mealModelDataMapper) {
        mGetUserDetails = getUserDetails;
        mGetMealList = getMealList;
        mDeleteMeal = deleteMeal;
        mUserModelDataMapper = userModelDataMapper;
        mMealModelDataMapper = mealModelDataMapper;
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((DiaryView) view);
    }

    @Override
    public void resume() {
        mGetUserDetails.execute(new GetUserDetailsSubscriber());
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mGetUserDetails.unsubscribe();
        mGetMealList.unsubscribe();
    }

    private void checkValidUser(UserModel userModel) {
        if (userModel == null) {
            mView.navigateToSettings();
        } else {
            if (userModel.isValid()) {
                mView.setDailyCaloriesGoal(userModel.getGoalCalories());
                retrieveMeals();
            } else {
                mView.navigateToSettings();
            }
        }
    }

    private void retrieveMeals() {
        mGetMealList.execute(new GetMealsSubscriber());
    }

    private void showMealsInView(Collection<MealModel> meals) {
        if (meals != null && !meals.isEmpty()) {
            mView.renderDaysInView(groupMealsInDays(meals));
        } else {
            Log.e(getClass().getSimpleName(), "Meal List cannot be null.");
        }
    }

    private List<Day> groupMealsInDays(Collection<MealModel> meals) {
        List<MealModel> mealModels = ((List<MealModel>) meals);
        Collections.reverse(mealModels);
        List<Day> days = new ArrayList<Day>();

        for (MealModel mealModel : mealModels) {
            Calendar firstCalendar = Calendar.getInstance();
            firstCalendar.setTime(mealModel.getDate());
            firstCalendar.set(Calendar.HOUR, 0);
            firstCalendar.set(Calendar.MINUTE, 0);
            firstCalendar.set(Calendar.SECOND, 0);

            boolean dateFound = false;
            for (Day day : days) {
                Calendar secondCalendar = Calendar.getInstance();
                secondCalendar.setTime(day.getDate());
                secondCalendar.set(Calendar.HOUR, 0);
                secondCalendar.set(Calendar.MINUTE, 0);
                secondCalendar.set(Calendar.SECOND, 0);

                if (firstCalendar.compareTo(secondCalendar) == 0) {
                    day.addMeal(mealModel);
                    dateFound = true;
                    break;
                }
            }

            if (!dateFound) {
                Day newDay = new Day(firstCalendar.getTime());
                newDay.addMeal(mealModel);
                days.add(newDay);
            }
        }

        return days;
    }

    public void deleteMeal(MealModel mealModel) {
        mDeleteMeal.setMeal(mMealModelDataMapper.transformInverse(mealModel));
        mDeleteMeal.execute(new DeleteMealSubscriber());
    }

    private final class GetUserDetailsSubscriber extends DefaultSubscriber<User> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(User user) {
            mUserModel = mUserModelDataMapper.transform(user);
            checkValidUser(mUserModel);
        }
    }

    private final class GetMealsSubscriber extends DefaultSubscriber<List<Meal>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(List<Meal> meals) {
            mMeals = mMealModelDataMapper.transform(meals);
            showMealsInView(mMeals);
        }
    }

    private final class DeleteMealSubscriber extends DefaultSubscriber {

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onCompleted() {
            retrieveMeals();
        }
    }
}
