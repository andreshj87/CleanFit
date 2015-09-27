package com.zireck.calories.presentation.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.zireck.calories.R;
import com.zireck.calories.presentation.view.adapter.FoodRepositoryFragmentPagerAdapter;
import com.zireck.calories.presentation.listener.OnFoodRepositoryTabChangeListener;

import butterknife.Bind;

/**
 * Created by Zireck on 20/07/2015.
 */
public class FoodCatalogFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private OnFoodRepositoryTabChangeListener mCallback;

    @Bind(R.id.pagerslidingtabstrip) PagerSlidingTabStrip mPagerSlidingTabStrip;
    @Bind(R.id.viewpager) ViewPager mViewPager;
    private FoodRepositoryFragmentPagerAdapter mAdapter;

    public static FoodCatalogFragment newInstance() {
        return new FoodCatalogFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnFoodRepositoryTabChangeListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewPager();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_food_catalog;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mCallback != null) {
            mCallback.tabChanged();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initViewPager() {
        mAdapter = new FoodRepositoryFragmentPagerAdapter(getActivity(), getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(this);
    }
}
