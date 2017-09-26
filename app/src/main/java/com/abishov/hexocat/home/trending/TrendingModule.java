package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.dagger.PerFragment;
import com.abishov.hexocat.commons.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
@PerFragment
public final class TrendingModule {
    private final int daysBefore;

    TrendingModule(int daysBefore) {
        this.daysBefore = daysBefore;
    }

    @Provides
    @PerFragment
    QueryDateProvider dateProvider() {
        return new QueryDateProvider();
    }

    @Provides
    @PerFragment
    TrendingService trendingService(Retrofit retrofit) {
        return retrofit.create(TrendingService.class);
    }

    @Provides
    @PerFragment
    TrendingRepository trendingRepository(TrendingService trendingService,
            QueryDateProvider queryDateProvider) {
        return new TrendingRepository(trendingService, queryDateProvider);
    }

    @Provides
    @PerFragment
    TrendingPresenter trendingPresenter(SchedulerProvider schedulerProvider,
            TrendingRepository trendingRepository) {
        return new TrendingPresenter(schedulerProvider, trendingRepository, daysBefore);
    }
}
