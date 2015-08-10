package com.zireck.projectk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vstechlab.easyfonts.EasyFonts;
import com.zireck.projectk.R;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.util.PictureUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zireck on 06/08/2015.
 */
public class FoodSpinnerAdapter extends BaseAdapter {

    public static final int SPINNER_ITEM_LAYOUT = R.layout.spinner_item;

    private Context mContext;

    private List<Food> mFoods;

    @Bind(R.id.food_picture) ImageView mFoodPicture;
    @Bind(R.id.food_name) TextView mFoodName;
    @Bind(R.id.food_brand) TextView mFoodBrand;

    public FoodSpinnerAdapter(Context context, int resource) {
        mContext = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = getCustomView(position, convertView, parent);
        RelativeLayout foodItem = (RelativeLayout) view.findViewById(R.id.food_item);
        foodItem.setPadding(32, 16, 32, 16);
        return view;
    }

    @Override
    public int getCount() {
        return mFoods.size();
    }

    @Override
    public Food getItem(int position) {
        return mFoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public void setFoods(List<Food> foods) {
        mFoods = foods;
        notifyDataSetChanged();
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(mContext).inflate(FoodSpinnerAdapter.SPINNER_ITEM_LAYOUT, parent, false);
        ButterKnife.bind(this, row);

        Food food = getItem(position);
        mFoodName.setText(food.getName());
        mFoodBrand.setText(food.getBrand());

        Picasso.with(mContext).load(PictureUtils.getPhotoFileUri(food.getPicture())).fit().centerCrop().into(mFoodPicture);
        mFoodName.setTypeface(EasyFonts.robotoLight(mContext));

        return row;
    }
}
