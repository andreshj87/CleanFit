package com.zireck.calories.presentation.view;

import com.zireck.calories.presentation.model.Day;

import java.util.Collection;

/**
 * Created by Zireck on 22/09/2015.
 */
public interface DiaryView extends View {
    void navigateToSettings();
    void renderDaysInView(Collection<Day> days);
    void dayListEmpty();
    void setDailyCaloriesGoal(double goal);
}
