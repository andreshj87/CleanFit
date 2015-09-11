package com.zireck.projectk.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zireck.projectk.presentation.enumeration.ActivityFactor;
import com.zireck.projectk.presentation.enumeration.Gender;
import com.zireck.projectk.presentation.enumeration.MeasurementSystem;
import com.zireck.projectk.presentation.util.DateUtils;

import java.util.Date;

/**
 * Class that represents user data in the presentation layer.
 */
public class UserModel implements Parcelable {

    private String name;
    private int gender;
    private Date birthday;
    private int age;
    private int measurementSystem;
    private double weight;
    private int height;
    private int activityFactor;
    private double bmr;
    private int goal;
    private double maintain;
    private double burn;
    private double gain;

    public UserModel() {

    }

    protected UserModel(Parcel in) {
        name = in.readString();
        gender = in.readInt();
        birthday = new Date(in.readLong());
        age = in.readInt();
        measurementSystem = in.readInt();
        weight = in.readDouble();
        height = in.readInt();
        activityFactor = in.readInt();
        bmr = in.readDouble();
        goal = in.readInt();
        maintain = in.readDouble();
        burn = in.readDouble();
        gain = in.readDouble();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMeasurementSystem() {
        return measurementSystem;
    }

    public void setMeasurementSystem(int measurementSystem) {
        this.measurementSystem = measurementSystem;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getActivityFactor() {
        return activityFactor;
    }

    public void setActivityFactor(int activityFactor) {
        this.activityFactor = activityFactor;
    }

    public double getBmr() {
        return bmr;
    }

    public void setBmr(double bmr) {
        this.bmr = bmr;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public double getMaintain() {
        return maintain;
    }

    public void setMaintain(double maintain) {
        this.maintain = maintain;
    }

    public double getBurn() {
        return burn;
    }

    public void setBurn(double burn) {
        this.burn = burn;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public void calculateAll() {
        calculateAge();
        calculateBmr();
        calculateRecommendedIntake();
        calculateBurn();
        calculateGain();
    }

    public void calculateAge() {
        setAge(DateUtils.calculateAge(getBirthday()));
    }

    /**
     * Calculate the Basal Metabolic Rate using the appropriate formula per each measurement system.
     */
    public void calculateBmr() {
        if (getMeasurementSystem() == MeasurementSystem.METRIC.getIntValue()) {
            if (getGender() == Gender.MALE.getIntValue()) {
                setBmr(66.5 + ( 13.75 * getWeight() ) + ( 5.003 * getHeight() ) - ( 6.755 * getAge() ));
            } else if (getGender() == Gender.FEMALE.getIntValue()) {
                setBmr(655 + ( 9.563 * getWeight() ) + ( 1.850 * getHeight() ) - ( 4.676 * getAge() ));
            }
        } else if (getMeasurementSystem() == MeasurementSystem.IMPERIAL.getIntValue()) {
            if (getGender() == Gender.MALE.getIntValue()) {
                setBmr(66 + ( 6.2 * getWeight() ) + ( 12.7 * getHeight() ) - ( 6.76 * getAge() ));
            } else if (getGender() == Gender.FEMALE.getIntValue()) {
                setBmr(65.5 + ( 4.35 * getWeight() ) + ( 4.7 * getHeight() ) - ( 4.7 * getAge() ));
            }
        }

        if (getBmr() <= 0) {
            throw new IllegalStateException("Illegal State for BMR");
        }
    }

    /**
     * Calculate the recommended caloric intake according to the user activity level.
     */
    public void calculateRecommendedIntake() {
        if (getActivityFactor() == ActivityFactor.SEDENTARY.getIntValue()) {
            setMaintain(getBmr() * ActivityFactor.SEDENTARY.getDoubleValue());
        } else if (getActivityFactor() == ActivityFactor.LIGHT.getIntValue()) {
            setMaintain(getBmr() * ActivityFactor.LIGHT.getDoubleValue());
        } else if (getActivityFactor() == ActivityFactor.MODERATE.getIntValue()) {
            setMaintain(getBmr() * ActivityFactor.MODERATE.getDoubleValue());
        } else if (getActivityFactor() == ActivityFactor.HEAVY.getIntValue()) {
            setMaintain(getBmr() * ActivityFactor.HEAVY.getDoubleValue());
        } else if (getActivityFactor() == ActivityFactor.VERY_HEAVY.getIntValue()) {
            setMaintain(getBmr() * ActivityFactor.VERY_HEAVY.getDoubleValue());
        }

        if (getMaintain() <= 0 || getMaintain() < getBmr()) {
            throw new IllegalStateException("Illegal State for Recommended Intake");
        }
    }

    public void calculateBurn() {
        setBurn(getMaintain() * 0.85);

        if (getBurn() <= 0) {
            throw new IllegalStateException("Illegal State for Burning Calories");
        }
    }

    public void calculateGain() {
        setGain(getMaintain() * 1.15);

        if (getGain() <= 0) {
            throw new IllegalStateException("Illegal State for Gaining Calories");
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(gender);
        dest.writeLong(birthday.getTime());
        dest.writeInt(age);
        dest.writeInt(measurementSystem);
        dest.writeDouble(weight);
        dest.writeInt(height);
        dest.writeInt(activityFactor);
        dest.writeDouble(bmr);
        dest.writeInt(goal);
        dest.writeDouble(maintain);
        dest.writeDouble(burn);
        dest.writeDouble(gain);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
