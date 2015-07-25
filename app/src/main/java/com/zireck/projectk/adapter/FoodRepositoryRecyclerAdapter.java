package com.zireck.projectk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.util.MathUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryRecyclerAdapter extends RecyclerView.Adapter<FoodRepositoryRecyclerAdapter.ViewHolder> {

    private List<Food> mFoodItems;
    private int mLayout;

    public FoodRepositoryRecyclerAdapter(List<Food> items, int layout) {
        mFoodItems = items;
        mLayout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = mFoodItems.get(position);
        holder.foodCalories.setText(MathUtils.formatDouble(food.getCalories()));
        holder.foodName.setText(food.getName());
        holder.foodBrand.setText(food.getBrand());

        // Nutrients
        holder.layoutNutrients.setWeightSum(100.0f);

        LinearLayout.LayoutParams params;

        params = (LinearLayout.LayoutParams) holder.fats.getLayoutParams();
        params.weight = (float) food.getFatsPercent();
        holder.fats.setLayoutParams(params);

        params = (LinearLayout.LayoutParams) holder.carbohydrates.getLayoutParams();
        params.weight = (float) food.getCarbohydratesPercent();
        holder.carbohydrates.setLayoutParams(params);

        params = (LinearLayout.LayoutParams) holder.proteins.getLayoutParams();
        params.weight = (float) food.getProteins();
        holder.proteins.setLayoutParams(params);

        holder.itemView.setTag(food);
    }

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.food_calories) TextView foodCalories;
        @Bind(R.id.food_name) TextView foodName;
        @Bind(R.id.food_brand) TextView foodBrand;

        @Bind(R.id.layout_nutrients_percent) LinearLayout layoutNutrients;
        @Bind(R.id.fats_percent) View fats;
        @Bind(R.id.carbohydrates_percent) View carbohydrates;
        @Bind(R.id.proteins_percent) View proteins;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
