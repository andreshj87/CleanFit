package com.zireck.projectk.presentation.presenter;

import android.os.Bundle;

/**
 * Created by Zireck on 29/07/2015.
 */
public interface FoodDetailPresenter {
    public void mapExtras(Bundle bundle);
    public void getFood();
    public void deleteFood();
    public long getFoodId();
}
