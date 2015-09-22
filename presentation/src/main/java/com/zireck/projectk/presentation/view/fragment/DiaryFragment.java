package com.zireck.projectk.presentation.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.model.Day;
import com.zireck.projectk.presentation.navigation.Navigator;
import com.zireck.projectk.presentation.presenter.DiaryPresenter;
import com.zireck.projectk.presentation.view.DiaryView;
import com.zireck.projectk.presentation.view.adapter.ExpandableItemAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Zireck on 22/09/2015.
 */
public class DiaryFragment extends BaseFragment implements DiaryView {

    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";

    @Inject Navigator mNavigator;

    @Inject DiaryPresenter mPresenter;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ExpandableItemAdapter mExpandableItemAdapter;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;

    public static DiaryFragment newInstance() {
        return new DiaryFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
        initRecyclerView(savedInstanceState);
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
        mPresenter.destroy();

        releaseRecyclerView();

        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecyclerViewExpandableItemManager != null) {
            outState.putParcelable(
                    SAVED_STATE_EXPANDABLE_ITEM_MANAGER,
                    mRecyclerViewExpandableItemManager.getSavedState()
            );
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_diary;
    }

    @Override
    public void navigateToSettings() {
        mNavigator.openSettingsActivity(getActivity());
    }

    @Override
    public void renderDaysInView(Collection<Day> days) {
        mExpandableItemAdapter.setDays((List<Day>) days);
    }

    private void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mPresenter.setView(this);
    }

    private void initRecyclerView(Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(getActivity());

        final Parcelable eimSavedState = (savedInstanceState != null) ?
                savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);

        mExpandableItemAdapter = new ExpandableItemAdapter(new ArrayList<Day>());
        mAdapter = mExpandableItemAdapter;
        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(mExpandableItemAdapter);

        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        animator.setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setHasFixedSize(false);

        mRecyclerViewExpandableItemManager.attachRecyclerView(mRecyclerView);
    }

    private void releaseRecyclerView() {
        if (mRecyclerViewExpandableItemManager != null) {
            mRecyclerViewExpandableItemManager.release();
            mRecyclerViewExpandableItemManager = null;
        }

        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }

        mAdapter = null;
        mLayoutManager = null;
    }
}
