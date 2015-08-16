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
import com.zireck.projectk.presentation.presenter.DrinkListPresenter;
import com.zireck.projectk.presentation.view.DrinkListView;
import com.zireck.projectk.presentation.view.adapter.FoodListAdapter;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Zireck on 16/08/2015.
 */
public class DrinkListFragment extends BaseFragment implements DrinkListView {

    @Inject DrinkListPresenter mDrinkListPresenter;
    @Bind(R.id.food_list) RecyclerView mRecyclerView;
    protected FoodListAdapter mAdapter;

    public static DrinkListFragment newInstance() {
        return new DrinkListFragment();
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
        loadDrinkList();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDrinkListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDrinkListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDrinkListPresenter.destroy();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_food_list;
    }

    private void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mDrinkListPresenter.setView(this);
    }

    private void loadDrinkList() {
        mDrinkListPresenter.initialize();
    }

    @Override
    public void renderDrinkList(Collection<FoodModel> drinkItems) {
        if (drinkItems != null) {
            mAdapter.setFoodsCollection(drinkItems);
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //mPresenter.onItemClick(view, position);
            }
        }));

        mAdapter = new FoodListAdapter(getActivity(), new ArrayList<FoodModel>(), FoodListAdapter.ITEM_LAYOUT);
        mRecyclerView.setAdapter(mAdapter);
    }
}
