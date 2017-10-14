package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.dagger.PerFragment;
import com.abishov.hexocat.commons.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
@PerFragment
public final class TrendingModule {

    TrendingModule() {
    }

    @Provides
    @PerFragment
    TrendingService trendingService(Retrofit retrofit) {
        return retrofit.create(TrendingService.class);
    }

    @Provides
    @PerFragment
    TrendingRepository trendingRepository(TrendingService trendingService) {
        return new TrendingRepository(trendingService);
    }

    @Provides
    @PerFragment
    TrendingPresenter trendingPresenter(SchedulerProvider schedulerProvider,
            TrendingRepository trendingRepository) {
        return new TrendingPresenter(schedulerProvider, trendingRepository);
    }
}
