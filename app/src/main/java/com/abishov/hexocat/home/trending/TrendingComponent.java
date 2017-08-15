package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.dagger.PerFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = TrendingModule.class)
public interface TrendingComponent {
    void inject(TrendingFragment trendingFragment);
}
