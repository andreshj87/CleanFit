package com.zireck.projectk.presentation.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.listener.OnAddFoodFinishedListener;
import com.zireck.projectk.presentation.presenter.AddEditFoodPresenter;
import com.zireck.projectk.presentation.presenter.AddFoodPresenter;
import com.zireck.projectk.presentation.util.PictureUtils;
import com.zireck.projectk.presentation.util.SnackbarUtils;
import com.zireck.projectk.presentation.view.AddFoodView;

import javax.inject.Inject;

/**
 * Created by Zireck on 24/07/2015.
 */
public class AddFoodFragment extends AddEditFoodFragment implements AddFoodView {

    private OnAddFoodFinishedListener mCallback;
    @Inject AddFoodPresenter mAddFoodPresenter;

    public static AddFoodFragment newInstance() {
        return new AddFoodFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnAddFoodFinishedListener) activity;
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
            if (resultCode == Activity.RESULT_OK) {
                mAddFoodPresenter.receivePicture();
            } else {
                SnackbarUtils.showError(getView(), "Picture wasn't taken.");
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
        menu.findItem(R.id.action_save).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                validateData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected AddEditFoodPresenter getPresenter() {
        return mAddFoodPresenter;
    }

    @Override
    public void foodSuccessfullyAdded() {
        if (mCallback != null) {
            mCallback.foodAdded();
        }
    }

    @Override
    public void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mAddFoodPresenter.setView(this);
    }

    protected void validateData() {
        String name = mFoodNameEditText.getText().toString();
        String brand = mFoodBrandEditText.getText().toString();
        boolean isDrink = mFoodIsDrinkCheckbox.isChecked();
        String calories = mFoodCaloriesEditText.getText().toString();
        String fats = mFoodFatsEditText.getText().toString();
        String carbohydrates = mFoodCarbohydratesEditText.getText().toString();
        String proteins = mFoodProteinsEditText.getText().toString();

        mAddFoodPresenter.validateData(name, brand, isDrink, calories, fats, carbohydrates, proteins);
    }
}
