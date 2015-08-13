package com.zireck.projectk.presentation.presenter;

/**
 * Created by Zireck on 22/07/2015.
 */
/*
public class OldFoodListPresenterImpl implements OldFoodListPresenter, OnFoodListFinishedListener {

    private Context mContext;
    private FoodListView mView;
    private FoodListInteractor mInteractor;
    @Inject Navigator mNavigator;

    public OldFoodListPresenterImpl(Context context, FoodListView view) {
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
        mView.renderFoodList(items);
    }
}*/
