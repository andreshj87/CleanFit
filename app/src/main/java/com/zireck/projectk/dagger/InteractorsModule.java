package com.zireck.projectk.dagger;

import com.zireck.projectk.interactor.AddFoodInteractor;
import com.zireck.projectk.interactor.AddFoodInteractorImpl;
import com.zireck.projectk.interactor.EditFoodInteractor;
import com.zireck.projectk.interactor.EditFoodInteractorImpl;
import com.zireck.projectk.interactor.FoodDetailInteractor;
import com.zireck.projectk.interactor.FoodDetailInteractorImpl;
import com.zireck.projectk.interactor.FoodListInteractor;
import com.zireck.projectk.interactor.FoodListInteractorImpl;

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
}
