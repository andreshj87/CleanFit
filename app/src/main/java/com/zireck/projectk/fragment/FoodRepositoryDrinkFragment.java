package com.zireck.projectk.fragment;

import com.zireck.projectk.presenter.FoodRepositoryDrinkPresenterImpl;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryDrinkFragment extends FoodRepositoryBaseFragment {
    @Override
    public void loadPresenter() {
        mPresenter = new FoodRepositoryDrinkPresenterImpl(this);
    }
}
