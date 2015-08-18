package com.zireck.projectk.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.view.adapter.FoodSpinnerAdapter;
import com.zireck.projectk.presentation.enumeration.Mealtime;
import com.zireck.projectk.presentation.model.FoodModel;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.util.SnackbarUtils;
import com.zireck.projectk.presentation.view.AddMealView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Zireck on 06/08/2015.
 */
public class AddMealFragment extends BaseFragment implements AddMealView,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        NumberPickerDialogFragment.NumberPickerDialogHandler {

    @Bind(R.id.root_layout) LinearLayout mRootLayout;
    @Bind(R.id.spinner_food) MaterialSpinner mSpinnerFood;

    @Bind(R.id.layout_date) LinearLayout mLayoutDate;
    @Bind(R.id.layout_time) LinearLayout mLayoutTime;
    @Bind(R.id.layout_daily) LinearLayout mLayoutDaily;
    @Bind(R.id.layout_amount) LinearLayout mLayoutAmount;

    @Bind(R.id.icon_date) ImageView mIconDate;
    @Bind(R.id.icon_time) ImageView mIconTime;
    @Bind(R.id.icon_daily) ImageView mIconDaily;
    @Bind(R.id.icon_amount) ImageView mIconAmount;

    @Bind(R.id.text_date) TextView mTextDate;
    @Bind(R.id.text_time) TextView mTextTime;
    @Bind(R.id.text_daily) TextView mTextDaily;
    @Bind(R.id.text_amount) TextView mTextAmount;

    @Bind(R.id.nutrients) TextView mNutrients;

    /*
    @Bind(R.id.button_meal_date) Button mButtonDate;
    @Bind(R.id.button_meal_time) Button mButtonTime;
    @Bind(R.id.button_meal_daily) Button mButtonDaily;
    @Bind(R.id.button_meal_amount) Button mButtonAmount;*/
    @Bind(R.id.meal_calories) TextView mMealCalories;
    @Bind(R.id.meal_fats) TextView mMealFats;
    @Bind(R.id.meal_carbohydrates) TextView mMealCarbohydrates;
    @Bind(R.id.meal_proteins) TextView mMealProteins;

    private FoodSpinnerAdapter mAdapter;
    //private AddMealPresenter mPresenter;

    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private MaterialDialog mMaterialDialog;
    private NumberPickerBuilder mNumberPickerBuilder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initIcons();
        initSpinner();
        initDateTime();

        mTextDaily.setText("Breakfast");
        mTextAmount.setText("100gr");

        //mPresenter = new AddMealPresenterImpl(this);
        //mPresenter.initialize();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_add_meal;
    }

    @Override
    public void setSpinnerFoodItems(List<FoodModel> foodItems) {
        mAdapter.setFoods(foodItems);
    }

    @Override
    public void setDateText(String date) {
        mTextDate.setText(date);
    }

    @Override
    public void setTimeText(String time) {
        mTextTime.setText(time);
    }

    @Override
    public void setAmountText(String amount) {
        mTextAmount.setText(amount);
    }

    @Override
    public void setCaloriesText(String calories) {
        mMealCalories.setText(calories);
    }

    @Override
    public void setFatsText(String fats) {
        mMealFats.setText(fats);
    }

    @Override
    public void setCarbohydratesText(String carbohydrates) {
        mMealCarbohydrates.setText(carbohydrates);
    }

    @Override
    public void setProteinsText(String proteins) {
        mMealProteins.setText(proteins);
    }

    @Override
    public FoodModel getFood() {
        int actualPosition = mSpinnerFood.getSelectedItemPosition() - 1;
        if (actualPosition < 0) {
            return null;
        }

        return mAdapter.getItem(actualPosition);
    }

    @Override
    public String getAmount() {
        return mTextAmount.getText().toString();
    }

    @Override
    public void setFoodError() {
        mSpinnerFood.setError("Invalid food");
        setError("food");
    }

    @Override
    public void setDateError() {
        setError("date");
    }

    @Override
    public void setTimeError() {
        setError("time");
    }

    @Override
    public void setDailyError() {
        setError("daily meal");
    }

    @Override
    public void setAmountError() {
        setError("amount");
    }

    @Override
    public void setGr() {
        setNutrientsHeader("GR");
        mSpinnerFood.setFloatingLabelText("Food");
    }

    @Override
    public void setMl() {
        setNutrientsHeader("ML");
        mSpinnerFood.setFloatingLabelText("Drink");
    }

    private void setNutrientsHeader(String measure) {
        mNutrients.setText("ENERGY & NUTRIENTS (PER 100" + measure + ")");
    }

    private void setError(String what) {
        SnackbarUtils.showShortMessage(mRootLayout, "Invalid " + what);
    }

    @OnClick(R.id.layout_date)
    public void onDateClick() {
        int year, month, dayOfMonth;

        /*
        Date currentDate = mPresenter.getCurrentDate();
        if (currentDate != null) {
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentDate);
            year = currentCalendar.get(Calendar.YEAR);
            month = currentCalendar.get(Calendar.MONTH);
            dayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);
        } else {
            Calendar now = Calendar.getInstance();
            year = now.get(Calendar.YEAR);
            month = now.get(Calendar.MONTH);
            dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        }

        mDatePickerDialog = DatePickerDialog.newInstance(
                this,
                year,
                month,
                dayOfMonth
        );

        mDatePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
        */
    }

    @OnClick(R.id.layout_time)
    public void onTimeClick() {
        int hourOfDay, minute;

        /*
        Date currentTime = mPresenter.getCurrentTime();
        if (currentTime != null) {
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);
            hourOfDay = currentCalendar.get(Calendar.HOUR_OF_DAY);
            minute = currentCalendar.get(Calendar.MINUTE);

        } else {
            Calendar now = Calendar.getInstance();
            hourOfDay = now.get(Calendar.HOUR_OF_DAY);
            minute = now.get(Calendar.MINUTE);
        }

        mTimePickerDialog = TimePickerDialog.newInstance(
                this,
                hourOfDay,
                minute,
                true
        );

        mTimePickerDialog.show(getActivity().getFragmentManager(), "Timepickerdialog");*/
    }

    @OnClick(R.id.layout_daily)
    public void onDailyClick() {
        mMaterialDialog = new MaterialDialog.Builder(getActivity())
                .title("Daily Meal")
                .items(Mealtime.getValues())
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int which,
                                               CharSequence text) {
                        mTextDaily.setText(text);
                        return false;
                    }
                })
                .theme(Theme.LIGHT)
                .widgetColorRes(R.color.primary)
                .positiveText("Choose")
                .positiveColorRes(R.color.primary)
                .negativeText("Cancel")
                .negativeColorRes(R.color.primary)
                .show();
    }

    @OnClick(R.id.layout_amount)
    public void onAmountClick() {
        mNumberPickerBuilder = new NumberPickerBuilder()
                .setFragmentManager(getChildFragmentManager())
                .setTargetFragment(this)
                .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                .setPlusMinusVisibility(View.INVISIBLE)
                .setDecimalVisibility(View.INVISIBLE)
                .setCurrentNumber(MathUtils.getAmountFromText(mTextAmount.getText().toString()))
                .setLabelText("ml"); // TODO

        mNumberPickerBuilder.show();
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        //mPresenter.setDate(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        //mPresenter.setTime(hourOfDay, minute);
    }

    @Override
    public void onDialogNumberSet(int reference, int number, double decimal, boolean isNegative,
                                  double fullNumber) {
        //mPresenter.setAmount(number);
    }

    private void initIcons() {
        MaterialIconView icon = new MaterialIconView(getActivity());
        icon.setIcon(MaterialDrawableBuilder.IconValue.CALENDAR_TEXT);
        icon.setColorResource(R.color.primary);
        mIconDate.setImageDrawable(icon.getDrawable());

        icon.setIcon(MaterialDrawableBuilder.IconValue.CALENDAR_CLOCK);
        mIconTime.setImageDrawable(icon.getDrawable());

        icon.setIcon(MaterialDrawableBuilder.IconValue.MORE);
        mIconDaily.setImageDrawable(icon.getDrawable());

        icon.setIcon(MaterialDrawableBuilder.IconValue.WEIGHT);
        mIconAmount.setImageDrawable(icon.getDrawable());
    }

    private void initSpinner() {
        mAdapter = new FoodSpinnerAdapter(getActivity(), FoodSpinnerAdapter.SPINNER_ITEM_LAYOUT);
        mSpinnerFood.setAdapter(mAdapter);
        mSpinnerFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0) {
                    //mPresenter.setFoodModel(null);
                    return;
                }

                //mPresenter.setFoodModel((FoodModel) mSpinnerFood.getItemAtPosition(position + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //mPresenter.setFoodModel(null);
            }
        });
    }

    private void initDateTime() {
        Calendar now = Calendar.getInstance();
        now.get(Calendar.DATE);
        //mPresenter.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        //mPresenter.setTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE));
    }
}
