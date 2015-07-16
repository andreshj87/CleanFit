package com.zireck.projectk.presenter;

import com.zireck.projectk.interactor.FoodRepositoryDrinkInteractorImpl;
import com.zireck.projectk.view.FoodRepositoryView;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryDrinkPresenterImpl extends FoodRepositoryPresenterImpl {

    public FoodRepositoryDrinkPresenterImpl(FoodRepositoryView view) {
        super(view);
    }

    @Override
    public void loadInteractor() {
        mInteractor = new FoodRepositoryDrinkInteractorImpl();
    }
}
