package com.zireck.calories.presentation.presenter;

import android.support.annotation.NonNull;

import com.zireck.calories.presentation.dagger.PerActivity;
import com.zireck.calories.presentation.mapper.FoodModelDataMapper;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.view.FoodListView;
import com.zireck.calories.domain.Food;
import com.zireck.calories.presentation.view.View;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 13/08/2015.
 */
@PerActivity
public class FoodListPresenter implements Presenter {

    private FoodListView mView;
    private final com.zireck.calories.domain.interactor.Interactor mGetFoodListInteractor;
    private final FoodModelDataMapper mFoodModelDataMapper;

    @Inject
    public FoodListPresenter(@Named("foodList") com.zireck.calories.domain.interactor.Interactor getFoodListInteractor,
                             FoodModelDataMapper foodModelDataMapper) {
        mGetFoodListInteractor = getFoodListInteractor;
        mFoodModelDataMapper = foodModelDataMapper;
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((FoodListView) view);
    }

    @Override
    public void resume() {
        getFoodList();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mGetFoodListInteractor.unsubscribe();
    }

    private void getFoodList() {
        mGetFoodListInteractor.execute(new FoodListSubscriber());
    }

    private void showFoodsCollectionInView(Collection<com.zireck.calories.domain.Food> foodsCollection) {
        final Collection<FoodModel> foodModelsCollection =
                mFoodModelDataMapper.transform(foodsCollection);
        mView.renderFoodList(foodModelsCollection);
    }

    public void onItemClick(int position) {
        mView.navigateToFoodDetails(position);
    }

    private final class FoodListSubscriber extends com.zireck.calories.domain.interactor.DefaultSubscriber<List<Food>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<com.zireck.calories.domain.Food> foods) {
            showFoodsCollectionInView(foods);
        }
    }
}
