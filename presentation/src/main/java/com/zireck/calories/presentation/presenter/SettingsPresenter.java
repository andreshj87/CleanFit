package com.zireck.calories.presentation.presenter;

import android.support.annotation.NonNull;

import com.zireck.calories.presentation.mapper.UserModelDataMapper;
import com.zireck.calories.presentation.util.DateUtils;
import com.zireck.calories.domain.interactor.DefaultSubscriber;
import com.zireck.calories.domain.interactor.InsertUser;
import com.zireck.calories.domain.interactor.Interactor;
import com.zireck.calories.presentation.model.UserModel;
import com.zireck.calories.presentation.view.SettingsView;
import com.zireck.calories.presentation.view.View;

import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Zireck on 11/09/2015.
 */
public class SettingsPresenter implements Presenter {

    private SettingsView mView;
    private Interactor mGetUserDetails;
    private InsertUser mInsertUser;
    private UserModelDataMapper mUserModelDataMapper;

    private UserModel mUserModel;

    @Inject
    public SettingsPresenter(@Named("userDetails") Interactor getUserDetails,
                             @Named("insertUser") InsertUser insertUser,
                             UserModelDataMapper userModelDataMapper) {
        mGetUserDetails = getUserDetails;
        mInsertUser = insertUser;
        mUserModelDataMapper = userModelDataMapper;

        mUserModel = new UserModel();
    }

    @Override
    public <T extends View> void setView(@NonNull T view) {
        mView = ((SettingsView) view);
    }

    @Override
    public void resume() {
        mGetUserDetails.execute(new GetUserDetailsSubscriber());
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mGetUserDetails.unsubscribe();
        mInsertUser.unsubscribe();
    }

    private void showUserDataInView(UserModel userModel) {
        mView.renderUserData(userModel);
    }

    public String getCurrentName() {
        return mUserModel.getName();
    }

    public void setCurrentName(String name) {
        if (!mUserModel.isValidName(name)) {
            showErrorInView("Invalid Name");
            return;
        }

        mUserModel.setName(name);
        showUserDataInView(mUserModel);
        saveUserData(mUserModel);
    }

    public void setCurrentWeight(double weight) {
        if (!mUserModel.isValidWeight(weight)) {
            showErrorInView("Invalid Weight");
            return;
        }

        mUserModel.setWeight(weight);
        showUserDataInView(mUserModel);
        saveUserData(mUserModel);
    }

    public double getCurrentWeight() {
        return (mUserModel.isValidWeight()) ? mUserModel.getWeight() : 0;
    }

    public void setCurrentHeight(int height) {
        if (!mUserModel.isValidHeight(height)) {
            showErrorInView("Invalid Height");
            return;
        }

        mUserModel.setHeight(height);
        showUserDataInView(mUserModel);
        saveUserData(mUserModel);
    }

    public int getCurrentHeight() {
        return  (mUserModel.isValidHeight()) ? mUserModel.getHeight() : 0;
    }

    public void setCurrentGender(int gender) {
        if (!mUserModel.isValidGender(gender)) {
            showErrorInView("Invalid Gender");
            return;
        }

        mUserModel.setGender(gender);
        showUserDataInView(mUserModel);
        saveUserData(mUserModel);
    }

    public int getCurrentGender() {
        return (mUserModel.isValidGender()) ? mUserModel.getGender() : -1;
    }

    public void setCurrentBirthday(int year, int monthOfYear, int dayOfMonth) {
        Date birthday = null;
        try {
            birthday = DateUtils.getDateFromText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!mUserModel.isValidBirthday(birthday)) {
            showErrorInView("Invalid Birthday");
            return;
        }

        mUserModel.setBirthday(birthday);
        showUserDataInView(mUserModel);
        saveUserData(mUserModel);
    }

    public Date getCurrentBirthday() {
        return (mUserModel.isValidBirthday()) ? mUserModel.getBirthday() : null;
    }

    public void setCurrentActivityFactor(int activityFactor) {
        if (!mUserModel.isValidActivityFactor(activityFactor)) {
            showErrorInView("Invalid Activity Level");
            return;
        }

        mUserModel.setActivityFactor(activityFactor);
        showUserDataInView(mUserModel);
        saveUserData(mUserModel);
    }

    public int getCurrentActivityFactor() {
        return (mUserModel.isValidActivityFactor()) ? mUserModel.getActivityFactor() : -1;
    }

    public void setCurrentGoal(int goal) {
        if (!mUserModel.isValidGoal(goal)) {
            showErrorInView("Invalid Goal");
            return;
        }

        mUserModel.setGoal(goal);
        showUserDataInView(mUserModel);
        saveUserData(mUserModel);
    }

    public int getCurrentGoal() {
        return (mUserModel.isValidGoal()) ? mUserModel.getGoal() : -1;
    }

    private void showErrorInView(String message) {
        mView.showError(message);
    }

    private void saveUserData(UserModel userModel) {
        if (userModel.isValid()) {
            userModel.calculateAll();
            mInsertUser.setUser(mUserModelDataMapper.transformInverse(userModel));
            mInsertUser.execute(new DefaultSubscriber());
        }
    }

    private final class GetUserDetailsSubscriber extends DefaultSubscriber<com.zireck.calories.domain.User> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            showUserDataInView(null);
        }

        @Override
        public void onNext(com.zireck.calories.domain.User user) {
            // TODO fix this:
            if (user != null) {
                mUserModel = mUserModelDataMapper.transform(user);
                showUserDataInView(mUserModel);
            } else {
                showUserDataInView(null);
            }

        }
    }
}
