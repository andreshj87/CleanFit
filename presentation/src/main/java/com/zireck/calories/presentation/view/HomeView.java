package com.zireck.calories.presentation.view;

import com.zireck.calories.presentation.model.Day;

import java.util.List;

/**
 * Created by Zireck on 25/08/2015.
 */
public interface HomeView extends View {
    void renderDays(List<Day> days);
    void navigateToSettings();
    void setTodayData(double maxCalories, double currentValue);
    void stopRefreshing();
}
