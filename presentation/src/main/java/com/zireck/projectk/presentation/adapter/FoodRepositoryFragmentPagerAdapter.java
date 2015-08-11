package com.zireck.projectk.presentation.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zireck.projectk.R;
import com.zireck.projectk.presentation.fragment.FoodListFragment;

/**
 * Created by Zireck on 20/07/2015.
 */
public class FoodRepositoryFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private final int[] mFragmentTitles = { R.string.fragment_foods, R.string.fragment_drinks };

    public FoodRepositoryFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FoodListFragment.newInstance(FoodListFragment.TAG_FOOD);
            case 1:
                return FoodListFragment.newInstance(FoodListFragment.TAG_DRINK);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(mFragmentTitles[position]);
    }
}
