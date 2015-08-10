package com.zireck.projectk.dagger;

import android.content.Context;

import com.zireck.projectk.adapter.FoodListAdapter;
import com.zireck.projectk.fragment.FoodListFragment;
import com.zireck.projectk.helper.Navigator;
import com.zireck.projectk.interactor.FoodListInteractor;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.presenter.FoodListPresenter;
import com.zireck.projectk.presenter.FoodListPresenterImpl;
import com.zireck.projectk.view.FoodListView;

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
