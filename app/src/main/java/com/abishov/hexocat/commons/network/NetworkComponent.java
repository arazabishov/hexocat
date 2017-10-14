package com.abishov.hexocat.commons.network;

import com.abishov.hexocat.commons.dagger.SessionScope;
import com.abishov.hexocat.commons.picasso.PicassoComponent;
import com.abishov.hexocat.commons.picasso.PicassoModule;
import com.abishov.hexocat.home.trending.TrendingComponent;
import com.abishov.hexocat.home.trending.TrendingModule;

import dagger.Subcomponent;

@SessionScope
@Subcomponent(modules = NetworkModule.class)
public interface NetworkComponent {
    PicassoComponent plus(PicassoModule picassoModule);

    TrendingComponent plus(TrendingModule trendingModule);
}