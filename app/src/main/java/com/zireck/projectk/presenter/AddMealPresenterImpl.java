package com.zireck.projectk.presenter;

import com.zireck.projectk.interactor.AddMealInteractor;
import com.zireck.projectk.listener.OnAddMealInteractorFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.view.AddMealView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Zireck on 06/08/2015.
 */
public class AddMealPresenterImpl implements AddMealPresenter, OnAddMealInteractorFinishedListener {

    private AddMealView mView;
    @Inject AddMealInteractor mInteractor;

    @Inject
    public AddMealPresenterImpl(AddMealView view, AddMealInteractor interactor) {
        mView = view;
        mInteractor = interactor;

        mInteractor.getFoods(this);
    }

    @Override
    public void onGetFoodsFinished(List<Food> foods) {
        // TODO populate spinner
        mView.setSpinnerFoodItems(foods);
    }
}
