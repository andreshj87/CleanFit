package com.zireck.calories.presentation.dagger.component;

import com.zireck.calories.presentation.dagger.module.ActivityModule;
import com.zireck.calories.presentation.dagger.module.FoodModule;
import com.zireck.calories.presentation.view.fragment.AddFoodFragment;
import com.zireck.calories.presentation.view.fragment.DiaryFragment;
import com.zireck.calories.presentation.view.fragment.EditFoodFragment;
import com.zireck.calories.presentation.view.fragment.FoodDetailFragment;
import com.zireck.calories.presentation.view.fragment.FoodListFragment;
import com.zireck.calories.presentation.view.fragment.SettingsFragment;
import com.zireck.calories.presentation.dagger.PerActivity;
import com.zireck.calories.presentation.dagger.module.MealModule;
import com.zireck.calories.presentation.dagger.module.UserModule;
import com.zireck.calories.presentation.view.fragment.AddEditFoodFragment;
import com.zireck.calories.presentation.view.fragment.AddMealFragment;
import com.zireck.calories.presentation.view.fragment.DrinkListFragment;
import com.zireck.calories.presentation.view.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by Zireck on 13/08/2015.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class,
                FoodModule.class,
                MealModule.class,
                UserModule.class
        }
)
public interface FoodComponent extends ActivityComponent {
    void inject(FoodListFragment foodListFragment);
    void inject(DrinkListFragment drinkListFragment);
    void inject(FoodDetailFragment foodDetailFragment);
    void inject(AddFoodFragment addFoodFragment);
    void inject(EditFoodFragment editFoodFragment);
    void inject(AddEditFoodFragment addEditFoodFragment);
    void inject(AddMealFragment addMealFragment);
    void inject(HomeFragment homeFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(DiaryFragment diaryFragment);
}
