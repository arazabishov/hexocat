package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.dagger.PerView;
import com.abishov.hexocat.commons.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
@PerView
public final class TrendingModule {
    private final int daysBefore;

    TrendingModule(int daysBefore) {
        this.daysBefore = daysBefore;
    }

    @Provides
    @PerView
    QueryDateProvider dateProvider() {
        return new QueryDateProvider();
    }

    @Provides
    @PerView
    TrendingService trendingService(Retrofit retrofit) {
        return retrofit.create(TrendingService.class);
    }

    @Provides
    @PerView
    TrendingRepository trendingRepository(TrendingService trendingService,
            QueryDateProvider queryDateProvider) {
        return new TrendingRepository(trendingService, queryDateProvider);
    }

    @Provides
    @PerView
    TrendingPresenter trendingPresenter(SchedulerProvider schedulerProvider,
            TrendingRepository trendingRepository) {
        return new TrendingPresenter(schedulerProvider, trendingRepository, daysBefore);
    }
}
