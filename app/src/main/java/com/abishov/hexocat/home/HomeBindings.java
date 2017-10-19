package com.abishov.hexocat.home;

import com.abishov.hexocat.commons.dagger.FragmentScope;
import com.abishov.hexocat.home.trending.TrendingFragment;
import com.abishov.hexocat.home.trending.TrendingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeBindings {

    @FragmentScope
    @ContributesAndroidInjector(modules = TrendingModule.class)
    abstract TrendingFragment contributesTrendingFragmentInjector();
}
