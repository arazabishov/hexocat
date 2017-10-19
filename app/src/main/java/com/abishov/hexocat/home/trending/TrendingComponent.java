package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.dagger.FragmentScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = TrendingModule.class)
@FragmentScope
public interface TrendingComponent extends AndroidInjector<TrendingFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TrendingFragment> {
    }
}
