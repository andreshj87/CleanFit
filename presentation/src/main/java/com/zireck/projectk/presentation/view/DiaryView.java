package com.zireck.projectk.presentation.view;

import com.zireck.projectk.presentation.model.Day;

import java.util.Collection;

/**
 * Created by Zireck on 22/09/2015.
 */
public interface DiaryView extends View {
    void navigateToSettings();
    void renderDaysInView(Collection<Day> days);
}
