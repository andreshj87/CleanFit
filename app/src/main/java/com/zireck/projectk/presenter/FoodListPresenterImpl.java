package com.zireck.projectk.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.helper.Navigator;
import com.zireck.projectk.interactor.FoodListInteractor;
import com.zireck.projectk.interactor.FoodListInteractorImpl;
import com.zireck.projectk.listener.OnFoodListFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.view.FoodListView;

import java.util.List;

/**
 * Created by Zireck on 22/07/2015.
 */
public class FoodListPresenterImpl implements FoodListPresenter, OnFoodListFinishedListener {

    private Navigator mNavigator;
    private FoodListView mView;
    private FoodListInteractor mInteractor;

    public FoodListPresenterImpl(Context context, FoodListView view) {
        mNavigator = new Navigator(context);
        mView = view;
        mInteractor = new FoodListInteractorImpl();
    }

    @Override
    public void onResume() {
        if (mView.getCurrentTag() == mView.getFoodTag()) {
            mInteractor.retrieveFoodRepository(this);
        } else if (mView.getCurrentTag() == mView.getDrinkTag()) {
            mInteractor.retrieveDrinkRepository(this);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        TextView foodId = (TextView) view.findViewById(R.id.food_id);
        long id = Long.valueOf(foodId.getText().toString());
        mNavigator.openFoodDetailActivity(id);
    }

    @Override
    public void onFinished(List<Food> items) {
        mView.setFoodItems(items);
    }
}
