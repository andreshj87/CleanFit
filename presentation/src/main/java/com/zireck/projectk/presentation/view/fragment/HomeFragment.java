package com.zireck.projectk.presentation.view.fragment;

import com.zireck.projectk.R;

/**
 * Created by Zireck on 06/08/2015.
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_home;
    }
}
