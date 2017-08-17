package com.abishov.hexocat.home.trending;

import android.support.annotation.NonNull;

import com.abishov.hexocat.commons.dagger.PerFragment;
import com.abishov.hexocat.commons.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
@PerFragment
public final class TrendingModule {

    @Provides
    @PerFragment
    QueryDateProvider dateProvider() {
        return new QueryDateProvider();
    }

    @Provides
    @PerFragment
    TrendingService trendingService(@NonNull Retrofit retrofit) {
        return retrofit.create(TrendingService.class);
    }

    @Provides
    @PerFragment
    TrendingRepository trendingRepository(@NonNull TrendingService trendingService,
            @NonNull QueryDateProvider queryDateProvider) {
        return new TrendingRepository(trendingService, queryDateProvider);
    }

    @Provides
    @PerFragment
    TrendingPresenter trendingPresenter(@NonNull SchedulerProvider schedulerProvider,
            @NonNull TrendingRepository trendingRepository) {
        return new TrendingPresenter(schedulerProvider, trendingRepository);
    }
}
