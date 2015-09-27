package com.zireck.calories.presentation.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zireck.calories.presentation.presenter.AddEditFoodPresenter;
import com.zireck.calories.presentation.util.PictureUtils;
import com.zireck.calories.R;
import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.presentation.listener.OnEditFoodFinishedListener;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.presenter.EditFoodPresenter;
import com.zireck.calories.presentation.view.EditFoodView;

import javax.inject.Inject;

/**
 * Created by Zireck on 31/07/2015.
 */
public class EditFoodFragment extends AddEditFoodFragment implements EditFoodView {

    private static final String EXTRA_FOOD_OBJECT = "food_object";

    private OnEditFoodFinishedListener mCallback;
    @Inject EditFoodPresenter mEditFoodPresenter;

    public static EditFoodFragment newInstance(FoodModel food) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EditFoodFragment.EXTRA_FOOD_OBJECT, food);

        EditFoodFragment editFoodFragment = new EditFoodFragment();
        editFoodFragment.setArguments(bundle);

        return editFoodFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnEditFoodFinishedListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PictureUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                mEditFoodPresenter.doNotReceivePicture();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().destroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_edit).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                validateData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected AddEditFoodPresenter getPresenter() {
        return mEditFoodPresenter;
    }

    @Override
    public void foodSuccessfullyEdited() {
        if (mCallback != null) {
            mCallback.foodEdited();
        }
    }

    @Override
    public void setFoodName(String foodName) {
        mFoodNameEditText.setText(foodName);
    }

    @Override
    public void setFoodBrand(String foodBrand) {
        mFoodBrandEditText.setText(foodBrand);
    }

    @Override
    public void setFoodCalories(String foodCalories) {
        mFoodCaloriesEditText.setText(foodCalories);
    }

    @Override
    public void setFoodFats(String foodFats) {
        mFoodFatsEditText.setText(foodFats);
    }

    @Override
    public void setFoodCarbohydrates(String foodCarbohydrates) {
        mFoodCarbohydratesEditText.setText(foodCarbohydrates);
    }

    @Override
    public void setFoodProteins(String foodProteins) {
        mFoodProteinsEditText.setText(foodProteins);
    }

    @Override
    public void setIsDrink(boolean isDrink) {
        mFoodIsDrinkCheckbox.setChecked(isDrink);
    }

    @Override
    public void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mEditFoodPresenter.setView(this);
        mEditFoodPresenter.mapExtras(getArguments(), EditFoodFragment.EXTRA_FOOD_OBJECT);
    }

    protected void validateData() {
        String name = mFoodNameEditText.getText().toString();
        String brand = mFoodBrandEditText.getText().toString();
        boolean isDrink = mFoodIsDrinkCheckbox.isChecked();
        String calories = mFoodCaloriesEditText.getText().toString();
        String fats = mFoodFatsEditText.getText().toString();
        String carbohydrates = mFoodCarbohydratesEditText.getText().toString();
        String proteins = mFoodProteinsEditText.getText().toString();

        mEditFoodPresenter.validateData(name, brand, isDrink, calories, fats, carbohydrates, proteins);
    }

}
