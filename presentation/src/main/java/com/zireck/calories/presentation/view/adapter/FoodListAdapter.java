package com.zireck.calories.presentation.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zireck.calories.presentation.util.MathUtils;
import com.zireck.calories.presentation.util.PictureUtils;
import com.zireck.calories.R;
import com.zireck.calories.presentation.model.FoodModel;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zireck on 24/08/2015.
 */
public class FoodListAdapter extends ArrayAdapter<FoodModel> {

    public static final int ITEM_LAYOUT = R.layout.dialog_food_list_item;

    private Context mContext;
    private int mResource;
    private List<FoodModel> mItems;

    public FoodListAdapter(Context context, int resource) {
        super(context, resource);

        mContext = context;
        mResource = resource;
    }

    @Override
    public int getCount() {
        return (mItems != null) ? mItems.size() : 0;
    }

    @Override
    public FoodModel getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FoodModel foodModel = getItem(position);

        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.foodId.setText(String.valueOf(foodModel.getId()));

        if (foodModel.getPicture() != null && !TextUtils.isEmpty(foodModel.getPicture())) {
            Uri pictureUri = PictureUtils.getPhotoFileUri(foodModel.getPicture());
            Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(viewHolder.foodPicture, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    setFoodTextDrawable(foodModel, viewHolder);
                }
            });

            viewHolder.foodTextDrawable.setVisibility(View.INVISIBLE);
            viewHolder.foodPicture.setVisibility(View.VISIBLE);
        } else {
            setFoodTextDrawable(foodModel, viewHolder);
        }

        viewHolder.foodName.setText(foodModel.getName());
        viewHolder.foodBrand.setText(foodModel.getBrand());
        viewHolder.foodCalories.setText(MathUtils.formatDouble(foodModel.getCalories()));

        return convertView;
    }

    private void setFoodTextDrawable(FoodModel foodModel, ViewHolder foodViewHolder) {
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getColor(foodModel.getName());
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(String.valueOf(foodModel.getName().charAt(0)).toUpperCase(), color);
        foodViewHolder.foodTextDrawable.setImageDrawable(textDrawable);

        foodViewHolder.foodPicture.setVisibility(View.INVISIBLE);
        foodViewHolder.foodTextDrawable.setVisibility(View.VISIBLE);
    }

    public void setFoodsCollection(Collection<FoodModel> foodsCollection) {
        validateFoodsCollection(foodsCollection);
        mItems = (List<FoodModel>) foodsCollection;
        notifyDataSetChanged();
    }

    private void validateFoodsCollection(Collection<FoodModel> foodsCollection) {
        if (foodsCollection == null) {
            throw new IllegalArgumentException("The food list cannot be null.");
        }
    }

    public static class ViewHolder {

        @Bind(R.id.food_id) TextView foodId;
        @Bind(R.id.food_picture) CircleImageView foodPicture;
        @Bind(R.id.food_textdrawable) ImageView foodTextDrawable;
        @Bind(R.id.food_name) TextView foodName;
        @Bind(R.id.food_brand) TextView foodBrand;
        @Bind(R.id.food_calories) TextView foodCalories;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
