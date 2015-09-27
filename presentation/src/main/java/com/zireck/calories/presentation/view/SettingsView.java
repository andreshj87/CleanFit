package com.zireck.calories.presentation.view;

import com.zireck.calories.presentation.model.UserModel;

/**
 * Created by Zireck on 11/09/2015.
 */
public interface SettingsView extends View {

    void renderUserData(UserModel userModel);
    void showError(String message);
}
