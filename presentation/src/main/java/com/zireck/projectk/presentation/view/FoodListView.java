package com.zireck.projectk.presentation.view;

import com.zireck.projectk.presentation.model.FoodModel;

import java.util.Collection;

/**
 * Created by Zireck on 22/07/2015.
 */
public interface FoodListView {
    public void renderFoodList(Collection<FoodModel> foodItems);
    public int getCurrentTag();
    public int getFoodTag();
    public int getDrinkTag();
    public int getRecentTag();
}
