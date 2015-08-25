package com.zireck.projectk.presentation.presenter;

import android.support.annotation.NonNull;

import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.presentation.mapper.MealModelDataMapper;
import com.zireck.projectk.presentation.model.Day;
import com.zireck.projectk.presentation.view.HomeView;
import com.zireck.projectk.presentation.view.View;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 25/08/2015.
 */
public class HomePresenter implements Presenter {

    private HomeView mView;
    private Interactor mGetMealListForDate;
    private MealModelDataMapper mMealModelDataMapper;

    private Day mDay;

    @Inject
    public HomePresenter(@Named("mealListForDate") Interactor getMealListForDate, MealModelDataMapper mealModelDataMapper) {
        mGetMealListForDate = getMealListForDate;
        mMealModelDataMapper = mealModelDataMapper;

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        mDay = new Day(now.getTime());
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((HomeView) view);
    }

    @Override
    public void resume() {
        mGetMealListForDate.execute(new GetMealListForDateSubscriber());
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mGetMealListForDate.unsubscribe();
    }

    private final class GetMealListForDateSubscriber extends DefaultSubscriber<List<Meal>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Meal> meals) {
            mDay.setMeals(mMealModelDataMapper.transform(meals));
            mDay.toString();
        }
    }
}
