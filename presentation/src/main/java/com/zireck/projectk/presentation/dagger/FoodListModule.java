package com.zireck.projectk.presentation.dagger;

import android.content.Context;

import com.zireck.projectk.presentation.adapter.FoodListAdapter;
import com.zireck.projectk.presentation.fragment.FoodListFragment;
import com.zireck.projectk.presentation.helper.Navigator;
import com.zireck.projectk.presentation.interactor.FoodListInteractor;
import com.zireck.projectk.presentation.model.Food;
import com.zireck.projectk.presentation.presenter.FoodListPresenter;
import com.zireck.projectk.presentation.presenter.FoodListPresenterImpl;
import com.zireck.projectk.presentation.view.FoodListView;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 06/08/2015.
 */
@Module(
        injects = FoodListFragment.class,
        addsTo = ActivityModule.class,
        complete = false
)
public class FoodListModule {

    private FoodListView mView;

    public FoodListModule(FoodListView view) {
        mView = view;
    }

    @Provides @Singleton
    public FoodListView provideView() {
        return mView;
    }

    @Provides @Singleton
    public FoodListPresenter providePresenter(Context context, FoodListView view, FoodListInteractor interactor, Navigator navigator) {
        return new FoodListPresenterImpl(context, view, interactor, navigator);
    }

    @Provides
    public FoodListAdapter provideAdapter(Context context) {
        return new FoodListAdapter(context, new ArrayList<Food>(), FoodListAdapter.ITEM_LAYOUT);
    }
}
