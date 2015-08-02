package com.zireck.projectk.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.zireck.projectk.R;
import com.zireck.projectk.listener.OnEditFoodFinishedListener;
import com.zireck.projectk.presenter.EditFoodPresenter;
import com.zireck.projectk.presenter.EditFoodPresenterImpl;
import com.zireck.projectk.util.PictureUtils;
import com.zireck.projectk.util.SnackbarUtils;
import com.zireck.projectk.view.EditFoodView;

import butterknife.OnClick;

/**
 * Created by Zireck on 31/07/2015.
 */
public class EditFoodFragment extends AddFoodFragment implements EditFoodView {

    private static final String EXTRA_FOOD_ID = "food_id";

    private OnEditFoodFinishedListener mCallback;
    private EditFoodPresenter mPresenter;

    public static EditFoodFragment newInstance(long foodId) {
        Bundle bundle = new Bundle();
        bundle.putLong(EditFoodFragment.EXTRA_FOOD_ID, foodId);

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new EditFoodPresenterImpl(getActivity(), this);
        mPresenter.mapExtras(getArguments());
        mPresenter.getFood();
    }

    @Override
    protected int getFragmentLayout() {
        return super.getFragmentLayout();
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
    public void onStart() {
        super.onStart();

        //mPresenter.loadPicture();
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("k9d3 EditFoodFragment.onStop()");
        //mPresenter.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PictureUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mPresenter.receivePicture();
            } else {
                mPresenter.doNotReceivePicture();
                SnackbarUtils.showError(getView(), "Picture wasn't taken.");
            }
        }
    }

    @OnClick(R.id.food_picture)
    public void onFoodImageClick() {
        mPresenter.startCamera(getActivity());
    }

    @OnClick(R.id.button_take_picture)
    public void onTakePictureClick() {
        System.out.println("k9d3 clicked @OnClick takePictureclick");
        mPresenter.startCamera(getActivity());
    }

    @OnClick(R.id.button_delete_picture)
    public void onDeletePictureClick() {
        mPresenter.deleteCurrentPicture();
    }

    @Override
    public ImageView getPictureImageView() {
        return mFoodPicture;
    }

    @Override
    public String getPictureCurrentName() {
        if (mFoodPicture.getTag(R.string.picture_current) == null) {
            return "";
        } else {
            return mFoodPicture.getTag(R.string.picture_current).toString();
        }
    }

    @Override
    public String getPictureNewName() {
        if (mFoodPicture.getTag(R.string.picture_new) == null) {
            return "";
        } else {
            return mFoodPicture.getTag(R.string.picture_new).toString();
        }
    }

    @Override
    public void setPictureCurrentName(String fileName) {
        mFoodPicture.setTag(R.string.picture_current, fileName);
    }

    @Override
    public void setPictureNewName(String fileName) {
        mFoodPicture.setTag(R.string.picture_new, fileName);
    }

    @Override
    public String getFoodIdTag() {
        return EditFoodFragment.EXTRA_FOOD_ID;
    }

    @Override
    public void foodSuccessfullyEdited() {
        if (mCallback != null) {
            mCallback.foodEdited();
        } else {
            // TODO it's a tablet, therefore show Snackbar on MainActivity
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

    protected void validateData() {
        String name = mFoodNameEditText.getText().toString();
        String brand = mFoodBrandEditText.getText().toString();
        boolean isDrink = mFoodIsDrinkCheckbox.isChecked();
        String calories = mFoodCaloriesEditText.getText().toString();
        String fats = mFoodFatsEditText.getText().toString();
        String carbohydrates = mFoodCarbohydratesEditText.getText().toString();
        String proteins = mFoodProteinsEditText.getText().toString();
        //String pictureFileName = getPictureCurrentName();

        mPresenter.validateData(name, brand, isDrink, calories, fats, carbohydrates, proteins);
    }

    @Override
    public void deletePicture() {
        mFoodPicture.setImageBitmap(null);
        mFoodPicture.setImageDrawable(null);
    }
}
