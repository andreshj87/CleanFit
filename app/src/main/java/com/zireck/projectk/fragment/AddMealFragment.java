package com.zireck.projectk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.zireck.projectk.R;
import com.zireck.projectk.adapter.FoodSpinnerAdapter;
import com.zireck.projectk.dagger.AddMealModule;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.presenter.AddMealPresenter;
import com.zireck.projectk.view.AddMealView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Zireck on 06/08/2015.
 */
public class AddMealFragment extends BaseFragment implements AddMealView {

    @Bind(R.id.spinner_food) MaterialSpinner mSpinnerFood;
    @Bind(R.id.button_meal_date) Button mMealDate;
    @Bind(R.id.button_meal_time) Button mMealTime;
    @Bind(R.id.button_meal_amount) Button mMealAmount;

    @Inject FoodSpinnerAdapter mAdapter;
    @Inject AddMealPresenter mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSpinnerFood.setAdapter(mAdapter);

        int padding = 32;

        MaterialIconView icon = new MaterialIconView(getActivity());
        icon.setIcon(MaterialDrawableBuilder.IconValue.CALENDAR_CLOCK);
        icon.setColorResource(R.color.primary);
        mMealDate.setCompoundDrawables(icon.getDrawable(), null, null, null);
        mMealDate.setCompoundDrawablePadding(padding);

        icon.setIcon(MaterialDrawableBuilder.IconValue.TIMER);
        mMealTime.setCompoundDrawables(icon.getDrawable(), null, null, null);
        mMealTime.setCompoundDrawablePadding(padding);

        icon.setIcon(MaterialDrawableBuilder.IconValue.WEIGHT);
        mMealAmount.setCompoundDrawables(icon.getDrawable(), null, null, null);
        mMealAmount.setCompoundDrawablePadding(padding);
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new LinkedList<Object>();
        modules.add(new AddMealModule(this));
        return modules;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_meal;
    }

    @Override
    public void setSpinnerFoodItems(List<Food> foodItems) {
        mAdapter.setFoods(foodItems);
    }
}
