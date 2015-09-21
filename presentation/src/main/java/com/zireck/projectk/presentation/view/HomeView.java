package com.zireck.projectk.presentation.view;

import com.zireck.projectk.presentation.model.Day;

import java.util.List;

/**
 * Created by Zireck on 25/08/2015.
 */
public interface HomeView extends View {
    void renderDays(List<Day> days);
    void navigateToSettings();
    void setTodayData(double maxCalories, double currentValue);
}
