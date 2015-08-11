package com.zireck.projectk.presentation.util;

import com.zireck.projectk.data.GreenDaoHelper;
import com.zireck.projectk.data.entity.FoodDao;
import com.zireck.projectk.data.entity.MealDao;

/**
 * Created by Zireck on 03/08/2015.
 */
public class GreenDaoUtils {

    public static GreenDaoHelper getGreenDaoHelper() {
        return new GreenDaoHelper();
    }

    public static FoodDao getFoodDao() {
        return getGreenDaoHelper().getFoodDao();
    }

    public static MealDao getMealDao() {
        return getGreenDaoHelper().getMealDao();
    }
}
