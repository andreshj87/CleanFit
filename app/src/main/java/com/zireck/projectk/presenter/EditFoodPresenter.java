package com.zireck.projectk.presenter;

import android.os.Bundle;

/**
 * Created by Zireck on 31/07/2015.
 */
public interface EditFoodPresenter extends AddFoodPresenter {
    public void mapExtras(Bundle bundle);
    public void getFood();
    public void loadPicture();
    public void doNotReceivePicture();
}
