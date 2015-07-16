package com.zireck.projectk.presenter;

import com.zireck.projectk.interactor.FoodRepositoryFoodInteractorImpl;
import com.zireck.projectk.view.FoodRepositoryView;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryFoodPresenterImpl extends FoodRepositoryPresenterImpl {

    public FoodRepositoryFoodPresenterImpl(FoodRepositoryView view) {
        super(view);
    }

    @Override
    public void loadInteractor() {
        mInteractor = new FoodRepositoryFoodInteractorImpl();
    }
}
