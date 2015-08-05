package com.zireck.projectk.dagger;

import android.content.Context;

import com.zireck.projectk.fragment.AddFoodFragment;
import com.zireck.projectk.interactor.AddFoodInteractor;
import com.zireck.projectk.presenter.AddFoodPresenter;
import com.zireck.projectk.presenter.AddFoodPresenterImpl;
import com.zireck.projectk.view.AddFoodView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 03/08/2015.
 */
@Module(
        injects = AddFoodFragment.class,
        addsTo = ActivityModule.class,
        complete = false
)
public class AddFoodModule {

    private AddFoodView mView;

    public AddFoodModule(AddFoodView view) {
        mView = view;
    }

    @Provides @Singleton
    public AddFoodView provideView() {
        return mView;
    }

    @Provides @Singleton
    public AddFoodPresenter providePresenter(Context context, AddFoodView view, AddFoodInteractor interactor) {
        return new AddFoodPresenterImpl(context, view, interactor);
    }
}
