package com.zireck.projectk.presentation.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.presentation.helper.LimitedDecimalsInputFilter;
import com.zireck.projectk.presentation.presenter.AddEditFoodPresenter;
import com.zireck.projectk.presentation.util.PictureUtils;
import com.zireck.projectk.presentation.util.SnackbarUtils;
import com.zireck.projectk.presentation.view.AddEditFoodView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Zireck on 18/08/2015.
 */
public abstract class AddEditFoodFragment extends BaseFragment implements AddEditFoodView {

    @Bind(R.id.food_picture) ImageView mFoodPicture;

    @Bind(R.id.button_picture_take) Button mButtonPictureTake;
    @Bind(R.id.button_picture_delete) Button mButtonPictureDelete;
    @Bind(R.id.icon_camera) MaterialIconView mIconCamera;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initButtonIcons();
        hideKeyboard();
        applyDecimalFilters();
        initDrinkCheckBox();
        //initEditTextFocusListenersWeird();
        initEditTextFocusListeners();
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
                getPresenter().receivePicture();
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
        inflater.inflate(R.menu.menu_add_edit, menu);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_edit_food;
    }

    protected abstract AddEditFoodPresenter getPresenter();


    @OnClick(R.id.food_picture)
    public void onFoodImageClick() {
        getPresenter().startCamera(getActivity());
    }

    @OnClick(R.id.button_picture_take)
    public void onTakePictureClick() {
        getPresenter().startCamera(getActivity());
    }

    @OnClick(R.id.button_picture_delete)
    public void onDeletePictureClick() {
        getPresenter().deleteCurrentPicture();
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
    public ImageView getPictureImageView() {
        return mFoodPicture;
    }

    @Override
    public void startIntentForCameraLaunch(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void deletePicture() {
        mFoodPicture.setImageBitmap(null);
        mFoodPicture.setImageDrawable(null);
    }

    @Override
    public void showDeletePictureLayout() {
        mButtonPictureDelete.setVisibility(View.VISIBLE);
        mIconCamera.setVisibility(View.GONE);
    }

    @Override
    public void hideDeletePictureLayout() {
        mButtonPictureDelete.setVisibility(View.INVISIBLE);
        mIconCamera.setVisibility(View.VISIBLE);
    }

    public abstract void initialize();

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

    private void initButtonIcons() {
        MaterialIconView icon = new MaterialIconView(getActivity());
        icon.setColorResource(R.color.primary);
        icon.setIcon(MaterialDrawableBuilder.IconValue.CAMERA);
        mButtonPictureTake.setCompoundDrawables(icon.getDrawable(), null, null, null);
        mButtonPictureTake.setCompoundDrawablePadding(4);

        icon.setIcon(MaterialDrawableBuilder.IconValue.CLOSE);
        mButtonPictureDelete.setCompoundDrawables(icon.getDrawable(), null, null, null);
        mButtonPictureDelete.setCompoundDrawablePadding(4);
    }

    protected void hideKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    protected void applyDecimalFilters() {
        InputFilter[] inputFilter = new InputFilter[] { new LimitedDecimalsInputFilter(5, 2) };

        mFoodCaloriesEditText.setFilters(inputFilter);
        mFoodFatsEditText.setFilters(inputFilter);
        mFoodCarbohydratesEditText.setFilters(inputFilter);
        mFoodProteinsEditText.setFilters(inputFilter);
    }

    protected void initDrinkCheckBox() {
        mFoodIsDrinkCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getPresenter().isDrink(isChecked);
            }
        });
    }

    private void initEditTextFocusListeners() {
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v instanceof EditText) {
                    EditText editText = (EditText) v;
                    if (hasFocus) {
                        getPresenter().hasFocus(editText);
                    } else {
                        getPresenter().lostFocus(editText);
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
                    getPresenter().hasFocus(nameEditText);
                } else {
                    getPresenter().lostFocus(nameEditText);
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
                    getPresenter().hasFocus(brandEditText);
                } else {
                    getPresenter().lostFocus(brandEditText);
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
                    getPresenter().hasFocus(caloriesEditText);
                } else {
                    getPresenter().lostFocus(caloriesEditText);
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
                    getPresenter().hasFocus(fatsEditText);
                } else {
                    getPresenter().lostFocus(fatsEditText);
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
                    getPresenter().hasFocus(carbohydratesEditText);
                } else {
                    getPresenter().lostFocus(carbohydratesEditText);
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
                    getPresenter().hasFocus(proteinsEditText);
                } else {
                    getPresenter().lostFocus(proteinsEditText);
                }
            }
        });
    }
}
