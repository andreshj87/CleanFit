package com.zireck.projectk.fragment;

import com.zireck.projectk.presenter.FoodRepositoryFoodListPresenterImpl;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryFoodListFragment extends FoodRepositoryListBaseFragment {
    @Override
    public void loadPresenter() {
        mPresenter = new FoodRepositoryFoodListPresenterImpl(this);
    }
}
