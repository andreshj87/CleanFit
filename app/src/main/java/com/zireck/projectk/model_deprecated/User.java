package com.zireck.projectk.model_deprecated;

import com.zireck.projectk.enums.ActivityFactor;
import com.zireck.projectk.enums.Gender;
import com.zireck.projectk.enums.Goal;
import com.zireck.projectk.enums.MeasurementSystem;

/**
 * Created by Zireck on 13/07/2015.
 */
public class User {
    private String name;
    private Gender gender;
    private int age;
    private MeasurementSystem measurementSystem;
    private double weight;
    private int height;
    private ActivityFactor activityFactor;
    private double bmr;
    private Goal goal;
    private double maintain;
    private double burn;
    private double gain;

    public void calculate() {
        calculateBmr();
        calculateRecommendedIntake();
        calculateBurn();
        calculateGain();
    }

    public void calculateBmr() {
        if (measurementSystem == MeasurementSystem.METRIC) {
            if (gender == Gender.MALE) {
                bmr = 66.5 + ( 13.75 * weight ) + ( 5.003 * height ) - ( 6.755 * age );
            } else if (gender == Gender.FEMALE) {
                bmr = 655 + ( 9.563 * weight ) + ( 1.850 * height ) - ( 4.676 * age );
            }
        } else if (measurementSystem == MeasurementSystem.IMPERIAL) {
            if (gender == Gender.MALE) {
                bmr = 66 + ( 6.2 * weight ) + ( 12.7 * height ) - ( 6.76 * age );
            } else if (gender == Gender.FEMALE) {
                bmr = 65.5 + ( 4.35 * weight ) + ( 4.7 * height ) - ( 4.7 * age );
            }
        }

        if (bmr <= 0) {
            throw new IllegalStateException("Illegal State for BMR");
        }
    }

    public void calculateRecommendedIntake() {
        if (activityFactor == ActivityFactor.SEDENTARY) {
            maintain = bmr * ActivityFactor.SEDENTARY.getDoubleValue();
        } else if (activityFactor == ActivityFactor.LIGHT) {
            maintain = bmr * ActivityFactor.LIGHT.getDoubleValue();
        } else if (activityFactor == ActivityFactor.MODERATE) {
            maintain = bmr * ActivityFactor.MODERATE.getDoubleValue();
        } else if (activityFactor == ActivityFactor.HEAVY) {
            maintain = bmr * ActivityFactor.HEAVY.getDoubleValue();
        } else if (activityFactor == ActivityFactor.VERY_HEAVY) {
            maintain = bmr * ActivityFactor.VERY_HEAVY.getDoubleValue();
        }

        if (maintain <= 0 || maintain < bmr) {
            throw new IllegalStateException("Illegal State for Recommended Intake");
        }
    }

    public void calculateBurn() {
        burn = maintain * 0.85;

        if (burn <= 0) {
            throw new IllegalStateException("Illegal State for Burning Calories");
        }
    }

    public void calculateGain() {
        gain = maintain * 1.15;

        if (gain <= 0) {
            throw new IllegalStateException("Illegal State for Gaining Calories");
        }
    }

}
