package com.zireck.calories.presentation.view;

import com.zireck.calories.presentation.model.FoodModel;

import java.util.Collection;

/**
 * Created by Zireck on 16/08/2015.
 */
public interface DrinkListView extends View {
    void renderDrinkList(Collection<FoodModel> drinkItems);
    void navigateToFoodDetails(final int position);
}
