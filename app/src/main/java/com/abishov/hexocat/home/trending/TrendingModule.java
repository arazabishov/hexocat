package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.dagger.FragmentScope;
import com.abishov.hexocat.commons.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
@FragmentScope
public final class TrendingModule {

    TrendingModule() {
    }

    @Provides
    @FragmentScope
    TrendingService trendingService(Retrofit retrofit) {
        return retrofit.create(TrendingService.class);
    }

    @Provides
    @FragmentScope
    TrendingRepository trendingRepository(TrendingService trendingService) {
        return new TrendingRepository(trendingService);
    }

    @Provides
    @FragmentScope
    TrendingPresenter trendingPresenter(SchedulerProvider schedulerProvider,
            TrendingRepository trendingRepository) {
        return new TrendingPresenter(schedulerProvider, trendingRepository);
    }
}
