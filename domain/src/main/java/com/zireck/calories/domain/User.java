package com.zireck.calories.domain;

import java.util.Date;

/**
 * Created by Zireck on 11/09/2015.
 */
public class User {

    private String name;
    private int gender;
    private Date birthday;
    private int age;
    //private int measurementSystem;
    private double weight;
    private int height;
    private int activityFactor;
    private double bmr;
    private int goal;
    private double maintain;
    private double burn;
    private double gain;

    public User() {

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

    /*
    public int getMeasurementSystem() {
        return measurementSystem;
    }

    public void setMeasurementSystem(int measurementSystem) {
        this.measurementSystem = measurementSystem;
    }*/

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
}
