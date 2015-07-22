package com.zireck.projectk.presenter;

import com.zireck.projectk.interactor.FoodListInteractor;
import com.zireck.projectk.interactor.FoodListInteractorImpl;
import com.zireck.projectk.listener.OnFoodListFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.view.FoodListView;

import java.util.List;

/**
 * Created by Zireck on 22/07/2015.
 */
public class FoodListPresenterImpl implements FoodListPresenter, OnFoodListFinishedListener {

    protected FoodListView mView;
    protected FoodListInteractor mInteractor;

    public FoodListPresenterImpl(FoodListView view) {
        mView = view;
        loadInteractor();
    }

    @Override
    public void onResume() {
        if (mView.getCurrentTag() == mView.getFoodTag()) {
            mInteractor.retrieveFoodRepository(this);
        } else if (mView.getCurrentTag() == mView.getDrinkTag()) {
            mInteractor.retrieveDrinkRepository(this);
        }
    }

    @Override
    public void onFinished(List<Food> items) {
        mView.setFoodItems(items);
    }

    private void loadInteractor() {
        mInteractor = new FoodListInteractorImpl();
    }
}
