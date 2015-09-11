package com.zireck.projectk.presentation.mapper;

import com.zireck.projectk.domain.User;
import com.zireck.projectk.presentation.model.UserModel;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link User}, in the domain layer, to {@link UserModel} in the
 * presentation layer.
 */
public class UserModelDataMapper {

    @Inject
    public UserModelDataMapper() {

    }

    /**
     * Transforms a {@link User} into a {@link UserModel}.
     *
     * @param user {@link User} object to be transformed.
     * @return {@link UserModel}.
     */
    public UserModel transform(User user) {
        if (user == null) {
            argumentException();
        }

        UserModel userModel = new UserModel();
        userModel.setName(user.getName());
        userModel.setGender(user.getGender());
        userModel.setBirthday(user.getBirthday());
        userModel.setAge(user.getAge());
        userModel.setMeasurementSystem(user.getMeasurementSystem());
        userModel.setWeight(user.getWeight());
        userModel.setHeight(user.getHeight());
        userModel.setActivityFactor(user.getActivityFactor());
        userModel.setBmr(user.getBmr());
        userModel.setGoal(user.getGoal());
        userModel.setMaintain(user.getMaintain());
        userModel.setBurn(user.getBurn());
        userModel.setGain(user.getGain());

        return userModel;
    }

    /**
     * Transforms a {@link UserModel} into a {@link User}.
     *
     * @param userModel {@link UserModel} object to be transformed.
     * @return {@link User}.
     */
    public User transformInverse(UserModel userModel) {
        if (userModel == null) {
            argumentException();
        }

        User user = new User();
        user.setName(userModel.getName());
        user.setGender(userModel.getGender());
        user.setBirthday(userModel.getBirthday());
        user.setAge(userModel.getAge());
        user.setMeasurementSystem(userModel.getMeasurementSystem());
        user.setWeight(userModel.getWeight());
        user.setHeight(userModel.getHeight());
        user.setActivityFactor(userModel.getActivityFactor());
        user.setBmr(userModel.getBmr());
        user.setGoal(userModel.getGoal());
        user.setMaintain(userModel.getMaintain());
        user.setBurn(userModel.getBurn());
        user.setGain(userModel.getGain());

        return user;
    }

    private void argumentException() {
        throw new IllegalArgumentException("Cannot transform a null value");
    }
}
