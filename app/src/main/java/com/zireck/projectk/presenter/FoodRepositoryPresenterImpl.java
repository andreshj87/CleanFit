package com.zireck.projectk.presenter;

import com.zireck.projectk.interactor.FoodRepositoryInteractor;
import com.zireck.projectk.interactor.FoodRepositoryFoodInteractorImpl;
import com.zireck.projectk.listener.OnFoodRepositoryFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.view.FoodRepositoryView;

import java.util.List;

/**
 * Created by Zireck on 16/07/2015.
 */
public abstract class FoodRepositoryPresenterImpl implements FoodRepositoryPresenter, OnFoodRepositoryFinishedListener {

    protected FoodRepositoryView mView;
    protected FoodRepositoryInteractor mInteractor;

    public FoodRepositoryPresenterImpl(FoodRepositoryView view) {
        mView = view;
        loadInteractor();
    }

    public abstract void loadInteractor();

    @Override
    public void onResume() {
        mInteractor.retrieveFoodRepository(this);
    }

    @Override
    public void onFinished(List<Food> foodItems) {
        mView.setFoodItems(foodItems);
    }
}
