package com.zireck.calories.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;
import com.zireck.calories.R;
import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.presentation.helper.RecyclerItemClickListener;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.navigation.Navigator;
import com.zireck.calories.presentation.presenter.DrinkListPresenter;
import com.zireck.calories.presentation.view.DrinkListView;
import com.zireck.calories.presentation.view.adapter.FoodRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Zireck on 16/08/2015.
 */
public class DrinkListFragment extends BaseFragment implements DrinkListView {

    @Inject Navigator mNavigator;
    @Inject DrinkListPresenter mPresenter;

    @Bind(R.id.food_list_empty) TextView mDrinkListEmpty;
    @Bind(R.id.food_list) RecyclerView mRecyclerView;
    protected FoodRecyclerAdapter mAdapter;

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
    public void renderDrinkList(Collection<FoodModel> drinkItems) {
        if (drinkItems != null && drinkItems.size() > 0) {
            mDrinkListEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mDrinkListEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

        mAdapter.setFoodsCollection(drinkItems);
    }

    @Override
    public void navigateToFoodDetails(FoodModel food) {
        mNavigator.openFoodDetailActivity(getActivity(), food);
    }

    private void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mPresenter.setView(this);

        mDrinkListEmpty.setText("Your drink list is empty.");
        mDrinkListEmpty.setTypeface(EasyFonts.robotoLight(getActivity()));
        mDrinkListEmpty.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FoodModel food = mAdapter.getItem(position);
                mPresenter.onItemClick(food);
            }
        }));

        mAdapter = new FoodRecyclerAdapter(getActivity(), new ArrayList<FoodModel>(), FoodRecyclerAdapter.ITEM_LAYOUT);
        mRecyclerView.setAdapter(mAdapter);
    }
}
