package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnAddMealInteractorFinishedListener;

/**
 * Created by Zireck on 06/08/2015.
 */
public interface AddMealInteractor {
    public void getFoods(OnAddMealInteractorFinishedListener listener);
}
