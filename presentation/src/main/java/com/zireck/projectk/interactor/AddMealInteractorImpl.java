package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnAddMealInteractorFinishedListener;
import com.zireck.projectk.util.GreenDaoUtils;

/**
 * Created by Zireck on 06/08/2015.
 */
public class AddMealInteractorImpl implements AddMealInteractor {

    @Override
    public void getFoods(OnAddMealInteractorFinishedListener listener) {
        listener.onGetFoodsFinished(GreenDaoUtils.getFoodDao().loadAll());
    }
}
