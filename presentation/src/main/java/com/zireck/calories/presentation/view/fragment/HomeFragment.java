package com.zireck.calories.presentation.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txusballesteros.widgets.FitChart;
import com.vstechlab.easyfonts.EasyFonts;
import com.zireck.calories.R;
import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.presentation.model.Day;
import com.zireck.calories.presentation.navigation.Navigator;
import com.zireck.calories.presentation.presenter.HomePresenter;
import com.zireck.calories.presentation.util.DateUtils;
import com.zireck.calories.presentation.util.MathUtils;
import com.zireck.calories.presentation.view.HomeView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Zireck on 06/08/2015.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @Inject
    Navigator mNavigator;

    @Inject HomePresenter mPresenter;

    @Bind(R.id.swipe_layout) SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.layout_days) ViewGroup mLayoutDays;
    @Bind(R.id.fit_chart) FitChart mFitChart;
    //@Bind(R.id.wheel_indicator_view) WheelIndicatorView mWheelIndicatorView;

    @Bind(R.id.fit_chart_main_text) TextView mFitChartMainText;
    @Bind(R.id.fit_chart_goal_text) TextView mFitChartGoalText;

    @Bind(R.id.calories_to_go) TextView mCaloriesToGo;
    @Bind(R.id.calories_eaten) TextView mCaloriesEaten;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();

        //initFitChart();
        //initWheelIndicatorView();
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

        initTextFont();
        initSwipeRefreshLayout();
    }

    private void initTextFont() {
        if (getView() != null && getView().findViewById(R.id.nothing_yet) != null) {
            ((TextView) getView().findViewById(R.id.nothing_yet))
                    .setTypeface(EasyFonts.robotoLight(getActivity()));
        }
    }

    private void initSwipeRefreshLayout() {
        mSwipeLayout.setColorSchemeResources(R.color.primary, R.color.calories);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.refreshData();
                    }
                }, 2500);
            }
        });
    }

    @Deprecated
    private void initFitChart() {
        mFitChart.setMinValue(0f);
        mFitChart.setMaxValue(100f);

        mFitChart.setValue(80f);
    }

    /*
    private void initWheelIndicatorView() {

        List<WheelIndicatorItem> items = new ArrayList<>();
        WheelIndicatorItem item1 = new WheelIndicatorItem(15, Color.BLUE);
        items.add(item1);
        WheelIndicatorItem item2 = new WheelIndicatorItem(30, Color.GREEN);
        items.add(item2);

        mWheelIndicatorView.setWheelIndicatorItems(items);
        mWheelIndicatorView.startItemsAnimation();
    }*/

    @Override
    public void renderDays(List<Day> days) {
        if (days == null || days.size() <= 0) {
            //throw new IllegalArgumentException("Day List cannot be null or empty.");
            return;
        }

        mLayoutDays.removeAllViews();

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

            int realProgress = day.getProgressForGoal(mPresenter.getUserGoalCalories());
            int barProgress = realProgress > 100 ? 100 : realProgress;

            dayCaloriesProgress.setLayoutParams(
                    new LinearLayout.LayoutParams(0, 24, barProgress)
            );
            dayCaloriesPercent.setText(realProgress + "%");

            // TODO: fix height of the last item
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mLayoutDays.addView(dayView, layoutParams);
        }
    }

    @Override
    public void navigateToSettings() {
        mNavigator.openSettingsActivity(getActivity());
    }

    @Override
    public void setTodayData(double maxCalories, double currentValue) {
        mFitChart.setMinValue(0f);
        mFitChart.setMaxValue((float) maxCalories);
        mFitChart.setValue((float) currentValue);

        mFitChartMainText.setText(MathUtils.betterFormatDouble(currentValue) + " kcal");
        mCaloriesEaten.setText(MathUtils.betterFormatDouble(currentValue) + " calories eaten");

        mFitChartGoalText.setText("Your goal is " + MathUtils.betterFormatDouble(maxCalories) + " kcal");

        if (currentValue >= maxCalories) {
            mCaloriesToGo.setText("Goal exceeded by " + MathUtils.betterFormatDouble(currentValue - maxCalories) + " calories");
        } else {
            mCaloriesToGo.setText(MathUtils.betterFormatDouble((maxCalories - currentValue)) + " calories to go");
        }
    }

    @Override
    public void stopRefreshing() {
        mSwipeLayout.setRefreshing(false);
    }
}
