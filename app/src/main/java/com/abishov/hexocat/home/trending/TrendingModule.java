package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.dagger.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
@FragmentScope
public abstract class TrendingModule {

    @Provides
    @FragmentScope
    static TrendingService trendingService(Retrofit retrofit) {
        return retrofit.create(TrendingService.class);
    }

    @Binds
    @FragmentScope
    abstract TrendingContract.Presenter trendingPresenter(TrendingPresenter impl);
}
