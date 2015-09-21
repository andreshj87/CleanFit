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

    private Date mFirstDate;
    private Date mLastDate;
    private MealRepository mMealRepository;

    @Inject
    public GetMealListForDate(MealRepository mealRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMealRepository = mealRepository;
    }

    public void setDate(Date firstDate, Date lastDate) {
        if (firstDate == null || lastDate == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }

        mFirstDate = firstDate;
        mLastDate = lastDate;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mMealRepository.meals(mFirstDate, mLastDate);
    }
}
