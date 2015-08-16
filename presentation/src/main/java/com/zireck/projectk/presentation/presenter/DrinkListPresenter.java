package com.zireck.projectk.presentation.presenter;

import android.support.annotation.NonNull;

import com.zireck.projectk.domain.Food;
import com.zireck.projectk.domain.interactor.DefaultSubscriber;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.presentation.dagger.PerActivity;
import com.zireck.projectk.presentation.mapper.FoodModelDataMapper;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.view.DrinkListView;
import com.zireck.projectk.presentation.view.View;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 16/08/2015.
 */
@PerActivity
public class DrinkListPresenter implements Presenter {

    private DrinkListView mView;

    private final Interactor mGetDrinkListInteractor;
    private final FoodModelDataMapper mFoodModelDataMapper;

    @Inject
    public DrinkListPresenter(@Named("drinkList") Interactor getDrinkListInteractor,
                             FoodModelDataMapper foodModelDataMapper) {
        mGetDrinkListInteractor = getDrinkListInteractor;
        mFoodModelDataMapper = foodModelDataMapper;
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((DrinkListView) view);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mGetDrinkListInteractor.unsubscribe();
    }

    public void initialize() {
        getDrinkList();
    }

    private void getDrinkList() {
        mGetDrinkListInteractor.execute(new DrinkListSubscriber());
    }

    private void showFoodsCollectionInView(Collection<Food> foodsCollection) {
        final Collection<FoodModel> foodModelsCollection =
                mFoodModelDataMapper.transform(foodsCollection);
        mView.renderDrinkList(foodModelsCollection);
    }

    public void onItemClick(int position) {
        mView.navigateToFoodDetails(position);
    }

    private final class DrinkListSubscriber extends DefaultSubscriber<List<Food>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Food> foods) {
            showFoodsCollectionInView(foods);
        }
    }
}
