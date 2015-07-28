package com.zireck.projectk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.helper.PictureHelper;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.util.BitmapUtils;
import com.zireck.projectk.util.MathUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryRecyclerAdapter extends RecyclerView.Adapter<FoodRepositoryRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Food> mFoodItems;
    private int mLayout;

    private PictureHelper mPictureHelper;

    public FoodRepositoryRecyclerAdapter(Context context, List<Food> items, int layout) {
        mContext = context;
        mFoodItems = items;
        mLayout = layout;

        mPictureHelper = new PictureHelper(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = mFoodItems.get(position);
        //holder.foodCalories.setText(MathUtils.formatDouble(food.getCalories()));

        holder.foodPicture.setImageBitmap(mPictureHelper.getSampledPictureBitmap(food.getPicture(), ViewHolder.PICTURE_WIDTH, ViewHolder.PICTURE_HEIGHT));
        holder.foodName.setText(food.getName());
        holder.foodBrand.setText(food.getBrand());

        holder.itemView.setTag(food);
    }

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static int PICTURE_WIDTH = 64;
        public static int PICTURE_HEIGHT = 64;

        @Bind(R.id.food_picture) CircleImageView foodPicture;
        @Bind(R.id.food_name) TextView foodName;
        @Bind(R.id.food_brand) TextView foodBrand;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
