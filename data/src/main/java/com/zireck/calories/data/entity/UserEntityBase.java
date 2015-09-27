package com.zireck.calories.data.entity;

import java.util.Date;

/**
 * Created by Zireck on 15/07/2015.
 */
public abstract class UserEntityBase {

    public abstract int getGender();
    public abstract Date getBirthday();
    public abstract Integer getAge();
    public abstract void setAge(Integer age);
    //public abstract int getMeasurementSystem();
    public abstract double getWeight();
    public abstract int getHeight();
    public abstract int getActivityFactor();
    public abstract Double getBmr();
    public abstract void setBmr(Double bmr);
    public abstract Double getMaintain();
    public abstract void setMaintain(Double maintain);
    public abstract Double getBurn();
    public abstract void setBurn(Double burn);
    public abstract Double getGain();
    public abstract void setGain(Double gain);

/*
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
    */
}
