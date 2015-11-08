package com.zireck.calories.presentation.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;
import com.zireck.calories.R;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.util.MathUtils;
import com.zireck.calories.presentation.util.PictureUtils;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder> {

    public static final int ITEM_LAYOUT = R.layout.fragment_food_list_item;

    private Context mContext;
    private List<FoodModel> mFoodsCollection;
    private int mLayout;

    public FoodRecyclerAdapter(Context context, List<FoodModel> foodsCollection, int layout) {
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
    public void onBindViewHolder(final FoodViewHolder holder, int position) {
        final FoodModel food = mFoodsCollection.get(position);

        holder.foodId.setText(String.valueOf(food.getId()));

        setFoodTextDrawable(food, holder);
        if (food.getPicture() != null && !TextUtils.isEmpty(food.getPicture())) {
            Uri pictureUri = PictureUtils.getPhotoFileUri(food.getPicture());
            Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(holder.foodPicture);

            holder.foodTextDrawable.setVisibility(View.INVISIBLE);
            holder.foodPicture.setVisibility(View.VISIBLE);
        }

        holder.foodName.setText(food.getName());
        holder.foodBrand.setText(food.getBrand());
        holder.foodCalories.setText(MathUtils.formatDouble(food.getCalories()));

        holder.itemView.setTag(food);
    }

    private void setFoodTextDrawable(FoodModel foodModel, FoodViewHolder foodViewHolder) {
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getColor(foodModel.getName());
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(String.valueOf(foodModel.getName().charAt(0)).toUpperCase(), color);
        foodViewHolder.foodTextDrawable.setImageDrawable(textDrawable);

        foodViewHolder.foodPicture.setVisibility(View.INVISIBLE);
        foodViewHolder.foodTextDrawable.setVisibility(View.VISIBLE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (mFoodsCollection != null) ? mFoodsCollection.size() : 0;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.food_id) TextView foodId;
        @Bind(R.id.food_picture) CircleImageView foodPicture;
        @Bind(R.id.food_textdrawable) ImageView foodTextDrawable;
        @Bind(R.id.food_name) TextView foodName;
        @Bind(R.id.food_brand) TextView foodBrand;
        @Bind(R.id.food_calories) TextView foodCalories;

        public FoodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public FoodModel getItem(int position) {
        return mFoodsCollection.get(position);
    }

    public void setFoodsCollection(Collection<FoodModel> foodsCollection) {
        validateFoodsCollection(foodsCollection);
        mFoodsCollection = (List<FoodModel>) foodsCollection;
        notifyDataSetChanged();
    }

    private void validateFoodsCollection(Collection<FoodModel> foodsCollection) {
        if (foodsCollection == null) {
            throw new IllegalArgumentException("The food list cannot be null.");
        }
    }
}
