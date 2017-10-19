package com.abishov.hexocat;

import android.app.Activity;

import com.abishov.hexocat.home.HomeActivity;
import com.abishov.hexocat.home.HomeComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = HomeComponent.class)
abstract class AppBindings {

    @Binds
    @IntoMap
    @ActivityKey(HomeActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bind(HomeComponent.Builder builder);
}
