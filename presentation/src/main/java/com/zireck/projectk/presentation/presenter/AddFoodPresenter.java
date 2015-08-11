package com.zireck.projectk.presentation.presenter;

import android.content.Context;
import android.widget.EditText;

/**
 * Created by Zireck on 24/07/2015.
 */
public interface AddFoodPresenter {
    public void validateData(String name, String brand, boolean isDrink, String calories, String fats, String carboydrates, String proteins);
    public void isDrink(boolean isDrink);
    public void startCamera(Context context);
    public void receivePicture();
    public void deleteCurrentPicture();
    public void hasFocus(EditText editText);
    public void lostFocus(EditText editText);
}
