package com.zireck.projectk.presenter;

import com.zireck.projectk.interactor.FoodRepositoryFoodListInteractorImpl;
import com.zireck.projectk.view.FoodRepositoryListView;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryFoodListPresenterImpl extends FoodRepositoryListPresenterImpl {

    public FoodRepositoryFoodListPresenterImpl(FoodRepositoryListView view) {
        super(view);
    }

    @Override
    public void loadInteractor() {
        mInteractor = new FoodRepositoryFoodListInteractorImpl();
    }
}
