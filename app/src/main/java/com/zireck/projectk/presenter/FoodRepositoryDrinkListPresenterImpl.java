package com.zireck.projectk.presenter;

import com.zireck.projectk.interactor.FoodRepositoryDrinkListInteractorImpl;
import com.zireck.projectk.view.FoodRepositoryListView;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryDrinkListPresenterImpl extends FoodRepositoryListPresenterImpl {

    public FoodRepositoryDrinkListPresenterImpl(FoodRepositoryListView view) {
        super(view);
    }

    @Override
    public void loadInteractor() {
        mInteractor = new FoodRepositoryDrinkListInteractorImpl();
    }
}
