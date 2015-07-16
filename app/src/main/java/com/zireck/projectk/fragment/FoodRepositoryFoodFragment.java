package com.zireck.projectk.fragment;

import com.zireck.projectk.presenter.FoodRepositoryFoodPresenterImpl;
import com.zireck.projectk.presenter.FoodRepositoryPresenterImpl;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryFoodFragment extends FoodRepositoryBaseFragment {
    @Override
    public void loadPresenter() {
        mPresenter = new FoodRepositoryFoodPresenterImpl(this);
    }
}
