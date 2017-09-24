package com.abishov.hexocat.commons.network;

import com.abishov.hexocat.commons.dagger.PerSession;
import com.abishov.hexocat.home.trending.TrendingComponent;
import com.abishov.hexocat.home.trending.TrendingModule;

import dagger.Subcomponent;

@PerSession
@Subcomponent(modules = NetworkModule.class)
public interface NetworkComponent {
    TrendingComponent plus(TrendingModule trendingModule);
}