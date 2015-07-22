package com.zireck.projectk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zireck.projectk.R;
import com.zireck.projectk.adapter.FoodRepositoryRecyclerAdapter;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.presenter.FoodListPresenter;
import com.zireck.projectk.presenter.FoodListPresenterImpl;
import com.zireck.projectk.view.FoodListView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Zireck on 22/07/2015.
 */
public class FoodListFragment extends BaseFragment implements FoodListView {

    private static final String TAG_NAME = "FOOD_TYPE";
    public static final int TAG_FOOD = 0;
    public static final int TAG_DRINK = 1;
    public static final int TAG_RECENT = 2;

    private int mCurrentTag;

    @Bind(R.id.food_list) RecyclerView mRecyclerView;
    private FoodRepositoryRecyclerAdapter mAdapter;
    private FoodListPresenter mPresenter;

    public static FoodListFragment newInstance(int tag) {
        Bundle bundle = new Bundle();
        bundle.putInt(FoodListFragment.TAG_NAME, tag);

        FoodListFragment foodListFragment = new FoodListFragment();
        foodListFragment.setArguments(bundle);

        return foodListFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrieveTag(savedInstanceState);

        loadPresenter();

        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_food_repository_list;
    }

    @Override
    public void setFoodItems(List<Food> items) {
        mAdapter = new FoodRepositoryRecyclerAdapter(items, R.layout.food_repository_recyclerview_item);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getCurrentTag() {
        return mCurrentTag;
    }

    @Override
    public int getFoodTag() {
        return FoodListFragment.TAG_FOOD;
    }

    @Override
    public int getDrinkTag() {
        return FoodListFragment.TAG_DRINK;
    }

    @Override
    public int getRecentTag() {
        return FoodListFragment.TAG_RECENT;
    }

    private void retrieveTag(Bundle bundle) {
        if (getArguments() != null && getArguments().containsKey(FoodListFragment.TAG_NAME)) {
            mCurrentTag = getArguments().getInt(FoodListFragment.TAG_NAME);
        }
    }

    private void loadPresenter() {
        mPresenter = new FoodListPresenterImpl(this);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
