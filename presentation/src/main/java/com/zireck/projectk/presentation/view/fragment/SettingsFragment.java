package com.zireck.projectk.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zireck.projectk.R;
import com.zireck.projectk.presentation.dagger.component.FoodComponent;
import com.zireck.projectk.presentation.enumeration.ActivityFactor;
import com.zireck.projectk.presentation.enumeration.Gender;
import com.zireck.projectk.presentation.enumeration.Goal;
import com.zireck.projectk.presentation.model.UserModel;
import com.zireck.projectk.presentation.presenter.SettingsPresenter;
import com.zireck.projectk.presentation.util.DateUtils;
import com.zireck.projectk.presentation.util.MathUtils;
import com.zireck.projectk.presentation.util.SnackbarUtils;
import com.zireck.projectk.presentation.view.SettingsView;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Zireck on 11/09/2015.
 */
public class SettingsFragment extends BaseFragment implements SettingsView,
        NumberPickerDialogFragment.NumberPickerDialogHandler,
        DatePickerDialog.OnDateSetListener {

    @Inject SettingsPresenter mPresenter;

    @Bind(R.id.user_name) TextView mUserName;
    @Bind(R.id.user_weight) TextView mUserWeight;
    @Bind(R.id.user_height) TextView mUserHeight;
    @Bind(R.id.user_gender) TextView mUserGender;
    @Bind(R.id.user_birthday) TextView mUserBirthday;
    @Bind(R.id.user_activity_factor) TextView mUserActivityFactor;
    @Bind(R.id.user_goal) TextView mUserGoal;

    private NumberPickerBuilder mNumberPickerBuilder;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // TODO: need callback?
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: need menu items?
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    private void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mPresenter.setView(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_settings;
    }

    @OnClick(R.id.layout_name)
    public void onClickName() {
        new MaterialDialog.Builder(getActivity())
                .theme(Theme.LIGHT)
                .title("Enter your name")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Name", mPresenter.getCurrentName(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        mPresenter.setCurrentName(input.toString());
                    }
                }).show();
    }

    @OnClick(R.id.layout_weight)
    public void onClickWeight() {
        mNumberPickerBuilder = new NumberPickerBuilder()
                .setReference(1)
                .setFragmentManager(getChildFragmentManager())
                .setTargetFragment(this)
                .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                .setPlusMinusVisibility(View.INVISIBLE)
                .setDecimalVisibility(View.VISIBLE)
                .setCurrentNumber(mPresenter.getCurrentWeight())
                .setLabelText(getActivity().getString(R.string.kilogram_short));

        mNumberPickerBuilder.show();
    }

    @OnClick(R.id.layout_height)
    public void onClickHeight() {
        mNumberPickerBuilder = new NumberPickerBuilder()
                .setReference(2)
                .setFragmentManager(getChildFragmentManager())
                .setTargetFragment(this)
                .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                .setPlusMinusVisibility(View.INVISIBLE)
                .setDecimalVisibility(View.INVISIBLE)
                .setCurrentNumber(mPresenter.getCurrentHeight())
                .setLabelText(getActivity().getString(R.string.centimeter_short));

        mNumberPickerBuilder.show();
    }

    @OnClick(R.id.layout_gender)
    public void onClickGender() {
        new MaterialDialog.Builder(getActivity())
                .theme(Theme.LIGHT)
                .title("Gender")
                .items(Gender.getStringValues(getActivity()))
                .itemsCallbackSingleChoice(mPresenter.getCurrentGender() - 1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Gender gender = Gender.fromValue(getActivity(), text.toString());
                        if (gender != null) {
                            mPresenter.setCurrentGender(gender.getIntValue());
                        }

                        return true;
                    }
                })
                .positiveText("Done")
                .show();
    }

    @OnClick(R.id.layout_birthday)
    public void onClickBirthday() {
        int year, month, dayOfMonth;

        Date currentBirthday = mPresenter.getCurrentBirthday();
        if (currentBirthday != null) {
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentBirthday);
            year = currentCalendar.get(Calendar.YEAR);
            month = currentCalendar.get(Calendar.MONTH);
            dayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);
        } else {
            Calendar now = Calendar.getInstance();
            year = now.get(Calendar.YEAR);
            month = now.get(Calendar.MONTH);
            dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                year,
                month,
                dayOfMonth
        );

        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(R.id.layout_activity_factor)
    public void onClickActivityFactor() {
        new MaterialDialog.Builder(getActivity())
                .theme(Theme.LIGHT)
                .title("Activity Level")
                .items(ActivityFactor.getStringValues(getActivity()))
                .itemsCallbackSingleChoice(mPresenter.getCurrentActivityFactor() - 1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        ActivityFactor activityFactor = ActivityFactor.fromValue(getActivity(), text.toString());
                        if (activityFactor != null) {
                            mPresenter.setCurrentActivityFactor(activityFactor.getIntValue());
                        }

                        return true;
                    }
                })
                .positiveText("Done")
                .show();
    }

    @OnClick(R.id.layout_goal)
    public void onClickGoal() {
        new MaterialDialog.Builder(getActivity())
                .theme(Theme.LIGHT)
                .title("Goal")
                .items(Goal.getStringValues(getActivity()))
                .itemsCallbackSingleChoice(mPresenter.getCurrentGoal() - 1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Goal goal = Goal.fromValue(getActivity(), text.toString());
                        if (goal != null) {
                            mPresenter.setCurrentGoal(goal.getIntValue());
                        }

                        return true;
                    }
                })
                .positiveText("Done")
                .show();
    }

    @Override
    public void onDialogNumberSet(int reference, int number, double decimal, boolean isNegative,
                                  double fullNumber) {
        if (reference == 1) {
            mPresenter.setCurrentWeight(fullNumber);
        } else if (reference == 2) {
            mPresenter.setCurrentHeight(number);
        }
    }

    @Override
    public void onDateSet(
            DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        mPresenter.setCurrentBirthday(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void renderUserData(UserModel userModel) {
        String notSet = "(not set)";

        if (userModel == null) {
            mUserName.setText(notSet);
            mUserWeight.setText(notSet);
            mUserHeight.setText(notSet);
            mUserGender.setText(notSet);
            mUserBirthday.setText(notSet);
            mUserActivityFactor.setText(notSet);
            mUserGoal.setText(notSet);

            SnackbarUtils.showError(getView(), "Please, enter your personal information.");
            return;
        }

        if (userModel.isValidName()) {
            mUserName.setText(userModel.getName());
        } else {
            mUserName.setText(notSet);
        }

        if (userModel.isValidWeight()) {
            mUserWeight.setText(MathUtils.formatDouble(userModel.getWeight()) + " " + getActivity().getString(R.string.kilogram_short));
        } else {
            mUserWeight.setText(notSet);
        }

        if (userModel.isValidHeight()) {
            mUserHeight.setText(userModel.getHeight() + " " + getActivity().getString(R.string.centimeter_short));
        } else {
            mUserHeight.setText(notSet);
        }

        Gender gender = Gender.fromValue(userModel.getGender());
        if (gender != null) {
            mUserGender.setText(gender.toString(getActivity()));
        } else {
            mUserGender.setText(notSet);
        }

        if (userModel.isValidBirthday()) {
            //mUserBirthday.setText(userModel.getBirthday().toString());
            String birthday = DateUtils.getFormattedBirthdayDate(userModel.getBirthday());
            mUserBirthday.setText(birthday);
        } else {
            mUserBirthday.setText(notSet);
        }

        ActivityFactor activityFactor = ActivityFactor.fromValue(userModel.getActivityFactor());
        if (activityFactor != null) {
            mUserActivityFactor.setText(activityFactor.toString(getActivity()));
        } else {
            mUserActivityFactor.setText(notSet);
        }

        Goal goal = Goal.fromValue(userModel.getGoal());
        if (goal != null) {
            mUserGoal.setText(goal.toString(getActivity()));
        } else {
            mUserGoal.setText(notSet);
        }

    }

    @Override
    public void showError(String message) {
        SnackbarUtils.showError(getView(), message);
    }
}
