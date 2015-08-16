package com.zireck.projectk.presentation.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.util.PictureUtils;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    public static final int ITEM_LAYOUT = R.layout.fragment_food_list_item;

    private Context mContext;
    private List<FoodModel> mFoodsCollection;
    private int mLayout;

    public FoodListAdapter(Context context, List<FoodModel> foodsCollection, int layout) {
        mContext = context;
        mFoodsCollection = foodsCollection;
        mLayout = layout;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        final FoodModel food = mFoodsCollection.get(position);

        holder.foodId.setText(String.valueOf(food.getId()));

        Uri pictureUri = PictureUtils.getPhotoFileUri(food.getPicture());
        Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(holder.foodPicture);

        holder.foodName.setText(food.getName());
        holder.foodBrand.setText(food.getBrand());
        holder.foodCalories.setText(MathUtils.formatDouble(food.getCalories()));

        holder.itemView.setTag(food);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (mFoodsCollection != null) ? mFoodsCollection.size() : 0;
        //System.out.println("k9d3 item count = " + mFoodsCollection.size());
        //return mFoodsCollection.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.food_id) TextView foodId;
        @Bind(R.id.food_picture) CircleImageView foodPicture;
        @Bind(R.id.food_name) TextView foodName;
        @Bind(R.id.food_brand) TextView foodBrand;
        @Bind(R.id.food_calories) TextView foodCalories;

        public FoodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setFoodsCollection(Collection<FoodModel> foodsCollection) {
        validateFoodsCollection(foodsCollection);
        //mFoodsCollection.clear();
        mFoodsCollection = (List<FoodModel>) foodsCollection;
        //mFoodsCollection.addAll(foodsCollection);
        notifyDataSetChanged();
    }

    private void validateFoodsCollection(Collection<FoodModel> foodsCollection) {
        if (foodsCollection == null) {
            throw new IllegalArgumentException("The food list cannot be null.");
        }
    }
}
