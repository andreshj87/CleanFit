package com.zireck.projectk.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zireck.projectk.R;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.util.MathUtils;
import com.zireck.projectk.util.PictureUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    public static final int ITEM_LAYOUT = R.layout.fragment_food_list_item;

    private Context mContext;
    private List<Food> mFoodItems;
    private int mLayout;

    public FoodListAdapter(Context context, List<Food> items, int layout) {
        mContext = context;
        mFoodItems = items;
        mLayout = layout;
    }

    public void setFoodItems(List<Food> items) {
        mFoodItems = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = mFoodItems.get(position);

        holder.foodId.setText(String.valueOf(food.getId()));

        Uri pictureUri = PictureUtils.getPhotoFileUri(food.getPicture());
        Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(holder.foodPicture);

        holder.foodName.setText(food.getName());
        holder.foodBrand.setText(food.getBrand());
        holder.foodCalories.setText(MathUtils.formatDouble(food.getCalories()));

        holder.itemView.setTag(food);
    }

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.food_id) TextView foodId;
        @Bind(R.id.food_picture) CircleImageView foodPicture;
        @Bind(R.id.food_name) TextView foodName;
        @Bind(R.id.food_brand) TextView foodBrand;
        @Bind(R.id.food_calories) TextView foodCalories;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
