package com.zireck.projectk.presentation.presenter;

import android.support.annotation.NonNull;

import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.domain.User;
import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.GetMealListForDate;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.presentation.mapper.MealModelDataMapper;
import com.zireck.projectk.presentation.mapper.UserModelDataMapper;
import com.zireck.projectk.presentation.model.Day;
import com.zireck.projectk.presentation.model.MealModel;
import com.zireck.projectk.presentation.model.UserModel;
import com.zireck.projectk.presentation.util.DateUtils;
import com.zireck.projectk.presentation.view.HomeView;
import com.zireck.projectk.presentation.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 25/08/2015.
 */
public class HomePresenter implements Presenter {

    private HomeView mView;
    private Interactor mGetUserDetails;
    private GetMealListForDate mGetMealListForDate;
    private Interactor mDeleteAllMeals;

    UserModelDataMapper mUserModelDataMapper;
    private MealModelDataMapper mMealModelDataMapper;

    private UserModel mUserModel;

    private Date mDate;
    private Day mToday;
    private List<Day> mWeek;

    private int mDaysReceived = 0;

    @Inject
    public HomePresenter(@Named("userDetails") Interactor getUserDetails,
                         @Named("mealListForDate") GetMealListForDate getMealListForDate,
                         @Named("deleteAllMeals") Interactor deleteAllMeals,
                         UserModelDataMapper userModelDataMapper,
                         MealModelDataMapper mealModelDataMapper) {
        mGetUserDetails = getUserDetails;
        mGetMealListForDate = getMealListForDate;
        mDeleteAllMeals = deleteAllMeals;
        mUserModelDataMapper = userModelDataMapper;
        mMealModelDataMapper = mealModelDataMapper;
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((HomeView) view);
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
        mGetMealListForDate.unsubscribe();
    }

    @Deprecated
    public void deleteAllMeals() {
        mDeleteAllMeals.execute(new DefaultSubscriber());
    }

    private void checkValidUser(UserModel userModel) {
        if (userModel == null) {
            mView.navigateToSettings();
        } else {
            if (userModel.isValid()) {
                retrieveMeals();
            } else {
                mView.navigateToSettings();
            }
        }
    }

    private void retrieveMeals() {
        getMealsForToday();
        getMealsForWeek();
    }

    private void getMealsForToday() {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        mDate = now.getTime();

        mToday = new Day(mDate);

        Date firstDate = DateUtils.getFirstTimeOfDay(now.getTime());
        Date lastDate = DateUtils.getLastTimeOfDay(now.getTime());

        mGetMealListForDate.setDate(firstDate, lastDate);
        mGetMealListForDate.execute(new GetMealListForTodaySubscriber());
    }

    // TODO: Retrieve days in order, possibly using some RxJava merging system magic
    private void getMealsForWeek() {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        now.add(Calendar.DAY_OF_MONTH, -1);

        mWeek = new ArrayList<Day>();
        for (int i=0; i<7; i++) {
            Date firstDate = DateUtils.getFirstTimeOfDay(now.getTime());
            Date lastDate = DateUtils.getLastTimeOfDay(now.getTime());
            mGetMealListForDate.setDate(firstDate, lastDate);
            mGetMealListForDate.execute(new GetMealListForWeekSubscriber());

            now.add(Calendar.DAY_OF_MONTH, -1);
        }
    }

    private void renderWeekWhenPossible() {
        mDaysReceived++;
        if (mDaysReceived >= 7) {
            mDaysReceived = 0;
            mView.renderDays(mWeek);
        }
    }

    public double getUserGoalCalories() {
        return (mUserModel != null && mUserModel.isValid()) ? mUserModel.getGoalCalories() : 0;
    }

    private final class GetUserDetailsSubscriber extends DefaultSubscriber<User> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            mView.navigateToSettings();
        }

        @Override
        public void onNext(User user) {
            mUserModel = mUserModelDataMapper.transform(user);
            checkValidUser(mUserModel);

            /*
            if (user != null) {
                mUserModel = mUserModelDataMapper.transform(user);
                checkValidUser(mUserModel);
            } else {
                checkValidUser(null);
            }*/
        }
    }

    private final class GetMealListForTodaySubscriber extends DefaultSubscriber<List<Meal>> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Meal> meals) {
            mToday.setMeals(mMealModelDataMapper.transform(meals));

            if (mUserModel != null && mUserModel.isValid()) {
                mUserModel.calculateAll();

                mToday.calculateEnergyAndNutrients();
                mView.setTodayData(mUserModel.getGoalCalories(), mToday.getCalories());
            } else {
                throw new IllegalStateException("Invalid UserModel");
            }
        }
    }

    private final class GetMealListForWeekSubscriber extends DefaultSubscriber<List<Meal>> {
        @Override
        public void onCompleted() {
            renderWeekWhenPossible();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            renderWeekWhenPossible();
        }

        @Override
        public void onNext(List<Meal> meals) {
            if (meals == null || meals.size() <= 0) {
                //throw new IllegalArgumentException("Meal List cannot be null.");
                return;
            }

            Collection<MealModel> mealModels = mMealModelDataMapper.transform(meals);
            Date date = mealModels.iterator().next().getDate();
            Day day = new Day(date);
            day.setMeals(mealModels);
            mWeek.add(day);
        }
    }
}
