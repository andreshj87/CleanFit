package com.zireck.projectk.domain.interactor;

import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.repository.MealRepository;

import java.util.Date;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 25/08/2015.
 */
public class GetMealListForDate extends Interactor {

    private Date mDate;
    private MealRepository mMealRepository;

    @Inject
    public GetMealListForDate(MealRepository mealRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMealRepository = mealRepository;
    }

    public void setDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }

        mDate = date;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mMealRepository.meals(mDate);
    }
}
