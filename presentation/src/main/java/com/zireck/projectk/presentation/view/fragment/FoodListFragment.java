package com.zireck.projectk.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.helper.RecyclerItemClickListener;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.navigation.Navigator;
import com.zireck.projectk.presentation.presenter.FoodListPresenter;
import com.zireck.projectk.presentation.view.FoodListView;
import com.zireck.projectk.presentation.view.adapter.FoodListAdapter;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Zireck on 22/07/2015.
 */
public class FoodListFragment extends BaseFragment implements FoodListView {

    @Inject Navigator mNavigator;
    @Inject FoodListPresenter mPresenter;

    @Bind(R.id.food_list) RecyclerView mRecyclerView;
    protected FoodListAdapter mAdapter;

    public static FoodListFragment newInstance() {
        return new FoodListFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_food_list;
    }

    @Override
    public void renderFoodList(Collection<FoodModel> foodModelsCollection) {
        if (foodModelsCollection != null) {
            mAdapter.setFoodsCollection(foodModelsCollection);
        }
    }

    @Override
    public void navigateToFoodDetails(int position) {
        FoodModel food = mAdapter.getItem(position);
        mNavigator.openFoodDetailActivity(getActivity(), food);
    }

    protected void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mPresenter.setView(this);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.onItemClick(position);
            }
        }));

        mAdapter = new FoodListAdapter(getActivity(), new ArrayList<FoodModel>(), FoodListAdapter.ITEM_LAYOUT);
        mRecyclerView.setAdapter(mAdapter);
    }

}
