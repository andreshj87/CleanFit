package com.zireck.projectk.presentation.dagger.component;

import com.zireck.projectk.presentation.dagger.PerActivity;
import com.zireck.projectk.presentation.dagger.module.ActivityModule;
import com.zireck.projectk.presentation.dagger.module.FoodModule;
import com.zireck.projectk.presentation.view.fragment.AddEditFoodFragment;
import com.zireck.projectk.presentation.view.fragment.AddFoodFragment;
import com.zireck.projectk.presentation.view.fragment.AddMealFragment;
import com.zireck.projectk.presentation.view.fragment.DrinkListFragment;
import com.zireck.projectk.presentation.view.fragment.EditFoodFragment;
import com.zireck.projectk.presentation.view.fragment.FoodDetailFragment;
import com.zireck.projectk.presentation.view.fragment.FoodListFragment;

import dagger.Component;

/**
 * Created by Zireck on 13/08/2015.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class,
                FoodModule.class
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
}
