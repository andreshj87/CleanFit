package com.zireck.projectk.fragment;

import com.zireck.projectk.presenter.FoodRepositoryDrinkListPresenterImpl;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryDrinkListFragment extends FoodRepositoryListBaseFragment {
    @Override
    public void loadPresenter() {
        mPresenter = new FoodRepositoryDrinkListPresenterImpl(this);
    }
}
