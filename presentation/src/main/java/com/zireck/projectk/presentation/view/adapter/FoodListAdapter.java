package com.zireck.projectk.presentation.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        FoodModel foodModel = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.foodId.setText(String.valueOf(foodModel.getId()));

        Uri pictureUri = PictureUtils.getPhotoFileUri(foodModel.getPicture());
        Picasso.with(mContext).load(pictureUri).fit().centerCrop().into(viewHolder.foodPicture);

        viewHolder.foodName.setText(foodModel.getName());
        viewHolder.foodBrand.setText(foodModel.getBrand());
        viewHolder.foodCalories.setText(MathUtils.formatDouble(foodModel.getCalories()));

        return convertView;
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
        @Bind(R.id.food_name) TextView foodName;
        @Bind(R.id.food_brand) TextView foodBrand;
        @Bind(R.id.food_calories) TextView foodCalories;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
