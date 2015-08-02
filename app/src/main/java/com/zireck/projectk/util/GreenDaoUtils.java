package com.zireck.projectk.util;

import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.model.GreenDaoHelper;
import com.zireck.projectk.model.MealDao;

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
