package com.zireck.projectk.fragment;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zireck.projectk.R;
import com.zireck.projectk.helper.LimitedDecimalsInputFilter;
import com.zireck.projectk.listener.OnAddFoodFinishedListener;
import com.zireck.projectk.presenter.AddFoodPresenter;
import com.zireck.projectk.presenter.AddFoodPresenterImpl;
import com.zireck.projectk.util.PictureUtils;
import com.zireck.projectk.util.SnackbarUtils;
import com.zireck.projectk.view.AddFoodView;

import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Zireck on 24/07/2015.
 */
public class AddFoodFragment extends BaseFragment implements AddFoodView {

    @Bind(R.id.food_picture) ImageView mFoodPicture;

    @Bind(R.id.layout_take_picture) LinearLayout mLayoutTakePicture;
    @Bind(R.id.layout_delete_picture) LinearLayout mLayoutDeletePicture;
    @Bind(R.id.button_take_picture) Button mButtonTakePicture;
    @Bind(R.id.button_delete_picture) Button mButtonDeletePicture;

    @Bind(R.id.food_name_icon) MaterialIconView mFoodNameIcon;
    @Bind(R.id.food_name_text_input_layout) TextInputLayout mFoodNameTextInputLayout;
    @Bind(R.id.food_name_edit_text) EditText mFoodNameEditText;

    @Bind(R.id.food_brand_icon) MaterialIconView mFoodBrandIcon;
    @Bind(R.id.food_brand_text_input_layout) TextInputLayout mFoodBrandTextInputLayout;
    @Bind(R.id.food_brand_edit_text) EditText mFoodBrandEditText;

    @Bind(R.id.food_isdrink_icon) MaterialIconView mFoodIsDrinkIcon;
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

        //initEditTextFocusListeners();
        initEditTextFocusListenersWeird();

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
                mPresenter.receivePicture();
            } else {
                mPresenter.doNotReceivePicture();
                SnackbarUtils.showError(getView(), "Picture wasn't taken.");
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        //mPresenter.onStop();
    }

    @OnClick(R.id.food_picture)
    public void onFoodImageClick() {
        mPresenter.startCamera(getActivity());
    }

    @OnClick(R.id.button_take_picture)
    public void onTakePictureClick() {
        mPresenter.startCamera(getActivity());
    }

    @OnClick(R.id.button_delete_picture)
    public void onDeletePictureClick() {
        mPresenter.deleteCurrentPicture();
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
    public void deletePicture() {
        mFoodPicture.setImageBitmap(null);
        mFoodPicture.setImageDrawable(null);
    }

    @Override
    public ImageView getPictureImageView() {
        return mFoodPicture;
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
                        mPresenter.hasFocus(editText);
                    } else {
                        mPresenter.lostFocus(editText);
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
                    mPresenter.hasFocus(nameEditText);
                } else {
                    mPresenter.lostFocus(nameEditText);
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
                    mPresenter.hasFocus(brandEditText);
                } else {
                    mPresenter.lostFocus(brandEditText);
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
                    mPresenter.hasFocus(caloriesEditText);
                } else {
                    mPresenter.lostFocus(caloriesEditText);
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
                    mPresenter.hasFocus(fatsEditText);
                } else {
                    mPresenter.lostFocus(fatsEditText);
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
                    mPresenter.hasFocus(carbohydratesEditText);
                } else {
                    mPresenter.lostFocus(carbohydratesEditText);
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
                    mPresenter.hasFocus(proteinsEditText);
                } else {
                    mPresenter.lostFocus(proteinsEditText);
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
        //String pictureFileName = getPictureCurrentName();

        mPresenter.validateData(name, brand, isDrink, calories, fats, carbohydrates, proteins);
    }

    protected void applyDecimalFilters() {
        InputFilter[] inputFilter = new InputFilter[] { new LimitedDecimalsInputFilter(5, 2) };

        mFoodCaloriesEditText.setFilters(inputFilter);
        mFoodFatsEditText.setFilters(inputFilter);
        mFoodCarbohydratesEditText.setFilters(inputFilter);
        mFoodProteinsEditText.setFilters(inputFilter);
    }

    public void activityStopped() {
        //mPresenter.deleteCurrentPicture();
    }
}
