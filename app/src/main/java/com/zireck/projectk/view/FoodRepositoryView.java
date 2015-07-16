package com.zireck.projectk.view;

import com.zireck.projectk.model.DaoSession;
import com.zireck.projectk.model.Food;

import java.util.List;

/**
 * Created by Zireck on 16/07/2015.
 */
public interface FoodRepositoryView {
    public void setFoodItems(List<Food> foodItems);
}
