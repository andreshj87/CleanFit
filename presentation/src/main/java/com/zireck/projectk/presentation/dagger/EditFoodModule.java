package com.zireck.projectk.presentation.dagger;

import android.content.Context;

import com.zireck.projectk.presentation.fragment.EditFoodFragment;
import com.zireck.projectk.presentation.interactor.EditFoodInteractor;
import com.zireck.projectk.presentation.presenter.EditFoodPresenter;
import com.zireck.projectk.presentation.presenter.EditFoodPresenterImpl;
import com.zireck.projectk.presentation.view.EditFoodView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 04/08/2015.
 */
@Module(
        injects = EditFoodFragment.class,
        addsTo = ActivityModule.class,
        complete = false
)
public class EditFoodModule {

    private EditFoodView mView;

    public EditFoodModule(EditFoodView view) {
        mView = view;
    }

    @Provides @Singleton
    public EditFoodView provideView() {
        return mView;
    }

    @Provides @Singleton
    public EditFoodPresenter providePresenter(Context context, EditFoodView view, EditFoodInteractor interactor) {
        return new EditFoodPresenterImpl(context, view, interactor);
    }
}