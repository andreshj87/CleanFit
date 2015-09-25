package com.zireck.projectk.presentation.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.enumeration.Mealtime;
import com.zireck.projectk.presentation.listener.OnDeleteMealClick;
import com.zireck.projectk.presentation.model.Day;
import com.zireck.projectk.presentation.model.MealModel;
import com.zireck.projectk.presentation.util.DateUtils;
import com.zireck.projectk.presentation.util.MathUtils;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zireck on 22/09/2015.
 */
public class ExpandableItemAdapter extends AbstractExpandableItemAdapter<
        ExpandableItemAdapter.DaysViewHolder, ExpandableItemAdapter.MealsViewHolder> {

    private Context mContext;
    private List<Day> mDays;
    private double mGoal;
    private OnDeleteMealClick mCallback;

    public static class DaysViewHolder extends AbstractExpandableItemViewHolder {

        @Bind(R.id.day_layout) RelativeLayout mDayLayout;
        @Bind(R.id.day_date) TextView mDayDate;
        @Bind(R.id.day_calories) TextView mDayCalories;
        @Bind(R.id.day_calories_progress) View mDayCaloriesProgress;
        @Bind(R.id.day_calories_percent) TextView mDayCaloriesPercent;

        public DaysViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public static class MealsViewHolder extends AbstractExpandableItemViewHolder {

        @Bind(R.id.meal_calories) TextView mMealCalories;
        @Bind(R.id.meal_food) TextView mMealFood;
        @Bind(R.id.meal_time) TextView mMealTime;
        @Bind(R.id.meal_delete_button) MaterialIconView mMealDeleteButton;

        public MealsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ExpandableItemAdapter(Context context, OnDeleteMealClick callback, List<Day> days) {
        mContext = context;
        mCallback = callback;
        mDays = days;

        setHasStableIds(true);
    }

    public void setGoal(double goal) {
        mGoal = goal;
        notifyDataSetChanged();
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

        day.calculateEnergyAndNutrients();

        daysViewHolder.mDayDate.setText(DateUtils.getFormattedDayDate(day.getDate()));
        daysViewHolder.mDayCalories.setText(MathUtils.betterFormatDouble(day.getCalories()) + "kcal");

        int realProgress = day.getProgressForGoal(mGoal);
        int barProgress = realProgress > 100 ? 100 : realProgress;

        daysViewHolder.mDayCaloriesProgress.setLayoutParams(
                new LinearLayout.LayoutParams(0, 24, barProgress)
        );

        daysViewHolder.mDayCaloriesPercent.setText(realProgress + "%");
        daysViewHolder.itemView.setClickable(true);

        final int expandState = daysViewHolder.getExpandStateFlags();
        if ((expandState & RecyclerViewExpandableItemManager.STATE_FLAG_IS_UPDATED) != 0) {

            int color;

            if ((expandState & RecyclerViewExpandableItemManager.STATE_FLAG_IS_EXPANDED) != 0) {
                color = Color.GREEN;
            } else {
                color = Color.WHITE;
            }

            //daysViewHolder.mDayLayout.setBackgroundColor(color);
        }
    }

    @Override
    public void onBindChildViewHolder(MealsViewHolder mealsViewHolder, int groupPosition, int childPosition, int viewType) {
        final Day day = mDays.get(groupPosition);
        final List<MealModel> meals = (List<MealModel>) day.getMeals();
        final MealModel meal = meals.get(childPosition);

        mealsViewHolder.mMealCalories.setText(MathUtils.betterFormatDouble(meal.getCalories()) + "kcal");

        String foodName = "(Deleted Food)";
        if (meal.getFoodModel() != null && !TextUtils.isEmpty(meal.getFoodModel().getName())) {
            foodName = meal.getFoodModel().getName();
        }
        mealsViewHolder.mMealFood.setText(foodName + ", " + meal.getGrams() + "gr");

        Mealtime mealtime = Mealtime.fromValue(meal.getMealtime());
        mealsViewHolder.mMealTime.setText(mContext.getResources().getString(mealtime.getResourceValue()) + " @ " + DateUtils.getTimeFromMealDate(meal.getDate()));

        mealsViewHolder.mMealDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mContext)
                        .theme(Theme.LIGHT)
                        .title("Delete")
                        .content("Delete meal?")
                        .positiveText("Ok")
                        .negativeText("Cancel")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                mCallback.deleteMeal(meal);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(DaysViewHolder daysViewHolder, int i, int i1, int i2, boolean b) {
        if (!(daysViewHolder.itemView.isEnabled() && daysViewHolder.itemView.isClickable())) {
            return false;
        }

        return true;
    }

}
