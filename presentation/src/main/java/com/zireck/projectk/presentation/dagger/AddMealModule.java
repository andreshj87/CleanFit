package com.zireck.projectk.presentation.dagger;

import android.content.Context;

import com.zireck.projectk.presentation.adapter.FoodSpinnerAdapter;
import com.zireck.projectk.presentation.fragment.AddMealFragment;
import com.zireck.projectk.presentation.interactor.AddMealInteractor;
import com.zireck.projectk.presentation.presenter.AddMealPresenter;
import com.zireck.projectk.presentation.presenter.AddMealPresenterImpl;
import com.zireck.projectk.presentation.view.AddMealView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 06/08/2015.
 */
@Module(
        injects = AddMealFragment.class,
        addsTo = ActivityModule.class,
        library = true,
        complete = false
)
public class AddMealModule {

    private AddMealView mView;

    public AddMealModule(AddMealView view) {
        mView = view;
    }

    @Provides @Singleton
    public AddMealView provideView() {
        return mView;
    }

    @Provides @Singleton
    public AddMealPresenter providePresenter(AddMealView view, AddMealInteractor interactor) {
        return new AddMealPresenterImpl(view, interactor);
    }

    @Provides
    public FoodSpinnerAdapter provideAdapter(Context context) {
        return new FoodSpinnerAdapter(context, FoodSpinnerAdapter.SPINNER_ITEM_LAYOUT);
    }
}
