package com.zireck.projectk.presenter;

/**
 * Created by Zireck on 24/07/2015.
 */
public interface AddFoodPresenter {
    public void validateData(String name, String brand, boolean isDrink, String calories, String fats, String carboydrates, String proteins);
    public void isDrink(boolean isDrink);
}
