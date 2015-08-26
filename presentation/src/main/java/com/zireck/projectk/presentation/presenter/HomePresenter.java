package com.zireck.projectk.presentation.presenter;

import android.support.annotation.NonNull;

import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.GetMealListForDate;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.presentation.mapper.MealModelDataMapper;
import com.zireck.projectk.presentation.model.Day;
import com.zireck.projectk.presentation.view.HomeView;
import com.zireck.projectk.presentation.view.View;

import java.util.Calendar;
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

    @Inject
    public HomePresenter(@Named("mealList") Interactor getMealList, @Named("mealListForDate") GetMealListForDate getMealListForDate, @Named("deleteAllMeals") Interactor deleteAllMeals, MealModelDataMapper mealModelDataMapper) {
        mGetMealList = getMealList;
        mGetMealListForDate = getMealListForDate;
        mDeleteAllMeals = deleteAllMeals;
        mMealModelDataMapper = mealModelDataMapper;

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        mDate = now.getTime();

        mDay = new Day(mDate);
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((HomeView) view);
    }

    @Override
    public void resume() {
        //mGetMealList.execute(new GetMealListForDateSubscriber());



        mGetMealListForDate.setDate(mDate);
        mGetMealListForDate.execute(new GetMealListForDateSubscriber());
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

    private final class GetMealListForDateSubscriber extends DefaultSubscriber<List<Meal>> {
        @Override
        public void onCompleted() {
            System.out.println("k9d3 GetMealListForDate onComplete");
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            System.out.println("k9d3 GetMealListForDate onError");
        }

        @Override
        public void onNext(List<Meal> meals) {
            System.out.println("k9d3 GetMealListForDate onNext");
            mDay.setMeals(mMealModelDataMapper.transform(meals));
            mDay.toString();
        }
    }
}
