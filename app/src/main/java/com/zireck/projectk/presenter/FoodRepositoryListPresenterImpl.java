package com.zireck.projectk.presenter;

import com.zireck.projectk.interactor.FoodRepositoryListInteractor;
import com.zireck.projectk.listener.OnFoodRepositoryListFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.view.FoodRepositoryListView;

import java.util.List;

/**
 * Created by Zireck on 16/07/2015.
 */
public abstract class FoodRepositoryListPresenterImpl implements FoodRepositoryListPresenter, OnFoodRepositoryListFinishedListener {

    protected FoodRepositoryListView mView;
    protected FoodRepositoryListInteractor mInteractor;

    public FoodRepositoryListPresenterImpl(FoodRepositoryListView view) {
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
