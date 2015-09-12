package com.zireck.projectk.data.entity.mapper;

import com.zireck.projectk.data.entity.UserEntity;
import com.zireck.projectk.domain.User;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link UserEntity}, in the data layer, to {@link User} in the
 * domain layer.
 */
public class UserEntityDataMapper {

    @Inject
    public UserEntityDataMapper() {

    }

    /**
     * Transforms a {@link UserEntity} into an {@link User}.
     *
     * @param userEntity {@link UserEntity} to be transformed.
     * @return {@link User} if valid {@link UserEntity}, otherwise null.
     */
    public User transform(UserEntity userEntity) {
        User user = null;
        if (userEntity != null) {
            user = new User();
            user.setName(userEntity.getName());
            user.setGender(userEntity.getGender());
            user.setBirthday(userEntity.getBirthday());
            user.setAge(userEntity.getAge());
            //user.setMeasurementSystem(userEntity.getMeasurementSystem());
            user.setWeight(userEntity.getWeight());
            user.setHeight(userEntity.getHeight());
            user.setActivityFactor(userEntity.getActivityFactor());
            user.setBmr(userEntity.getBmr());
            user.setGoal(userEntity.getGoal());
            user.setMaintain(userEntity.getMaintain());
            user.setBurn(userEntity.getBurn());
            user.setGain(userEntity.getGain());
        }

        return user;
    }

    /**
     * Transforms a {@link User} into a {@link UserEntity}.
     *
     * @param user {@link User} to be transformed.
     * @return {@link UserEntity} if valid {@link User}, otherwise null.
     */
    public UserEntity transformInverse(User user) {
        UserEntity userEntity = null;
        if (user != null) {
            userEntity = new UserEntity();
            userEntity.setName(user.getName());
            userEntity.setGender(user.getGender());
            userEntity.setBirthday(user.getBirthday());
            userEntity.setAge(user.getAge());
            //userEntity.setMeasurementSystem(user.getMeasurementSystem());
            userEntity.setWeight(user.getWeight());
            userEntity.setHeight(user.getHeight());
            userEntity.setActivityFactor(user.getActivityFactor());
            userEntity.setBmr(user.getBmr());
            userEntity.setGoal(user.getGoal());
            userEntity.setMaintain(user.getMaintain());
            userEntity.setBurn(user.getBurn());
            userEntity.setGain(user.getGain());
        }

        return userEntity;
    }
}
