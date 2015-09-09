package com.zireck.projectk.presentation.view.fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.dlazaro66.wheelindicatorview.WheelIndicatorItem;
import com.dlazaro66.wheelindicatorview.WheelIndicatorView;
import com.txusballesteros.widgets.FitChart;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.presenter.HomePresenter;
import com.zireck.projectk.presentation.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Zireck on 06/08/2015.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @Inject HomePresenter mPresenter;

    @Bind(R.id.fit_chart) FitChart mFitChart;
    @Bind(R.id.wheel_indicator_view) WheelIndicatorView mWheelIndicatorView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();

        initFitChart();
        initWheelIndicatorView();
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

    private void initFitChart() {
        mFitChart.setMinValue(0f);
        mFitChart.setMaxValue(100f);

        mFitChart.setValue(80f);
    }

    private void initWheelIndicatorView() {

        List<WheelIndicatorItem> items = new ArrayList<>();
        WheelIndicatorItem item1 = new WheelIndicatorItem(15, Color.BLUE);
        items.add(item1);
        WheelIndicatorItem item2 = new WheelIndicatorItem(30, Color.GREEN);
        items.add(item2);

        mWheelIndicatorView.setWheelIndicatorItems(items);
        mWheelIndicatorView.startItemsAnimation();

    }
}
