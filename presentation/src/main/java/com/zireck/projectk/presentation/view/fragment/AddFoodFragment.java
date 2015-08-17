package com.zireck.projectk.presentation.view.fragment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.helper.LimitedDecimalsInputFilter;
import com.zireck.projectk.presentation.listener.OnAddFoodFinishedListener;
import com.zireck.projectk.presentation.presenter.AddFoodPresenter;
import com.zireck.projectk.presentation.util.PictureUtils;
import com.zireck.projectk.presentation.util.SnackbarUtils;
import com.zireck.projectk.presentation.view.AddFoodView;

import net.steamcrafted.materialiconlib.MaterialIconView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Zireck on 24/07/2015.
 */
public class AddFoodFragment extends BaseFragment implements AddFoodView {

    @Bind(R.id.food_picture) ImageView mFoodPicture;

    @Bind(R.id.layout_delete_picture) LinearLayout mLayoutDeletePicture;

    @Bind(R.id.food_name_icon) MaterialIconView mFoodNameIcon;
    @Bind(R.id.food_name_text_input_layout) TextInputLayout mFoodNameTextInputLayout;
    @Bind(R.id.food_name_edit_text) EditText mFoodNameEditText;

    @Bind(R.id.food_brand_icon) MaterialIconView mFoodBrandIcon;
    @Bind(R.id.food_brand_text_input_layout) TextInputLayout mFoodBrandTextInputLayout;
    @Bind(R.id.food_brand_edit_text) EditText mFoodBrandEditText;

    @Bind(R.id.food_isdrink_checkbox) CheckBox mFoodIsDrinkCheckbox;

    @Bind(R.id.nutrients) TextView mNutrientsTextView;

    @Bind(R.id.food_calories_icon) MaterialIconView mFoodCaloriesIcon;
    @Bind(R.id.food_calories_text_input_layout) TextInputLayout mFoodCaloriesTextInputLayout;
    @Bind(R.id.food_calories_edit_text) EditText mFoodCaloriesEditText;

    @Bind(R.id.food_nutrients_icon) MaterialIconView mFoodNutrientsIcon;

    @Bind(R.id.food_fats_text_input_layout) TextInputLayout mFoodFatsTextInputLayout;
    @Bind(R.id.food_fats_edit_text) EditText mFoodFatsEditText;

    @Bind(R.id.food_carbohydrates_text_input_layout) TextInputLayout mFoodCarbohydratesTextInputLayout;
    @Bind(R.id.food_carbohydrates_edit_text) EditText mFoodCarbohydratesEditText;

    @Bind(R.id.food_proteins_text_input_layout) TextInputLayout mFoodProteinsTextInputLayout;
    @Bind(R.id.food_proteins_edit_text) EditText mFoodProteinsEditText;

    private OnAddFoodFinishedListener mCallback;
    //private OldAddFoodPresenter mPresenter;
    @Inject AddFoodPresenter mAddFoodPresenter;

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

        hideKeyboard();
        //initEditTextFocusListeners();
        initEditTextFocusListenersWeird();
        applyDecimalFilters();
        initDrinkCheckBox();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    private void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mAddFoodPresenter.setView(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_food;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_food, menu);
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

    @OnClick(R.id.food_picture)
    public void onFoodImageClick() {
        mAddFoodPresenter.startCamera(getActivity());
    }

    @OnClick(R.id.button_take_picture)
    public void onTakePictureClick() {
        mAddFoodPresenter.startCamera(getActivity());
    }

    @OnClick(R.id.button_delete_picture)
    public void onDeletePictureClick() {
        mAddFoodPresenter.deleteCurrentPicture();
    }

    @Override
    public void foodSuccessfullyAdded() {
        if (mCallback != null) {
            mCallback.foodAdded();
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
    public MaterialIconView getIconName() {
        return mFoodNameIcon;
    }

    @Override
    public MaterialIconView getIconBrand() {
        return mFoodBrandIcon;
    }

    @Override
    public MaterialIconView getIconCalories() {
        return mFoodCaloriesIcon;
    }

    @Override
    public MaterialIconView getIconNutrients() {
        return mFoodNutrientsIcon;
    }

    @Override
    public void startIntentForCameraLaunch(Intent intent, final int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public ImageView getPictureImageView() {
        return mFoodPicture;
    }

    @Override
    public void deletePicture() {
        mFoodPicture.setImageBitmap(null);
        mFoodPicture.setImageDrawable(null);
    }

    @Override
    public void showDeletePictureLayout() {
        mLayoutDeletePicture.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDeletePictureLayout() {
        mLayoutDeletePicture.setVisibility(View.GONE);
    }

    private void initEditTextFocusListeners() {
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v instanceof EditText) {
                    EditText editText = (EditText) v;
                    if (hasFocus) {
                        mAddFoodPresenter.hasFocus(editText);
                    } else {
                        mAddFoodPresenter.lostFocus(editText);
                    }
                }
            }
        };

        mFoodNameEditText.setOnFocusChangeListener(onFocusChangeListener);
        mFoodBrandEditText.setOnFocusChangeListener(onFocusChangeListener);
        mFoodCaloriesEditText.setOnFocusChangeListener(onFocusChangeListener);
        mFoodFatsEditText.setOnFocusChangeListener(onFocusChangeListener);
        mFoodCarbohydratesEditText.setOnFocusChangeListener(onFocusChangeListener);
        mFoodProteinsEditText.setOnFocusChangeListener(onFocusChangeListener);
    }

    @Deprecated
    private void initEditTextFocusListenersWeird() {
        // https://code.google.com/p/android/issues/detail?id=178693

        final EditText nameEditText = mFoodNameTextInputLayout.getEditText();
        final View.OnFocusChangeListener nameExistingOnFocusChangeListener = nameEditText.getOnFocusChangeListener();
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                nameExistingOnFocusChangeListener.onFocusChange(v, hasFocus);

                if (hasFocus) {
                    mAddFoodPresenter.hasFocus(nameEditText);
                } else {
                    mAddFoodPresenter.lostFocus(nameEditText);
                }
            }
        });

        final EditText brandEditText = mFoodBrandTextInputLayout.getEditText();
        final View.OnFocusChangeListener brandExistingOnFocusChangeListener = brandEditText.getOnFocusChangeListener();
        brandEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                brandExistingOnFocusChangeListener.onFocusChange(v, hasFocus);

                if (hasFocus) {
                    mAddFoodPresenter.hasFocus(brandEditText);
                } else {
                    mAddFoodPresenter.lostFocus(brandEditText);
                }
            }
        });

        final EditText caloriesEditText = mFoodCaloriesTextInputLayout.getEditText();
        final View.OnFocusChangeListener caloriesExistingOnFocusChangeListener = caloriesEditText.getOnFocusChangeListener();
        caloriesEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                caloriesExistingOnFocusChangeListener.onFocusChange(v, hasFocus);

                if (hasFocus) {
                    mAddFoodPresenter.hasFocus(caloriesEditText);
                } else {
                    mAddFoodPresenter.lostFocus(caloriesEditText);
                }
            }
        });

        final EditText fatsEditText = mFoodFatsTextInputLayout.getEditText();
        final View.OnFocusChangeListener fatsExistingOnFocusChangeListener = fatsEditText.getOnFocusChangeListener();
        fatsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                fatsExistingOnFocusChangeListener.onFocusChange(v, hasFocus);

                if (hasFocus) {
                    mAddFoodPresenter.hasFocus(fatsEditText);
                } else {
                    mAddFoodPresenter.lostFocus(fatsEditText);
                }
            }
        });

        final EditText carbohydratesEditText = mFoodCarbohydratesTextInputLayout.getEditText();
        final View.OnFocusChangeListener carbohydratesExistingOnFocusChangeListener = carbohydratesEditText.getOnFocusChangeListener();
        carbohydratesEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                carbohydratesExistingOnFocusChangeListener.onFocusChange(v, hasFocus);

                if (hasFocus) {
                    mAddFoodPresenter.hasFocus(carbohydratesEditText);
                } else {
                    mAddFoodPresenter.lostFocus(carbohydratesEditText);
                }
            }
        });

        final EditText proteinsEditText = mFoodProteinsTextInputLayout.getEditText();
        final View.OnFocusChangeListener proteinsExistingOnFocusChangeListener = proteinsEditText.getOnFocusChangeListener();
        proteinsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                proteinsExistingOnFocusChangeListener.onFocusChange(v, hasFocus);

                if (hasFocus) {
                    mAddFoodPresenter.hasFocus(proteinsEditText);
                } else {
                    mAddFoodPresenter.lostFocus(proteinsEditText);
                }
            }
        });

    }

    protected void setUnits(String unit) {
        StringBuilder text = new StringBuilder();
        text.append("Energy & Nutrients (per 100");
        text.append(unit);
        text.append(")");
        mNutrientsTextView.setText(text);
    }

    protected void setError(EditText editText, String type) {
        editText.setError("Invalid " + type);
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

    protected void applyDecimalFilters() {
        InputFilter[] inputFilter = new InputFilter[] { new LimitedDecimalsInputFilter(5, 2) };

        mFoodCaloriesEditText.setFilters(inputFilter);
        mFoodFatsEditText.setFilters(inputFilter);
        mFoodCarbohydratesEditText.setFilters(inputFilter);
        mFoodProteinsEditText.setFilters(inputFilter);
    }

    protected void hideKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    protected void initDrinkCheckBox() {
        mFoodIsDrinkCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAddFoodPresenter.isDrink(isChecked);
            }
        });
    }
}
