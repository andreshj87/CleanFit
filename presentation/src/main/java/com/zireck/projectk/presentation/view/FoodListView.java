package com.zireck.projectk.presentation.view;

import com.zireck.projectk.presentation.model.FoodModel;

import java.util.Collection;

/**
 * Created by Zireck on 22/07/2015.
 */
public interface FoodListView extends View {
    void renderFoodList(Collection<FoodModel> foodItems);
    void navigateToFoodDetails(final int position);
}
