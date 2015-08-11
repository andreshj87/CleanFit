package com.zireck.projectk.presentation.dagger;

import com.zireck.projectk.presentation.interactor.AddFoodInteractor;
import com.zireck.projectk.presentation.interactor.AddFoodInteractorImpl;
import com.zireck.projectk.presentation.interactor.AddMealInteractor;
import com.zireck.projectk.presentation.interactor.AddMealInteractorImpl;
import com.zireck.projectk.presentation.interactor.EditFoodInteractor;
import com.zireck.projectk.presentation.interactor.EditFoodInteractorImpl;
import com.zireck.projectk.presentation.interactor.FoodDetailInteractor;
import com.zireck.projectk.presentation.interactor.FoodDetailInteractorImpl;
import com.zireck.projectk.presentation.interactor.FoodListInteractor;
import com.zireck.projectk.presentation.interactor.FoodListInteractorImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 03/08/2015.
 */
@Module(library = true)
public class InteractorsModule {

    @Provides
    public AddFoodInteractor provideAddFoodInteractor() {
        return new AddFoodInteractorImpl();
    }

    @Provides
    public EditFoodInteractor provideEditFoodInteractor() {
        return new EditFoodInteractorImpl();
    }

    @Provides
    public FoodDetailInteractor provideFoodDetailInteractor() {
        return new FoodDetailInteractorImpl();
    }

    @Provides
    public FoodListInteractor provideFoodListInteractor() {
        return new FoodListInteractorImpl();
    }

    @Provides
    public AddMealInteractor provideAddMealInteractor() {
        return new AddMealInteractorImpl();
    }
}
