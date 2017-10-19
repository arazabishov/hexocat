package com.abishov.hexocat.home;

import com.abishov.hexocat.commons.dagger.ActivityScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {
        HomeModule.class,
        HomeBindings.class
})
@ActivityScope
public interface HomeComponent extends AndroidInjector<HomeActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<HomeActivity> {
    }
}
