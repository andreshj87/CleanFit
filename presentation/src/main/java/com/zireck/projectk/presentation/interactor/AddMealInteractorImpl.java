package com.zireck.projectk.presentation.interactor;

import com.zireck.projectk.presentation.listener.OnAddMealInteractorFinishedListener;
import com.zireck.projectk.data.util.GreenDaoUtils;

/**
 * Created by Zireck on 06/08/2015.
 */
public class AddMealInteractorImpl implements AddMealInteractor {

    @Override
    public void getFoods(OnAddMealInteractorFinishedListener listener) {
        listener.onGetFoodsFinished(GreenDaoUtils.getFoodDao().loadAll());
    }
}
