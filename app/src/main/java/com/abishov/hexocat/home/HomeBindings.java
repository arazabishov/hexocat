package com.abishov.hexocat.home;

import android.support.v4.app.Fragment;

import com.abishov.hexocat.home.trending.TrendingComponent;
import com.abishov.hexocat.home.trending.TrendingFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = TrendingComponent.class)
abstract class HomeBindings {

    @Binds
    @IntoMap
    @FragmentKey(TrendingFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bind(TrendingComponent.Builder builder);
}
