package com.zireck.projectk.presentation.view.fragment;

import android.os.Bundle;

import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.presenter.HomePresenter;
import com.zireck.projectk.presentation.view.HomeView;

import javax.inject.Inject;

/**
 * Created by Zireck on 06/08/2015.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @Inject HomePresenter mPresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    private void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mPresenter.setView(this);
    }
}
