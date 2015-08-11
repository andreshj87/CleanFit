package com.zireck.projectk.presentation.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.presentation.helper.Navigator;
import com.zireck.projectk.presentation.interactor.FoodListInteractor;
import com.zireck.projectk.presentation.interactor.FoodListInteractorImpl;
import com.zireck.projectk.presentation.listener.OnFoodListFinishedListener;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.view.FoodListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Zireck on 22/07/2015.
 */
public class FoodListPresenterImpl implements FoodListPresenter, OnFoodListFinishedListener {

    private Context mContext;
    private FoodListView mView;
    private FoodListInteractor mInteractor;
    @Inject Navigator mNavigator;

    public FoodListPresenterImpl(Context context, FoodListView view) {
        mContext = context;
        mView = view;
        mInteractor = new FoodListInteractorImpl();
        mNavigator = new Navigator();
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
        mNavigator.openFoodDetailActivity(mContext, id);
    }

    @Override
    public void onFinished(List<FoodModel> items) {
        mView.setFoodItems(items);
    }
}
