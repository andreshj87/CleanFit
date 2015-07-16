package com.zireck.projectk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.model.Food;

import java.util.List;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryRecyclerAdapter extends RecyclerView.Adapter<FoodRepositoryRecyclerAdapter.ViewHolder> {

    private List<Food> mItems;
    private int mLayout;

    public FoodRepositoryRecyclerAdapter(List<Food> items, int layout) {
        mItems = items;
        mLayout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food item = mItems.get(position);
        holder.text.setText(item.getName() + " (" + item.getBrand() + ")");
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.food_name);
        }
    }
}
