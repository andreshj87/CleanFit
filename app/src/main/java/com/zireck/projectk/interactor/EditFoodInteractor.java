package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnEditFoodInteractorFinishedListener;

/**
 * Created by Zireck on 31/07/2015.
 */
public interface EditFoodInteractor {
    public void getFood(OnEditFoodInteractorFinishedListener listener, long foodId);
}
