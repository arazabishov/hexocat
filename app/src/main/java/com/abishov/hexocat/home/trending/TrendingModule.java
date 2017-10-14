package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.dagger.PerView;
import com.abishov.hexocat.commons.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
@PerView
public final class TrendingModule {

    TrendingModule() {
    }

    @Provides
    @PerView
    TrendingService trendingService(Retrofit retrofit) {
        return retrofit.create(TrendingService.class);
    }

    @Provides
    @PerView
    TrendingRepository trendingRepository(TrendingService trendingService) {
        return new TrendingRepository(trendingService);
    }

    @Provides
    @PerView
    TrendingPresenter trendingPresenter(SchedulerProvider schedulerProvider,
            TrendingRepository trendingRepository) {
        return new TrendingPresenter(schedulerProvider, trendingRepository);
    }
}
