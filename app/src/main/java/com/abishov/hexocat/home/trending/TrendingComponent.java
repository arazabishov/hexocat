package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.dagger.PerView;

import dagger.Subcomponent;

@PerView
@Subcomponent(modules = TrendingModule.class)
public interface TrendingComponent {
    void inject(TrendingView trendingView);

    void inject(TrendingFragment trendingFragment);
}
