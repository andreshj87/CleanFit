package com.zireck.calories.presentation.view;

import com.zireck.calories.presentation.model.FoodModel;

import java.util.Collection;

/**
 * Created by Zireck on 22/07/2015.
 */
public interface FoodListView extends View {
    void renderFoodList(Collection<FoodModel> foodItems);
    void navigateToFoodDetails(final int position);
}
