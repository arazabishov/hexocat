package com.abishov.hexocat;

import com.abishov.hexocat.commons.dagger.ActivityScope;
import com.abishov.hexocat.home.HomeActivity;
import com.abishov.hexocat.home.HomeBindings;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class AppBindings {

    @ActivityScope
    @ContributesAndroidInjector(modules = HomeBindings.class)
    abstract HomeActivity contributeHomeActivityInjector();
}
