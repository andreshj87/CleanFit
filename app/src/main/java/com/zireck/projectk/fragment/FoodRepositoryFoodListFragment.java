package com.zireck.projectk.fragment;

import com.zireck.projectk.presenter.FoodRepositoryFoodListPresenterImpl;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryFoodListFragment extends FoodRepositoryListBaseFragment {

    public static FoodRepositoryFoodListFragment newInstance() {
        return new FoodRepositoryFoodListFragment();
    }

    @Override
    public void loadPresenter() {
        mPresenter = new FoodRepositoryFoodListPresenterImpl(this);
    }
}
