package com.zireck.projectk.presentation.presenter;

import android.support.annotation.NonNull;

import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.GetMealListForDate;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.presentation.mapper.MealModelDataMapper;
import com.zireck.projectk.presentation.model.Day;
import com.zireck.projectk.presentation.model.MealModel;
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
    private Interactor mGetMealList;
    private GetMealListForDate mGetMealListForDate;
    private Interactor mDeleteAllMeals;
    private MealModelDataMapper mMealModelDataMapper;

    private Date mDate;
    private Day mDay;
    private List<Day> mWeek;

    @Inject
    public HomePresenter(@Named("mealList") Interactor getMealList, @Named("mealListForDate") GetMealListForDate getMealListForDate, @Named("deleteAllMeals") Interactor deleteAllMeals, MealModelDataMapper mealModelDataMapper) {
        mGetMealList = getMealList;
        mGetMealListForDate = getMealListForDate;
        mDeleteAllMeals = deleteAllMeals;
        mMealModelDataMapper = mealModelDataMapper;
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((HomeView) view);
    }

    @Override
    public void resume() {
        //mGetMealList.execute(new GetMealListForDateSubscriber());

        //mGetMealListForDate.setDate(mDate);
        //mGetMealListForDate.execute(new GetMealListForDateSubscriber());

        //getMealsForToday();
        getMealsForWeek();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mGetMealListForDate.unsubscribe();
    }

    @Deprecated
    public void deleteAllMeals() {
        mDeleteAllMeals.execute(new DefaultSubscriber());
    }

    private void getMealsForToday() {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        mDate = now.getTime();

        mDay = new Day(mDate);
        mGetMealListForDate.setDate(mDate);
        mGetMealListForDate.execute(new GetMealListForTodaySubscriber());
    }

    private void getMealsForWeek() {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        now.add(Calendar.DAY_OF_MONTH, -1);

        mWeek = new ArrayList<Day>();
        for (int i=0; i<7; i++) {
            System.out.println("k9d3 requesting meals for day: " + now.getTime().toString());
            mGetMealListForDate.setDate(now.getTime());
            mGetMealListForDate.execute(new GetMealListForDateSubscriber());

            now.add(Calendar.DAY_OF_MONTH, -1);
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
            mDay.setMeals(mMealModelDataMapper.transform(meals));
            System.out.println("k9d3 Meals for today (" + mDay.getDate().toString() + "): ");
            mDay.toString();
        }
    }

    private final class GetMealListForDateSubscriber extends DefaultSubscriber<List<Meal>> {
        @Override
        public void onCompleted() {
            //System.out.println("k9d3 GetMealListForDate onComplete");
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //System.out.println("k9d3 GetMealListForDate onError");
        }

        @Override
        public void onNext(List<Meal> meals) {
            if (meals == null || meals.size() <= 0) {
                //throw new IllegalArgumentException("Meal List cannot be null.");
                System.out.println("k9d3 Meal List cannot be null");
                return;
            }

            Collection<MealModel> mealModels = mMealModelDataMapper.transform(meals);
            Date date = mealModels.iterator().next().getDate();
            Day day = new Day(date);
            day.setMeals(mealModels);
            mWeek.add(day);

            System.out.println("k9d3 dia añadido a semana: " + day.getDate().toString());
        }
    }

    @Deprecated
    public void showMeals() {
        mView.renderDays(mWeek);

        System.out.println("k9d3 ----- START -----");

        if (mDay != null) {
            System.out.println("k9d3 Meals for today (" + mDay.getDate().toString() + "): ");
            mDay.toString();
        } else {
            System.out.println("k9d3 today=null");
        }

        System.out.println("k9d3 -----");
        System.out.println("k9d3 Weekly meals");
        if (mWeek == null || mWeek.size() <= 0) {
            System.out.println("k9d3 Weekly meals=null or zero");
        } else {
            for (int i=0; i<mWeek.size(); i++) {
                System.out.println("k9d3 Meals for day (" + mWeek.get(i).getDate().toString() + "): ");
                mWeek.get(i).toString();
            }
        }

        System.out.println("k9d3 ----- /THE END -----");
    }
}
