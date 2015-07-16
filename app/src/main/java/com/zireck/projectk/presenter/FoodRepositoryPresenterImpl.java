package com.zireck.projectk.presenter;

import com.zireck.projectk.interactor.FoodRepositoryInteractor;
import com.zireck.projectk.interactor.FoodRepositoryInteractorImpl;
import com.zireck.projectk.listener.OnFoodRepositoryFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.view.FoodRepositoryView;

import java.util.List;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryPresenterImpl implements FoodRepositoryPresenter, OnFoodRepositoryFinishedListener {

    private FoodRepositoryView mView;
    private FoodRepositoryInteractor mInteractor;

    public FoodRepositoryPresenterImpl(FoodRepositoryView view) {
        mView = view;
        mInteractor = new FoodRepositoryInteractorImpl();
    }

    @Override
    public void onResume() {
        mInteractor.retrieveFoodRepository(this);
    }

    @Override
    public void onFinished(List<Food> foodItems) {
        mView.setFoodItems(foodItems);
    }
}
