package com.zireck.projectk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.zireck.projectk.R;
import com.zireck.projectk.adapter.FoodRepositoryFragmentPagerAdapter;

import butterknife.Bind;

/**
 * Created by Zireck on 20/07/2015.
 */
public class FoodRepositoryFragment extends BaseFragment {

    @Bind(R.id.pagerslidingtabstrip) PagerSlidingTabStrip mPagerSlidingTabStrip;
    @Bind(R.id.viewpager) ViewPager mViewPager;
    private FoodRepositoryFragmentPagerAdapter mAdapter;

    public static FoodRepositoryFragment newInstance() {
        return new FoodRepositoryFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new FoodRepositoryFragmentPagerAdapter(getActivity(), getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_food_repository;
    }
}
