package com.zireck.projectk.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.helper.LimitedDecimalsInputFilter;
import com.zireck.projectk.helper.PictureHelper;
import com.zireck.projectk.listener.OnAddFoodFinishedListener;
import com.zireck.projectk.presenter.AddFoodPresenter;
import com.zireck.projectk.presenter.AddFoodPresenterImpl;
import com.zireck.projectk.util.SnackbarUtils;
import com.zireck.projectk.view.AddFoodView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Zireck on 24/07/2015.
 */
public class AddFoodFragment extends BaseFragment implements AddFoodView {

    @Bind(R.id.food_picture) ImageView mFoodPicture;

    @Bind(R.id.food_name_text_input_layout) TextInputLayout mFoodNameTextInputLayout;
    @Bind(R.id.food_name_edit_text) EditText mFoodNameEditText;

    @Bind(R.id.food_brand_text_input_layout) TextInputLayout mFoodBrandTextInputLayout;
    @Bind(R.id.food_brand_edit_text) EditText mFoodBrandEditText;

    @Bind(R.id.food_isdrink_checkbox) CheckBox mFoodIsDrinkCheckbox;

    @Bind(R.id.nutrients) TextView mNutrientsTextView;

    @Bind(R.id.food_calories_text_input_layout) TextInputLayout mFoodCaloriesTextInputLayout;
    @Bind(R.id.food_calories_edit_text) EditText mFoodCaloriesEditText;

    @Bind(R.id.food_fats_text_input_layout) TextInputLayout mFoodFatsTextInputLayout;
    @Bind(R.id.food_fats_edit_text) EditText mFoodFatsEditText;

    @Bind(R.id.food_carbohydrates_text_input_layout) TextInputLayout mFoodCarbohydratesTextInputLayout;
    @Bind(R.id.food_carbohydrates_edit_text) EditText mFoodCarbohydratesEditText;

    @Bind(R.id.food_proteins_text_input_layout) TextInputLayout mFoodProteinsTextInputLayout;
    @Bind(R.id.food_proteins_edit_text) EditText mFoodProteinsEditText;

    private OnAddFoodFinishedListener mCallback;
    private AddFoodPresenter mPresenter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mPresenter = new AddFoodPresenterImpl(getActivity(), this);

        applyDecimalFilters();

        mFoodIsDrinkCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPresenter.isDrink(isChecked);
            }
        });
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_food;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_food, menu);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("k9d3 called!");
        if (requestCode == PictureHelper.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mPresenter.receivePicture();
            } else {
                SnackbarUtils.showError(getView(), "Picture wasn't taken.");
            }
        }
    }

    @OnClick(R.id.food_picture)
    public void onFoodImageClick() {
        //mCallbackOnFoodPicture.launchCamera(String.valueOf(System.currentTimeMillis()) + ".jpg");
        mPresenter.startCamera(getActivity());
    }

    @Override
    public void foodSuccessfullyAdded() {
        if (mCallback != null) {
            mCallback.foodAdded();
        } else {
            // TODO it's a tablet, therefore show Snackbar on MainActivity
        }
    }

    @Override
    public void setGr() {
        setUnits("gr");
    }

    @Override
    public void setMl() {
        setUnits("ml");
    }

    @Override
    public void setNameError() {
        setError(mFoodNameEditText, "name");
    }

    @Override
    public void setBrandError() {
        setError(mFoodBrandEditText, "brand");
    }

    @Override
    public void setCaloriesError() {
        setError(mFoodCaloriesEditText, "calories");
    }

    @Override
    public void setFatsError() {
        setError(mFoodFatsEditText, "fats");
    }

    @Override
    public void setCarbohydratesError() {
        setError(mFoodCarbohydratesEditText, "carbohydrates");
    }

    @Override
    public void setProteinsError() {
        setError(mFoodProteinsEditText, "proteins");
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
    public void startIntentForCameraLaunch(Intent intent, final int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void setPicture(Bitmap picture) {
        mFoodPicture.setImageBitmap(picture);
        /*
        if (picture != null) {
            mFoodPicture.setImageBitmap(picture);
        } else {
            throw new NullPointerException("Picture can't be null");
        }*/
    }

    @Override
    public void deletePicture() {
        mFoodPicture.setImageBitmap(null);
    }

    private void setUnits(String unit) {
        StringBuilder text = new StringBuilder();
        text.append("Energy & Nutrients (per 100");
        text.append(unit);
        text.append(")");
        mNutrientsTextView.setText(text);
    }

    private void setError(EditText editText, String type) {
        editText.setError("Invalid " + type);
    }

    private void validateData() {
        String name = mFoodNameEditText.getText().toString();
        String brand = mFoodBrandEditText.getText().toString();
        boolean isDrink = mFoodIsDrinkCheckbox.isChecked();
        String calories = mFoodCaloriesEditText.getText().toString();
        String fats = mFoodFatsEditText.getText().toString();
        String carbohydrates = mFoodCarbohydratesEditText.getText().toString();
        String proteins = mFoodProteinsEditText.getText().toString();

        mPresenter.validateData(name, brand, isDrink, calories, fats, carbohydrates, proteins);
    }

    private void applyDecimalFilters() {
        InputFilter[] inputFilter = new InputFilter[] { new LimitedDecimalsInputFilter(5, 2) };

        mFoodCaloriesEditText.setFilters(inputFilter);
        mFoodFatsEditText.setFilters(inputFilter);
        mFoodCarbohydratesEditText.setFilters(inputFilter);
        mFoodProteinsEditText.setFilters(inputFilter);
    }
}
