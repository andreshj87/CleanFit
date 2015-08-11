package com.zireck.projectk.presentation.presenter;

import android.view.View;

/**
 * Created by Zireck on 22/07/2015.
 */
public interface FoodListPresenter {
    public void onResume();
    public void onItemClick(View view, int position);
}
