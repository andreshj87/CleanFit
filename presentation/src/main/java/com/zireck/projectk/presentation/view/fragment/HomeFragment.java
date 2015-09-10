package com.zireck.projectk.presentation.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlazaro66.wheelindicatorview.WheelIndicatorItem;
import com.dlazaro66.wheelindicatorview.WheelIndicatorView;
import com.txusballesteros.widgets.FitChart;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.model.Day;
import com.zireck.projectk.presentation.presenter.HomePresenter;
import com.zireck.projectk.presentation.util.DateUtils;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Zireck on 06/08/2015.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @Inject HomePresenter mPresenter;

    @Bind(R.id.layout_days) LinearLayout mLayoutDays;
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

    @OnClick(R.id.button_meals)
    public void onClickMeals() {
        mPresenter.showMeals();
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

    @Override
    public void renderDays(List<Day> days) {
        // TODO: when should this be called?
        if (days == null || days.size() <= 0) {
            throw new IllegalArgumentException("Day List cannot be null.");
        }

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        for (Day day : days) {
            View dayView = inflater.inflate(R.layout.day_item, mLayoutDays, false);

            TextView dayDate = (TextView) dayView.findViewById(R.id.day_date);
            TextView dayCalories = (TextView) dayView.findViewById(R.id.day_calories);
            View dayCaloriesProgress = (View) dayView.findViewById(R.id.day_calories_progress);
            TextView dayCaloriesPercent = (TextView) dayView.findViewById(R.id.day_calories_percent);

            day.calculateEnergyAndNutrients();

            dayDate.setText(DateUtils.getFormattedDayDate(day.getDate()));
            dayCalories.setText(String.valueOf(MathUtils.betterFormatDouble(day.getCalories())) + " kcal");

            mLayoutDays.addView(dayView);
        }
    }
}
