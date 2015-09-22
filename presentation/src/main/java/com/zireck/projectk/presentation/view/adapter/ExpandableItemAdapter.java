package com.zireck.projectk.presentation.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.model.Day;
import com.zireck.projectk.presentation.model.MealModel;
import com.zireck.projectk.presentation.util.DateUtils;

import java.util.List;

/**
 * Created by Zireck on 22/09/2015.
 */
public class ExpandableItemAdapter extends AbstractExpandableItemAdapter<
        ExpandableItemAdapter.DaysViewHolder, ExpandableItemAdapter.MealsViewHolder> {

    private List<Day> mDays;

    public static class DaysViewHolder extends AbstractExpandableItemViewHolder {

        RelativeLayout mDayLayout;
        TextView mDayDate;

        public DaysViewHolder(View v) {
            super(v);
            mDayLayout = (RelativeLayout) v.findViewById(R.id.day_layout);
            mDayDate = (TextView) v.findViewById(R.id.day_date);
        }
    }

    public static class MealsViewHolder extends AbstractExpandableItemViewHolder {

        TextView mMealItem;

        public MealsViewHolder(View v) {
            super(v);
            mMealItem = (TextView) v.findViewById(R.id.meal_item);
        }
    }

    public ExpandableItemAdapter(List<Day> days) {
        mDays = days;

        setHasStableIds(true);
    }

    public void setDays(List<Day> days) {
        mDays = days;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mDays != null ? mDays.size() : 0;
    }

    @Override
    public int getChildCount(int i) {
        return mDays != null && mDays.get(i) != null && mDays.get(i).getMeals() != null
                ? mDays.get(i).getMeals().size() : 0;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int j) {
        return j;
    }

    @Override
    public int getGroupItemViewType(int i) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int i, int i1) {
        return 0;
    }

    @Override
    public DaysViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.day_item, parent, false);
        return new DaysViewHolder(view);
    }

    @Override
    public MealsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.meal_item, parent, false);
        return new MealsViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(DaysViewHolder daysViewHolder, int groupPosition, int viewType) {
        final Day day = mDays.get(groupPosition);

        daysViewHolder.mDayDate.setText(DateUtils.getFormattedDayDate(day.getDate()));
        daysViewHolder.itemView.setClickable(true);

        final int expandState = daysViewHolder.getExpandStateFlags();
        if ((expandState & RecyclerViewExpandableItemManager.STATE_FLAG_IS_UPDATED) != 0) {

            int color;

            if ((expandState & RecyclerViewExpandableItemManager.STATE_FLAG_IS_EXPANDED) != 0) {
                color = Color.GREEN;
            } else {
                color = Color.WHITE;
            }

            daysViewHolder.mDayLayout.setBackgroundColor(color);
        }
    }

    @Override
    public void onBindChildViewHolder(MealsViewHolder mealsViewHolder, int groupPosition, int childPosition, int viewType) {
        final Day day = mDays.get(groupPosition);
        final List<MealModel> meals = (List<MealModel>) day.getMeals();
        MealModel meal = meals.get(childPosition);

        mealsViewHolder.mMealItem.setText(meal.getFoodModel().getName() + " " + meal.getGrams() + "gr");
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(DaysViewHolder daysViewHolder, int i, int i1, int i2, boolean b) {
        if (!(daysViewHolder.itemView.isEnabled() && daysViewHolder.itemView.isClickable())) {
            return false;
        }

        return true;
    }

}
